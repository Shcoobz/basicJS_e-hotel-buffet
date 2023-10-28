package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GuestServiceImpl implements GuestService{
    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {

        String Name = "Guest Name";
        Random random = new Random();
        GuestType[] guestTypes = GuestType.values();
        GuestType guestType = guestTypes[random.nextInt(guestTypes.length)];

        LocalDate checkIn = seasonStart.plusDays(random.nextInt((int) ChronoUnit.DAYS.between(seasonStart, seasonEnd)));
        LocalDate checkOut = checkIn.plusDays(random.nextInt(7));
        if(checkOut.isAfter(seasonEnd)){
            checkOut = seasonEnd;
        }


        return new Guest (Name, guestType, checkIn, checkOut);
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        Set<Guest> guestsForToday = new HashSet<>();
        for(Guest guest : guests){
            if(!date.isBefore(guest.checkIn()) && !date.isAfter(guest.checkOut())){
                guestsForToday.add(guest);
            }
        }
        return guestsForToday;
    }
}
