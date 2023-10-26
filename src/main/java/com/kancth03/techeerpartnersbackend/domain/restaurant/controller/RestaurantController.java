package com.kancth03.techeerpartnersbackend.domain.restaurant.controller;

import com.kancth03.techeerpartnersbackend.domain.restaurant.dto.AddRestaurantRequest;
import com.kancth03.techeerpartnersbackend.domain.restaurant.dto.AddRestaurantResponse;
import com.kancth03.techeerpartnersbackend.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("")
    public AddRestaurantResponse addRestaurant(@RequestBody AddRestaurantRequest request) {
        // 레스토랑에는 레스토랑명, 레스토랑 카테고리 (한식, 중식, 일식 등)의 내용이 있습니다.
        // 등록한 날짜를 기록해야 합니다.
        return restaurantService.addRestaurant(request);
    }
}