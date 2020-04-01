package com.razi.ubbtt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WeekCourseNote")
public class WeekCourseNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "week_id")
    private int weekId;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "note_id")
    private int noteId;
}
