// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.controller;

import info.niteshjha.model.User;
import info.niteshjha.validation.ValidatePassword;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result) {

        new ValidatePassword().validate(user, result);

        if (result.hasErrors()) {
            return "signup";
        }
        System.out.println(user.toString());
        return "redirect:/login";
    }
}
