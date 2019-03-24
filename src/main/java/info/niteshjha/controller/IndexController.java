package info.niteshjha.controller;

import info.niteshjha.config.UserService;
import info.niteshjha.model.PasswordResetToken;
import info.niteshjha.model.User;
import info.niteshjha.service.PasswordResetTokenService;
import info.niteshjha.service.SimpleMailService;
import info.niteshjha.service.UserCreateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class IndexController {

    private UserCreateService userCreateService;

    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private SimpleMailService mailService;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    public IndexController(UserCreateService userCreateService, PasswordResetTokenService passwordResetTokenService) {
        this.userCreateService = userCreateService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @RequestMapping({"/", "/login"})
    public ModelAndView getIndex() {
        return new ModelAndView("/login", "user", new User());
    }

    @RequestMapping("/signup")
    public ModelAndView register() {
        return new ModelAndView("/signup", "user", new User());
    }


    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotPassword() {
        return new ModelAndView("forgotPassword");
    }

    @RequestMapping(value = "/user/forgotPassword", method = RequestMethod.POST)
    public ModelAndView forgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        User userByEmail = this.userCreateService.getUserByEmail(email);

        if (userByEmail == null) {
            log.info("Not found User with email id :: " + email);
            redirectAttributes.addFlashAttribute("errorMessage", "Not found User with email id :: " + email);
            new ModelAndView("redirect:/forgotPassword");
        }

        final String token = UUID.randomUUID().toString();

        final String URL = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        passwordResetTokenService.createPasswordResetToken(new PasswordResetToken(token, userByEmail));

        String RESET_PASSWORD_SEND_URL = URL + "/changePassword?token=" + token + "&id=" + userByEmail.getId();


        log.info("Sending Password Reset Mail to user :: " + email);
        mailService.sendMail(RESET_PASSWORD_SEND_URL, email, env.getProperty("password.reset.subject"), env.getProperty("password.reset.body"));


        log.info("Successfully send password reset mail to user :: " + email);
        redirectAttributes.addFlashAttribute("successMessage", "You will receive password reset link by email shortly.");
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public ModelAndView changePassword(@RequestParam("token") String token, @RequestParam("id") Long id, RedirectAttributes redirectAttributes) {

        PasswordResetToken resetToken = this.passwordResetTokenService.getPasswordResetToken(token);

        if (resetToken == null) {
            log.info("Provided Password reset Token is invalid.");
            redirectAttributes.addFlashAttribute("errorMessage", "Provided Password reset Token is invalid.");
            return new ModelAndView("redirect:/login");
        }

        if (resetToken.getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Token has expired.");
            return new ModelAndView("redirect:/login");
        }

        if (resetToken.isUsed()) {
            log.info("Provided Password reset Token is invalid.");
            redirectAttributes.addFlashAttribute("errorMessage", "Provided Password reset Token is invalid.");
            return new ModelAndView("redirect:/login");
        }

        User user = userCreateService.getCreatedUser(id);

        if (user == null) {
            log.info("Provided Password reset Token is invalid.");
            redirectAttributes.addFlashAttribute("errorMessage", "Provided Password reset Token is invalid.");
            return new ModelAndView("redirect:/login");
        }

        resetToken.setUsed(true);
        passwordResetTokenService.updatePasswordResetToken(resetToken);


        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, userService.loadUserByUsername(user.getEmail()).getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/resetPassword");

    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView savePassword(@RequestParam("password") final String password, @RequestParam("confirmPassword") final String passwordConfirmation, final RedirectAttributes redirectAttributes) {

        if (!password.equals(passwordConfirmation)) {
            final Map<String, Object> model = new HashMap<>();
            model.put("errorMessage", "Passwords do not match");
            return new ModelAndView("resetPassword", model);
        }

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        principal.setPassword(password);

        userCreateService.modifyUser(principal);

        redirectAttributes.addFlashAttribute("successMessage", "Password has been reset successfully.");
        return new ModelAndView("redirect:/login");
    }

}
