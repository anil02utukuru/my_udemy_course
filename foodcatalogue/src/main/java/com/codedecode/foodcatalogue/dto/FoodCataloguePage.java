package com.codedecode.foodcatalogue.dto;

import java.util.List;

import com.codedecode.foodcatalogue.entity.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodCataloguePage {
    private List<FoodItem> foodItemList;
    private Restaurant restaurant;

}
