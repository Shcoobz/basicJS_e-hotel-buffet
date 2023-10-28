package com.codecool.ehotel;

import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetManager;
import com.codecool.ehotel.service.buffet.BuffetRefillSpecification;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.ui.DisplayBreakfast;

import java.util.Map;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EHotelBuffetApplication {

    public static void main(String[] args) {

        // Initialize services
        GuestService guestService = new GuestServiceImpl();

    // initialize BreakfastBuffet
    BuffetService buffetService = new BuffetServiceImpl();

    // Create initial buffet
    BuffetManager buffetManager = buffetService.createSampleBuffet();

    // Display initial buffet
    DisplayBreakfast display = new DisplayBreakfast();
    System.out.println("\nInitial Breakfast Buffet:");
    display.showBreakfastMenu(buffetManager);

    // Refill the buffet using the specification
    Map<MealType, Integer> refillSpec = BuffetRefillSpecification.getSampleRefillSpecification();
    buffetService.refillBuffet(buffetManager, refillSpec);
        // Define the "season" date limits
        LocalDate seasonStart = LocalDate.of(2023, 11, 1);
        LocalDate seasonEnd = LocalDate.of(2024, 11, 5);

    // Generate guests for the season

    // Display the updated buffet
    System.out.println("\nUpdated Breakfast Buffet:");
    display.showBreakfastMenu(buffetManager);
        // Generate guests for the season
        List<Guest> guests = generateGuestsForSeason(guestService, seasonStart, seasonEnd);

        // Run breakfast buffet
        LocalDate currentDate = seasonStart;

        while (!currentDate.isAfter(seasonEnd)) {
            Set<Guest> guestsForToday = guestService.getGuestsForDay(guests, currentDate);
            currentDate = currentDate.plusDays(1);
        }
        System.out.println(guests);
    }

    private static List<Guest> generateGuestsForSeason(GuestService guestService, LocalDate seasonStart, LocalDate seasonEnd) {
        List<Guest> generatedGuests = new ArrayList<>();
        int numberOfGuestsToGenerate = 30;
        for (int i = 0; i < numberOfGuestsToGenerate; ++i) {
            Guest guest = guestService.generateRandomGuest(seasonStart, seasonEnd);
            generatedGuests.add(guest);
        }
        return generatedGuests;
    }
}
