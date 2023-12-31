package com.codecool.ehotel.model;

import com.codecool.ehotel.service.buffet.BuffetMealPortion;

import java.util.Map;
import java.util.List;

/**
 * Represents a buffet in a hotel. This record encapsulates a collection of meals,
 * categorized by meal types. Each meal type is associated with a list of buffet meal portions.
 */
public record Buffet(Map<MealType, List<BuffetMealPortion>> meals) {
  /**
   * Retrieves the buffet meals categorized by their types.
   *
   * @return A map where each key is a MealType and the corresponding value is a list of BuffetMealPortion objects.
   */
  // The getter method for 'meals' is implicitly provided by the record.
}
