package com.codedecode.foodcatalogue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codedecode.foodcatalogue.dto.FoodCataloguePage;
import com.codedecode.foodcatalogue.dto.FoodItemDTO;
import com.codedecode.foodcatalogue.dto.Restaurant;
import com.codedecode.foodcatalogue.entity.FoodItem;
import com.codedecode.foodcatalogue.mapper.FoodItemMapper;
import com.codedecode.foodcatalogue.repo.FoodItemRepo;


@Service
public class FoodCatalogueService {
    @Autowired
    FoodItemRepo foodItemRepo;

    @Autowired
    RestTemplate restTemplate;

    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem saved=foodItemRepo.save(FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO));
        return FoodItemMapper.INSTANCE.mapFoodToFoodItemDTO(saved);
    }

    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer restaurantId) {
        
        List<FoodItem> foodItemList=fetchFoodItemList(restaurantId);
        Restaurant restaurant=fetchRestaurantDetailsFromRestauMS(restaurantId);
        return createFoodCataloguePage(foodItemList,restaurant);
         
    }
                        
            private List<FoodItem> fetchFoodItemList(Integer restaurantId) {
                return foodItemRepo.findByRestaurantId(restaurantId);
            }
        
            private Restaurant fetchRestaurantDetailsFromRestauMS(Integer restaurantId) {
                return restTemplate.getForObject("http://RESTAURANT-SERVICE/restaurant/fetchById/"+restaurantId, Restaurant.class);
            }
        
            private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant) {
                FoodCataloguePage foodCataloguePage=new FoodCataloguePage();
                foodCataloguePage.setFoodItemList(foodItemList);
                foodCataloguePage.setRestaurant(restaurant);
                return foodCataloguePage;

            }


}
