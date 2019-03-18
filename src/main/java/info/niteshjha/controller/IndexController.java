package info.niteshjha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/", "/login"})
    public String getIndex() {
        return "login";
    }


    @RequestMapping("/signup")
    public String register() {
        return "signup";
    }
}
