package com.kancth03.techeerpartnersbackend.domain.restaurant.service;

import com.kancth03.techeerpartnersbackend.domain.restaurant.dto.*;
import com.kancth03.techeerpartnersbackend.domain.restaurant.entity.Restaurant;
import com.kancth03.techeerpartnersbackend.domain.restaurant.entity.RestaurantCategory;
import com.kancth03.techeerpartnersbackend.domain.restaurant.repository.RestaurantRepository;
import com.kancth03.techeerpartnersbackend.domain.restaurant.validate.RestaurantValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantValidate restaurantValidate;

    public AddRestaurantResponse addRestaurant(AddRestaurantRequest request) {
        restaurantValidate.duplicateValidate(request.name());

        Restaurant savedRestaurant = restaurantRepository.save(request.toEntity());
        return AddRestaurantResponse.entityToDto(savedRestaurant);
    }

    public ModifyCategoryResponse modifyCategory(ModifyCategoryRequest request) {
        // 레스토랑 id 존재 확인
        restaurantValidate.restaurantValidate(request.id());

        Restaurant restaurant = restaurantRepository.findById(request.id()).orElseThrow();
        RestaurantCategory oldCategory = restaurant.getCategory();

        restaurant.setCategory(request.category());
        // 쿼리 확인을 위해 바로 save
        restaurantRepository.save(restaurant);

        return new ModifyCategoryResponse(restaurant.getName(), oldCategory, restaurant.getCategory());
    }

    public List<FindRestaurantResponse> findRestaurantList() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        return restaurantList.stream()
                .map(FindRestaurantResponse::entityToDto)
                .toList();
    }

    public List<FindRestaurantResponse> findRestaurantList(RestaurantCategory category) {
        List<Restaurant> restaurantList = restaurantRepository.findAllByCategory(category);

        return restaurantList.stream()
                .map(FindRestaurantResponse::entityToDto)
                .toList();
    }

    public FindRestaurantResponse findRestaurant(Long restaurantId) {
        restaurantValidate.restaurantValidate(restaurantId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        return FindRestaurantResponse.entityToDto(restaurant);
    }

    public void deleteRestaurant(Long restaurantId) {
        restaurantValidate.restaurantValidate(restaurantId);

        restaurantRepository.deleteById(restaurantId);
    }
}