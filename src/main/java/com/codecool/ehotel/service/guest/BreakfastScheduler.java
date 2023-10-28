package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BreakfastScheduler {
    public List<Set<Guest>> splitGuestsIntoBreakfastCycles(List<Guest> guests){
        int numberOfCycles = 8;

        List<Set<Guest>> breakfastCycles = new ArrayList<>(numberOfCycles);
        for(int i = 0; i < numberOfCycles; i++){
            breakfastCycles.add(new HashSet<>());
        }

        Random random = new Random();

        LocalTime startTime = LocalTime.of(6, 0);
        LocalTime endTime = LocalTime.of(10,0);

        for(Guest guest : guests){
            int cycleIndex = random.nextInt(numberOfCycles);
            LocalTime randomTime = startTime.plusMinutes((cycleIndex * 30));

            if (randomTime.isBefore(endTime)) {
                breakfastCycles.get(cycleIndex).add(guest);
            }
        }
        return breakfastCycles;
    }
}
