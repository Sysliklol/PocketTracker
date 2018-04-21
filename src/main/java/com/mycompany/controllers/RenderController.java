package com.mycompany.controllers;

import com.mycompany.auth.RequestUser;
import com.mycompany.entities.User;
import com.mycompany.services.UserService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RenderController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    protected String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model){

        if (error != null) {
            model.addAttribute("error", "Invalid email or password!");
        }

        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    protected String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", ((RequestUser) authentication.getPrincipal()).getUser());
        return "home";
    }
}
