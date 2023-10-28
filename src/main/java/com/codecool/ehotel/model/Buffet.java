package com.codecool.ehotel.model;

import com.codecool.ehotel.service.buffet.BuffetMealPortion;

import java.util.Map;
import java.util.List;

public record Buffet(Map<MealType, List<BuffetMealPortion>> meals) { }
