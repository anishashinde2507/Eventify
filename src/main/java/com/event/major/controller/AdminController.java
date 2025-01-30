package com.event.major.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.event.major.dto.EventDTO;
import com.event.major.model.Category;
import com.event.major.model.Event;
import com.event.major.service.CategoryService;
import com.event.major.service.EventService;


@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    EventService eventService;

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }
    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }
    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";

    }
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category>category = categoryService.getAllCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }
        else{
            return "404";
        }
    }



    // Events
    @GetMapping("admin/events")
    public String deleteCat(Model model){
        model.addAttribute("events",eventService.getAllEvent());
        return "events";
    }

    @GetMapping("/admin/events/add")
    public String eventAddGet(Model model){
        
        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "eventsAdd";

    }
    @PostMapping("/admin/events/add")
    public String eventAddPost(@ModelAttribute("eventDTO")EventDTO eventDTO, @RequestParam("productImage")MultipartFile file, @RequestParam("imgName")String imgName) throws IOException {

        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setCategory(categoryService.getAllCategoryById(eventDTO.getCategoryId()).get());
        event.setPrice(eventDTO.getPrice());
        event.setWeight(eventDTO.getWeight());
        event.setDescription(eventDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath, file.getBytes());

        }
        else{
            imageUUID = imgName;

        }
        event.setImageName(imageUUID);
        eventService.addEvent(event);

        return "redirect:/admin/events";
    }

    @GetMapping("/admin/event/delete/{id}")
    public String deleteEvent(@PathVariable long id){
        eventService.removeEventById(id);
        return "redirect:/admin/events";
    }
    @GetMapping("/admin/event/update/{id}")
    public String updateEventGet(@PathVariable long id, Model model){
       Event event = eventService.getEventById(id).get();
       EventDTO eventDTO = new EventDTO();
       eventDTO.setId(event.getId());
       eventDTO.setName(event.getName());
       eventDTO.setCategoryId(event.getCategory().getId());
       eventDTO.setPrice(event.getPrice());
       eventDTO.setWeight(event.getWeight());
       eventDTO.setDescription(event.getDescription());
       eventDTO.setImageName(event.getImageName());

       model.addAttribute("categories", categoryService.getAllCategory());
       model.addAttribute("eventDTO", eventDTO);

       return "eventsAdd";

    }

}
