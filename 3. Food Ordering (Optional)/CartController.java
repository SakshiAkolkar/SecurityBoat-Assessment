package com.example.booking.controller;

import com.example.booking.model.CartItem;
import com.example.booking.model.FoodItem;
import com.example.booking.model.User;
import com.example.booking.service.CartItemService;
import com.example.booking.service.FoodItemService;
import com.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private FoodItemService foodItemService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        List<CartItem> cartItems = cartItemService.findByUser(user);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long foodItemId, @RequestParam int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        FoodItem foodItem = foodItemService.findById(foodItemId).orElseThrow(() -> new IllegalArgumentException("Invalid food item Id:" + foodItemId));
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setFoodItem(foodItem);
        cartItem.setQuantity(quantity);

        cartItemService.save(cartItem);
        return "redirect:/menu";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        CartItem cartItem = cartItemService.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("Invalid cart item Id:" + cartItemId));
        cartItemService.delete(cartItem);
        return "redirect:/cart";
    }
}
