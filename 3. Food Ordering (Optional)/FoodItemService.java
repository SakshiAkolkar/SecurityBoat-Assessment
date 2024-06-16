package com.example.booking.service;

import com.example.booking.model.FoodItem;
import com.example.booking.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;

    public List<FoodItem> findAll() {
        return foodItemRepository.findAll();
    }
}
