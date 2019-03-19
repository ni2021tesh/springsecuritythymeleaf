package info.niteshjha.controller;

import info.niteshjha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping({"/", "/login"})
    public ModelAndView getIndex() {
        return new ModelAndView("/login", "user", new User());
    }
    @RequestMapping("/signup")
    public ModelAndView register() {
        return new ModelAndView("/signup", "user", new User());
    }
}
