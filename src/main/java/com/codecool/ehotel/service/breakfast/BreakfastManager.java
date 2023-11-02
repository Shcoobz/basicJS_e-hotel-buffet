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
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BreakfastManager {

    private BuffetService buffetService;
    private BuffetManager buffetManager;
    private DisplayBreakfast displayBreakfast;

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

        LocalTime cycleStartTime = LocalTime.of(6, 0);  // start at 6:00 AM
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");  // to format the time to 6:00 AM
        int numberOfGuests = 1000;

        for (int cycle = 1; cycle <= 8; cycle++) {
            System.out.println("\nStarting cycle: " + cycle + " - " + cycleStartTime.format(timeFormatter) + "\n");

            Set<Guest> guestsToday = getGuestsForToday(numberOfGuests);

            serveBreakfast(guestsToday);

            cycleStartTime = cycleStartTime.plusMinutes(30);  // move to the next half hour for the next cycle
        }
        System.out.println("Total Unhappy Guests: " + totalUnhappyGuests);
        System.out.println("Total Waste Cost: $" + totalWasteCost);
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
        buffetService.refillBuffet(buffetManager, refillMap);
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
        boolean satisfied = false;
        String mealName;

        for (MealType meal : preferredMeals) {
            if (buffet.getCountOfMealType(meal) > 0) {
                buffet.consumeFreshest(meal);
                consumedNames.add(guest.name());
                consumedMeals.add(meal.toString());
                satisfied = true;
                break;
            } else {
                notConsumedNames.add(guest.name());
                notConsumedMeals.add(meal.toString());
                mealName = meal.toString();
            } ;
        }
        if (!satisfied) {
            notConsumedNames.add(guest.name());
            notConsumedMeals.add("No preferred meal available");
            totalUnhappyGuests++;


        }
    }

    private void printMealsInTableFormat(List<String> consumedNames, List<String> consumedMeals, List<String> notConsumedNames, List<String> notConsumedMeals) {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.format("| %-43s | %-43s |\n", "Consumed", "Not Consumed");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.format("| %-20s | %-20s | %-20s | %-20s |\n", "Name", "Meal", "Name", "Meal");
        System.out.println("---------------------------------------------------------------------------------------------");

        int maxRows = Math.max(consumedNames.size(), notConsumedNames.size());
        for (int i = 0; i < maxRows; i++) {
            String consumedName = (i < consumedNames.size()) ? consumedNames.get(i) : "";
            String consumedMeal = (i < consumedMeals.size()) ? consumedMeals.get(i) : "";
            String notConsumedName = (i < notConsumedNames.size()) ? notConsumedNames.get(i) : "";
            String notConsumedMeal = (i < notConsumedMeals.size()) ? notConsumedMeals.get(i) : "";

            System.out.format("| %-20s | %-20s | %-20s | %-20s |\n",
                    consumedName,
                    consumedMeal,
                    notConsumedName,
                    notConsumedMeal);
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }

/*  public void consumeMeal(Set<Guest> guests) {
    for (Guest guest : guests) {
      guestConsumesPreferredMeal(guest, buffetManager);
    }
  }*/

    public void consumeMeal(Set<Guest> guests) {
        List<String> consumedNames = new ArrayList<>();
        List<String> consumedMeals = new ArrayList<>();
        List<String> notConsumedNames = new ArrayList<>();
        List<String> notConsumedMeals = new ArrayList<>();

        for (Guest guest : guests) {
            GuestType guestType = guest.guestType();
            List<MealType> preferredMeals = guestType.getMealPreferences();
            boolean satisfied = false;
            //guestConsumesPreferredMeal(guest, buffetManager, consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);

            for (MealType meal : preferredMeals) {
                if (buffetManager.getCountOfMealType(meal) > 0) {

                }
            }
        }

        printMealsInTableFormat(consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);
    }

    private void serveBreakfast(Set<Guest> guestsToday) {
        refillToMax();
        displayBreakfast.showBreakfastMenu(buffetManager);
        consumeMeal(guestsToday);

        // discard
        // TODO: Implement discard logic here
    }

    private void calculateWasteCost() {
        Map<MealType, Integer> wastedCounts = buffetManager.calculateWasteCounts();
        int totalCost = 0;

        for (MealType type : wastedCounts.keySet()) {
            int wasteCount = wastedCounts.get(type);
            totalCost += wasteCount * type.getCost();
        }

        totalWasteCost += totalCost;
    }

    private void displaySessionMetrics() {
        System.out.println("Session Metrics:");
        System.out.println("Total Unhappy Guests: " + totalUnhappyGuests);
        System.out.println("Total Waste Cost: $" + totalWasteCost);
    }
}

