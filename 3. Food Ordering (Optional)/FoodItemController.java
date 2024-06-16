package com.example.booking.controller;

import com.example.booking.model.FoodItem;
import com.example.booking.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FoodItemController {
    @Autowired
    private FoodItemService foodItemService;

    @GetMapping("/menu")
    public String listFoodItems(Model model) {
        List<FoodItem> foodItems = foodItemService.findAll();
        model.addAttribute("foodItems", foodItems);
        return "menu";
    }
}
