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
        try {
            Date todayDate = new SimpleDateFormat("dd.MM.yyyy").parse(today);
            for (Week w: weekRepository.findAll()) {
                if (todayDate.compareTo(w.getStartDate()) >= 0 && todayDate.compareTo(w.getEndDate()) <= 0)
                    return w;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weekRepository.findAll().get(0);
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
