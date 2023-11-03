package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.*;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.ui.DisplayBreakfast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class BreakfastManager {
  private BuffetService buffetService;
  private BuffetManager buffetManager;
  private DisplayBreakfast displayBreakfast;
  private LocalDateTime currentSimulatedTime;
  private int totalUnhappyGuests;
  private int totalWasteCost;
  private static final int TOTAL_CYCLES = 9;
  private static final int NUMBER_OF_GUESTS = 1000;
  private static final int MAX_MEAL_COUNT = 3;
  private static final LocalTime OPENING_TIME = LocalTime.of(6, 0);
  private static final int DISCARD_CYCLE_THRESHOLD = 5;
  private static final int MINUTES_PER_CYCLE = 30;

  public BreakfastManager() {
    this.buffetService = new BuffetServiceImpl();
    this.buffetManager = new BuffetManager();
    this.displayBreakfast = new DisplayBreakfast();
    this.totalUnhappyGuests = 0;
    this.totalWasteCost = 0;
  }

  public void serve() {

    displayBreakfast.initialGreeting();

    currentSimulatedTime = LocalDateTime.of(LocalDate.now(),OPENING_TIME);

    for (int cycle = 1; cycle <= TOTAL_CYCLES; cycle++) {
      displayBreakfast.printCycleStart(cycle, currentSimulatedTime);
      Set<Guest> guestsToday = getGuestsForToday();
      serveBreakfast(guestsToday, cycle);
      currentSimulatedTime = currentSimulatedTime.plusMinutes(MINUTES_PER_CYCLE);
    }

    displayBreakfast.displaySessionMetrics(totalUnhappyGuests, totalWasteCost);
  }

  private Set<Guest> getGuestsForToday() {
    List<Guest> allGuests = generateGuestsForSeason();

    LocalDate today = LocalDate.now();
    GuestService guestService = new GuestServiceImpl();
    return guestService.getGuestsForDay(allGuests, today);
  }

  public void refillToMax() {
    Map<MealType, Integer> refillMap = new HashMap<>();

    // determine for each MealType how many portions are missing && put them in map
    for (MealType type : MealType.values()) {
      int currentCount = buffetManager.getCountOfMealType(type);
      int missingCount = MAX_MEAL_COUNT - currentCount; // determine how many are missing to reach max

      if (missingCount > 0) {
        refillMap.put(type, missingCount); // add missing count for current type
      }
    }

    // refill the buffet using the refillMap
    buffetService.refillBuffet(buffetManager, refillMap, currentSimulatedTime);
  }

  private List<Guest> generateGuestsForSeason() {
    GuestService guestService = new GuestServiceImpl();
    LocalDate seasonStart = LocalDate.now().withDayOfYear(1); // start of year
    LocalDate seasonEnd = LocalDate.now().withDayOfYear(365); // end of year

    List<Guest> allGuests = new ArrayList<>();
    for (int i = 0; i < NUMBER_OF_GUESTS; i++) {
      allGuests.add(guestService.generateRandomGuest(seasonStart, seasonEnd));
    }
    return allGuests;
  }

  private void guestConsumesPreferredMeal(Guest guest, BuffetManager buffet, List<String> consumedNames, List<String> consumedMeals, List<String> notConsumedNames, List<String> notConsumedMeals) {
    GuestType guestType = guest.guestType();
    List<MealType> preferredMeals = guestType.getMealPreferences();

    for (MealType meal : preferredMeals) {
      if (buffet.getCountOfMealType(meal) > 0) {
        buffet.consumeFreshest(meal);
        consumedNames.add(guest.name());
        consumedMeals.add(meal.toString());
        break;
      } else {
        notConsumedNames.add(guest.name());
        notConsumedMeals.add(meal.toString());
        totalUnhappyGuests++;
      }
    }
  }

  public void consumeMeal(Set<Guest> guests) {
    List<String> consumedNames = new ArrayList<>();
    List<String> consumedMeals = new ArrayList<>();
    List<String> notConsumedNames = new ArrayList<>();
    List<String> notConsumedMeals = new ArrayList<>();

    for (Guest guest : guests) {
      guestConsumesPreferredMeal(guest, buffetManager, consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);
    }

    displayBreakfast.showConsumedMeals(consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);
  }

  private void serveBreakfast(Set<Guest> guestsToday, int cycle) {
    // refill
    refillToMax();
    displayBreakfast.showBreakfastMenu(buffetManager);

    // consume
    consumeMeal(guestsToday);

    // discard
    discardOldMeals(cycle);

    if (cycle == TOTAL_CYCLES) {
      discardEndOfDayMeals();
    }
  }

  private void discardOldMeals(int currentCycle) {
    // calculate the discard time for short duration meals
    LocalDateTime discardShortBeforeTime = currentSimulatedTime.minusMinutes(MealDurability.SHORT.getDurationInMinutes()).plusMinutes(1);
    DiscardedMealsResult shortDiscardResult = discardMealsOfDurability(MealDurability.SHORT, discardShortBeforeTime);

    DiscardedMealsResult mediumDiscardResult = new DiscardedMealsResult(new ArrayList<>(), 0); // initialize with empty result

    // discard MEDIUM meals after cycle 5 (2.5 hours after 6:00 AM)
    if (currentCycle >= DISCARD_CYCLE_THRESHOLD) {
      LocalDateTime discardMediumBeforeTime = currentSimulatedTime.minusMinutes(MealDurability.MEDIUM.getDurationInMinutes());
      mediumDiscardResult = discardMealsOfDurability(MealDurability.MEDIUM, discardMediumBeforeTime);
    }

    displayBreakfast.displayDiscardedMealsAndCostSideBySide(MealDurability.SHORT, shortDiscardResult, MealDurability.MEDIUM, mediumDiscardResult);
  }

  private void discardEndOfDayMeals() {
    displayBreakfast.stageEndOfDayDiscard();

    DiscardedMealsResult shortDiscardResult = discardMealsOfDurability(MealDurability.SHORT, currentSimulatedTime.toLocalDate().atTime(23, 59));
    DiscardedMealsResult mediumDiscardResult = discardMealsOfDurability(MealDurability.MEDIUM, currentSimulatedTime.toLocalDate().atTime(23, 59));

    displayBreakfast.displayDiscardedMealsAndCostSideBySide(MealDurability.SHORT, shortDiscardResult, MealDurability.MEDIUM, mediumDiscardResult);
  }

  private DiscardedMealsResult discardMealsOfDurability(MealDurability durability, LocalDateTime beforeTime) {
    DiscardedMealsResult discardedResult = buffetService.collectWaste(buffetManager, durability, beforeTime);

    // if there are discarded meals, update the total waste cost
    if (!discardedResult.getDiscardedMeals().isEmpty()) {
      totalWasteCost += discardedResult.getTotalCost();
    }

    return discardedResult;
  }

}
