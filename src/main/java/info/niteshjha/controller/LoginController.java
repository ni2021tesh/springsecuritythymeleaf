// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.controller;

import info.niteshjha.model.User;
import info.niteshjha.validation.ValidatePassword;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {

        new ValidatePassword().validate(user, result);

        if (result.hasErrors()) {
            return new ModelAndView("signup");
        }
        System.out.println(user.toString());
        redirectAttributes.addFlashAttribute("successMessage", "User Created Successfully");
        return new ModelAndView("redirect:/login");
    }
}
