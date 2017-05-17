package com.epam.carrental.web.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView landingPage() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/failure", method = RequestMethod.GET)
    public ModelAndView failurePage() {
        return new ModelAndView("failure");
    }

    @RequestMapping(value = {"/", "/greeting"}, method = RequestMethod.GET)
    public ModelAndView greetingPage() {
        return new ModelAndView("greeting");
    }
}
