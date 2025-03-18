package com.codedecode.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codedecode.order.dto.OrderDTO;
import com.codedecode.order.dto.OrderDTOFromFE;
import com.codedecode.order.dto.UserDTO;
import com.codedecode.order.entity.Order;
import com.codedecode.order.mapper.OrderMapper;
import com.codedecode.order.repo.OrderRepo;

@Service
public class OrderService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    SequenceGenerator sequenceGenerator;

    public OrderDTO saveOrderInDb(OrderDTOFromFE orderDetails) {
        Integer newOrderId =sequenceGenerator.generateNextOrderId();
        UserDTO userDTO=fetchUserDetailsFromUserId(orderDetails.getUserId());
                Order orderToBeSaved=new Order(newOrderId, orderDetails.getFoodItemsList(), orderDetails.getRestaurant(),userDTO );
                orderRepo.save(orderToBeSaved);
                return OrderMapper.INSTANCE.mapOrderToOrderDTO(orderToBeSaved);
            }
        
            private UserDTO fetchUserDetailsFromUserId(Integer userId) {
                return restTemplate.getForObject("http://USER-SERVICE/user/fetchUserById/"+userId, UserDTO.class);
            }

    
}
