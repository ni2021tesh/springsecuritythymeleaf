// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.controller;

import info.niteshjha.model.User;
import info.niteshjha.service.UserCreateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private UserCreateService userCreateService;

    public UserController(UserCreateService userCreateService) {
        this.userCreateService = userCreateService;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public ModelAndView getUser() {
        return new ModelAndView("createUser").addObject("user", new User());
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ModelAndView createUser(User userCreate) {
        User userCreated = this.userCreateService.createUser(userCreate);
        return new ModelAndView("redirect:/userList").addObject("user", this.userCreateService.getUserList());
    }

    @RequestMapping(value = "/modifyUser/{id}", method = RequestMethod.GET)
    public ModelAndView modifyUser(@PathVariable(value = "id") Long userId) {
        return new ModelAndView("modifyUser").addObject("user", this.userCreateService.getCreatedUser(userId));
    }

    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    public ModelAndView modifyUser(User modifyUser) {
        this.userCreateService.modifyUser(modifyUser);
        return new ModelAndView("redirect:/userList").addObject("user", this.userCreateService.getUserList());
    }


    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Long id) {
        this.userCreateService.deleteUser(id);
        return new ModelAndView("redirect:/userList").addObject("user", this.userCreateService.getUserList());
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(Long userId) {
        return new ModelAndView("resetPassword").addObject("user", new User());
    }

}
