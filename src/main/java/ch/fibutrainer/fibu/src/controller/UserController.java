package ch.fibutrainer.fibu.src.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Vinicius
 * 30.Jan 2021
 * Version: 1.0
 * Project description:
 * A Controller for Users calls Userservice
 */
@Controller
public class UserController {

    @RequestMapping("/user/login")
    public String login(Model model){
        return "pages/home";
    }
}