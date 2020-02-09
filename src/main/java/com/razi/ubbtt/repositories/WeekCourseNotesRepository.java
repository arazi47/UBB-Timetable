package com.razi.ubbtt.repositories;

import com.razi.ubbtt.domain.WeekCourseNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WeekCourseNotesRepository extends JpaRepository<WeekCourseNotes, Long> {
}