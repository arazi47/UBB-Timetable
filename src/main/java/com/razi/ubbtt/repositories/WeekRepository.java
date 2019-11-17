package com.razi.ubbtt.repositories;

import com.razi.ubbtt.domain.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("weekRepository")
public interface WeekRepository extends JpaRepository<Week, Long> {
    @Query(value = "SELECT W FROM Week W WHERE W.weekNumber=(:weekNumber) AND W.semester=(:semester)")
    Week findByWeekNumberAndSemester(@Param("weekNumber") int weekNumber, @Param("semester") int semester);

    Week getCurrentWeek();
    int getCurrentSemester();

    @Query(value = "SELECT W FROM Week W WHERE W.weekNumber >= (:weekNumber) AND W.semester = (:semester) ORDER BY W.weekNumber")
    Set<Week> getWeeksFromCurrentUntilTheEndOfTheSemester(@Param("weekNumber") int weekNumber, @Param("semester") int semester);

    @Query(value = "SELECT W FROM Week W WHERE W.semester = (:semester) ORDER BY W.weekNumber")
    Set<Week> getAllWeeksBySemesterOrderedByWeekNumber(@Param("semester") int semester);

    @Query(value = "SELECT W FROM Week W ORDER BY W.weekNumber")
    Set<Week> getAllWeeksOrderedByWeekNumber();
}
