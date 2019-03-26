// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.controller;

import info.niteshjha.exception.EmailExistsException;
import info.niteshjha.model.EmailValidationToken;
import info.niteshjha.model.SecurityQuestion;
import info.niteshjha.model.SecurityQuestionDefinition;
import info.niteshjha.model.User;
import info.niteshjha.service.*;
import info.niteshjha.validation.ValidatePassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.UUID;

@Controller
@Slf4j
public class LoginController {

    private UserCreateService userCreateService;

    private EmailValidationTokenService emailValidationTokenService;

    private SimpleMailService mailService;

    @Autowired
    private Environment environment;

    private final SecurityQuestionService securityQuestionService;

    private final SecurityQuestionDefinitionService securityQuestionDefinitionService;

    @Autowired
    public LoginController(UserCreateService userCreateService, EmailValidationTokenService emailValidationTokenService, SimpleMailService mailService, SecurityQuestionService securityQuestionService, SecurityQuestionDefinitionService securityQuestionDefinitionService) {
        this.userCreateService = userCreateService;
        this.emailValidationTokenService = emailValidationTokenService;
        this.mailService = mailService;
        this.securityQuestionService = securityQuestionService;
        this.securityQuestionDefinitionService = securityQuestionDefinitionService;
    }

    @PostMapping(value = "/saveUser")
    public ModelAndView userRegistration(@Valid User user, @RequestParam Long questionId, @RequestParam String answer, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        new ValidatePassword().validate(user, result);

        if (result.hasErrors()) {
            return new ModelAndView("signup");
        }

        try {
            String token = UUID.randomUUID().toString();
            User signUpUser = this.userCreateService.createSignUpUser(user);

            SecurityQuestionDefinition securityDefinition = securityQuestionDefinitionService.getSecurityDefinition(questionId);

            securityQuestionService.saveSecurityQuestion(new SecurityQuestion(signUpUser, securityDefinition, answer));

            emailValidationTokenService.createValidationTokenForUser(token, signUpUser);
            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String url = appUrl + "/confirmEmail?token=" + token;
            mailService.sendMail(url, user.getEmail(), environment.getProperty("email.validation.subject"), environment.getProperty("email.validation.body"));
        } catch (EmailExistsException e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("signup");
        }
        redirectAttributes.addFlashAttribute("successMessage", "You will receive the mail shortly.");
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public ModelAndView postUserList() {
        return new ModelAndView("userList").addObject("userList", this.userCreateService.getUserList());
    }

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public ModelAndView getUserList() {
        return new ModelAndView("userList").addObject("userList", this.userCreateService.getUserList());
    }


    @RequestMapping(value = "/confirmEmail", method = RequestMethod.GET)
    public ModelAndView confirmUserEmail(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        EmailValidationToken emailValidationToken = emailValidationTokenService.getEmailValidationToken(token);

        if (emailValidationToken == null) {
            log.info("Provided Token is invalid.Please Register again.");
            redirectAttributes.addFlashAttribute("errorMessage", "Provided Token is invalid.Please Register again.");
            return new ModelAndView("redirect:/login");
        }

        if (emailValidationToken.isUsed()) {
            log.info("Provided account is already registered.");
            redirectAttributes.addFlashAttribute("errorMessage", "Provided account is already registered.");
            return new ModelAndView("redirect:/login");
        }

        User user = emailValidationToken.getUser();

        if (emailValidationToken.getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Token has expired.Please register again");
            return new ModelAndView("redirect:/login");
        }

        user.setEnabled(true);
        userCreateService.modifyUser(user);

        emailValidationToken.setUsed(true);
        emailValidationTokenService.updateToken(emailValidationToken);

        redirectAttributes.addFlashAttribute("message", "Email verified successfully");
        return new ModelAndView("redirect:/login");
    }
}
