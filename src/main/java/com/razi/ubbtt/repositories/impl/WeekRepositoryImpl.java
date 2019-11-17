package com.razi.ubbtt.repositories.impl;

import com.razi.ubbtt.domain.Week;
import com.razi.ubbtt.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WeekRepositoryImpl {
    @Autowired
    private WeekRepository weekRepository;

    /**
     *
     * @return the current week of the semester based on the current day
     */
    public Week getCurrentWeek() {
        String today = "9.03.2020";
        //String today = "23.01.2020";
        Date todayDate = null;
        try {
            todayDate = new SimpleDateFormat("dd.MM.yyyy").parse(today);
            for (Week w: weekRepository.findAll()) {
                if (todayDate.compareTo(w.getStartDate()) >= 0 && todayDate.compareTo(w.getEndDate()) <= 0) {
                    System.out.println("FOUND SEM = " + w.getSemester() + "; weekno = " + w.getWeekNumber());
                    return w;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // return closest next week
        return getNextWeek(todayDate);
    }

    /*
        TODO maybe in the future implement this:
            if it's a holiday, all lectures are canceled

        TODO if there's a note on a particular lecture already send an error to the user
     */
    public boolean isHoliday(Date today) {
        //try {
            //Date holidayDate = new SimpleDateFormat("dd.MM.yyyy").parse("25.12.2019");
            //List<Date> holidays = new ArrayList<>();
            //holidays.add(holidayDate);

            // Check if today is in holiday
            //for (Date d: holidays) {
            //    if (today.compareTo(d) >= 0 && today.compareTo())
            //}

        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}

        return false;
    }

    public Week getNextWeek(Date today) {
        Set<Week> weeks = weekRepository.getAllWeeksOrderedByWeekNumber();
        for (Week w: weeks) {
            // if we're just before that week
            // then that's the next one
            if (today.compareTo(w.getStartDate()) <= 0) {
                return w;
            }
        }

        // year's over probably
        List<Week> weekList = new ArrayList<>(weeks);
        return weekList.get(weekList.size() - 1);
    }

    public int getCurrentSemester() {
        return getCurrentWeek().getSemester();
    }

    /*
    public Set<Week> getWeeksFromCurrentUntilTheEndOfTheSemester() {
        int currWeekNumber = getCurrentWeek().getWeekNumber();
        //Set<Week> weeks = new HashSet<>();
        //for (Week week: weekRepository.findAll()) {
         //   if (week.getWeekNumber() >= currWeekNumber)
          //      weeks.add(week);
       // }

        //List<Week> sortedWeeks = new ArrayList<>(weeks);
        //sortedWeeks.sort(Comparator.comparingInt(Week::getWeekNumber));

        //return new HashSet<>(sortedWeeks);
    }
    */
}
