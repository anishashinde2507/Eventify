package com.event.major.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.event.major.global.GlobalData;
import com.event.major.model.Event;
import com.event.major.service.EventService;


@Controller
public class CardController {
    @Autowired
    EventService eventService;
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id){
        GlobalData.cart.add(eventService.getEventById(id).get());
        return "redirect:/allevents";

    }
    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Event::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";

    }
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";

    }
    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Event::getPrice).sum());
        return "checkout";
        
    }
    
}
