package com.razi.ubbtt.repositories;

import com.razi.ubbtt.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("noteRepository")
public interface NoteRepository extends JpaRepository<Note, Long> {
    //List<Note> getNotesBySemesterAndWeekNumberAndGr(int semester, int weekNumber);

    @Query(value = "SELECT N FROM Note N WHERE N.semester = (:semester) AND N.weekNumber = (:weekNumber) AND N.groupOrYear in :groupOrYear")
    Set<Note> getNotesForCurrentSemesterAndWeekAndGroupOrYear(@Param("semester") int semester, @Param("weekNumber") int weekNumber, @Param("groupOrYear") List<String> groupOrYear);

    @Query(value = "SELECT N FROM Note N WHERE N.author = (:author)")
    Set<Note> findAllByAuthor(@Param("author") String author);
}