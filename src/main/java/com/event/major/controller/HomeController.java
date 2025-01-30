package com.event.major.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.event.major.global.GlobalData;
import com.event.major.service.CategoryService;
import com.event.major.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    EventService eventService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "index";
    }

    @GetMapping("/allevents")
    public String allevents(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("events", eventService.getAllEvent());
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "allevents";
    }

    @GetMapping("/allevents/category/{id}")
    public String alleventsByCategory(Model model, @PathVariable int id) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("events", eventService.getAllEventsByCategoryId(id));
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "allevents";
    }
    @GetMapping("/allevents/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable int id) {
       model.addAttribute("event", eventService.getEventById(id).get());
       model.addAttribute("cartCount", GlobalData.cart.size());

        return "viewproduct";
    }

    

    
}
