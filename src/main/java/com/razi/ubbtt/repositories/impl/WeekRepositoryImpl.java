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

    public Week getCurrentWeek() {
        String today = "9.03.2020";
        Date todayDate = null;
        try {
            todayDate = new SimpleDateFormat("dd.MM.yyyy").parse(today);
            for (Week w: weekRepository.findAll()) {
                if (todayDate.compareTo(w.getStartDate()) >= 0 && todayDate.compareTo(w.getEndDate()) <= 0) {
                    return w;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // return closest next week
        return getNextWeek(todayDate);
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
}
