package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.*;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.ui.DisplayBreakfast;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public BreakfastManager() {
        this.buffetService = new BuffetServiceImpl();
        this.buffetManager = new BuffetManager();
        this.displayBreakfast = new DisplayBreakfast();
        this.totalUnhappyGuests = 0;
        this.totalWasteCost = 0;
    }

    public void serve() {

        displayBreakfast.initialGreeting();

        currentSimulatedTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 0));

        int numberOfGuests = 1000;

        for (int cycle = 1; cycle <= 9; cycle++) {
            displayBreakfast.printCycleStart(cycle, currentSimulatedTime);
            Set<Guest> guestsToday = getGuestsForToday(numberOfGuests);
            serveBreakfast(guestsToday, cycle);
            currentSimulatedTime = currentSimulatedTime.plusMinutes(30);
        }

        displaySessionMetrics();
    }

    private Set<Guest> getGuestsForToday(int numberOfGuests) {
        List<Guest> allGuests = generateGuestsForSeason(numberOfGuests);

        LocalDate today = LocalDate.now();
        GuestService guestService = new GuestServiceImpl();
        return guestService.getGuestsForDay(allGuests, today);
    }

    public void refillToMax() {
        Map<MealType, Integer> refillMap = new HashMap<>();

        // determine for each MealType how many portions are missing && put them in map
        for (MealType type : MealType.values()) {
            int currentCount = buffetManager.getCountOfMealType(type);
            int missingCount = 3 - currentCount; // determine how many are missing to reach max

            if (missingCount > 0) {
                refillMap.put(type, missingCount); // add missing count for current type
            }
        }

        // refill the buffet using the refillMap
        buffetService.refillBuffet(buffetManager, refillMap, currentSimulatedTime);
    }

    private List<Guest> generateGuestsForSeason(int numberOfGuests) {
        GuestService guestService = new GuestServiceImpl();
        LocalDate seasonStart = LocalDate.now().withDayOfYear(1); // start of year
        LocalDate seasonEnd = LocalDate.now().withDayOfYear(365); // end of year

        List<Guest> allGuests = new ArrayList<>();
        for (int i = 0; i < numberOfGuests; i++) {
            allGuests.add(guestService.generateRandomGuest(seasonStart, seasonEnd));
        }
        return allGuests;
    }

    private void guestConsumesPreferredMeal(Guest guest, BuffetManager buffet, List<String> consumedNames, List<String> consumedMeals, List<String> notConsumedNames, List<String> notConsumedMeals) {
        GuestType guestType = guest.guestType();
        List<MealType> preferredMeals = guestType.getMealPreferences();
        //boolean satisfied = false;
        // String mealName;

        for (MealType meal : preferredMeals) {
            if (buffet.getCountOfMealType(meal) > 0) {
                buffet.consumeFreshest(meal);
                consumedNames.add(guest.name());
                consumedMeals.add(meal.toString());
                //satisfied = true;
                break;
            } else {
                notConsumedNames.add(guest.name());
                notConsumedMeals.add(meal.toString());
                // mealName = meal.toString();
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
            GuestType guestType = guest.guestType();
            List<MealType> preferredMeals = guestType.getMealPreferences();
            boolean satisfied = false;

            guestConsumesPreferredMeal(guest, buffetManager, consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);

  /*    for (MealType meal : preferredMeals) {
        if (buffetManager.getCountOfMealType(meal) > 0) {

        }
      }*/
        }

        displayBreakfast.printMealsInTableFormat(consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);
    }

    private void serveBreakfast(Set<Guest> guestsToday, int cycle) {
        // refill
        refillToMax();
        displayBreakfast.showBreakfastMenu(buffetManager);

        // consume
        consumeMeal(guestsToday);

        // discard
        discardOldMeals(cycle);

        if (cycle == 9) {
            discardEndOfDayMeals();
        }
    }

    private void discardOldMeals(int currentCycle) {
        // calculate the discard time for short duration meals
        LocalDateTime discardShortBeforeTime = currentSimulatedTime.minusMinutes(MealDurability.SHORT.getDurationInMinutes()).plusMinutes(1);
        discardMealsOfDurability(MealDurability.SHORT, discardShortBeforeTime);

        // discard MEDIUM meals after cycle 5 (2.5 hours after 6:00 AM)
        if (currentCycle >= 5) {
            LocalDateTime discardMediumBeforeTime = currentSimulatedTime.minusMinutes(MealDurability.MEDIUM.getDurationInMinutes());
            discardMealsOfDurability(MealDurability.MEDIUM, discardMediumBeforeTime);
        }
    }

    private void discardEndOfDayMeals() {
        DiscardedMealsResult shortDiscardResult = discardMealsOfDurability(MealDurability.SHORT, currentSimulatedTime.toLocalDate().atTime(23, 59));
        //displayBreakfast.displayDiscardedMealsAndCost(shortDiscardResult);

        DiscardedMealsResult mediumDiscardResult = discardMealsOfDurability(MealDurability.MEDIUM, currentSimulatedTime.toLocalDate().atTime(23, 59));
        //displayBreakfast.displayDiscardedMealsAndCost(mediumDiscardResult);
    }

    private DiscardedMealsResult discardMealsOfDurability(MealDurability durability, LocalDateTime beforeTime) {
        DiscardedMealsResult discardedResult = buffetService.collectWaste(buffetManager, durability, beforeTime);

        if (discardedResult.getDiscardedMeals().isEmpty()) {
            displayBreakfast.printNoMealsToDiscardMessage(durability);
        } else {
            displayBreakfast.displayDiscardedMealsAndCost(durability, discardedResult);

            totalWasteCost += discardedResult.getTotalCost();
        }
        return discardedResult;
    }

    private void displaySessionMetrics() {
        System.out.println("Session Metrics:");
        System.out.println("Total Unhappy Guests: " + totalUnhappyGuests);
        System.out.println("Total Waste Cost: $" + totalWasteCost);
    }

}
