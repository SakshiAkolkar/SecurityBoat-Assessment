package com.example.booking.controller;

import com.example.booking.model.CartItem;
import com.example.booking.model.Order;
import com.example.booking.model.User;
import com.example.booking.service.CartItemService;
import com.example.booking.service.OrderService;
import com.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        List<CartItem> cartItems = cartItemService.findByUser(user);
        model.addAttribute("cartItems", cartItems);

        double totalPrice = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getFoodItem().getPrice()).sum();
        model.addAttribute("totalPrice", totalPrice);

        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        List<CartItem> cartItems = cartItemService.findByUser(user);

        double totalPrice = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getFoodItem().getPrice()).sum();

        Order order = new Order();
        order.setUser(user);
        order.setItems(cartItems);
        order.setTotalPrice(totalPrice);
        order.setOrderTime(LocalDateTime.now());

        orderService.save(order);

        cartItemService.clearCart(user);

        return "redirect:/confirmation/" + order.getId();
    }

    @GetMapping("/order/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        return "order";
    }
}
