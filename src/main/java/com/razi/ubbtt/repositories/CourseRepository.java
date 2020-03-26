package com.razi.ubbtt.repositories;

import com.razi.ubbtt.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findById(int id);

    // Currently cannot get weekNumber TODO!!!
    @Query(value = "SELECT C FROM Course C WHERE (C.groupOrYear=(:group) OR C.groupOrYear=(:subgroup1) OR C.groupOrYear=(:subgroup2) OR C.groupOrYear=(:year)) AND C.semester = (:semester)")
    List<Course> getCoursesForGroupForCurrentSemesterAndWeek(@Param("group") String group, @Param("subgroup1") String subgroup1, @Param("subgroup2") String subgroup2, @Param("year") String year, @Param("semester") int semester);

    @Query(value = "SELECT C FROM Course C WHERE C.year=(:year) AND C.semester=(:semester)")
    List<Course> getCoursesByYearAndSemester(@Param("year") int year, @Param("semester") int semester);

    @Query(value = "SELECT C FROM Course C WHERE C.year=(:year) AND C.semester=(:semester) AND C.day=(:day)")
    List<Course> getCoursesByYearAndSemesterAndDay(@Param("year") int year, @Param("semester") int semester, @Param("day") String day);

    @Query(value = "SELECT C FROM Course C WHERE C.semester=(:semester)")
    List<Course> getCoursesBySemester(@Param("semester") int semester);

    @Query(value = "SELECT C.discipline FROM Course C WHERE C.year=(:year) OR C.semester=(:semester)")
    List<String> getDisciplinesByYearAndSemester(@Param("year") int year, @Param("semester") int semester);

    @Query(value = "SELECT DISTINCT C.discipline FROM Course C")
    Set<String> getAllDisciplines();

    /**
     *
     * @param semester 1 or 2
     * @return all disciplines from the selected semester
     *
     * When a teacher wants to add a note, we'll get all the disciplines from the semester we're currently in
     */
    @Query(value = "SELECT DISTINCT C.discipline FROM Course C WHERE C.semester=(:semester)")
    Set<String> getDisciplinesBySemester(@Param("semester") int semester);
}