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
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "week_number")
    private int weekNumber;

    @Column(name = "semester")
    private int semester;

    @Column(name = "discipline")
    private String discipline;

    @Column(name = "course_type")
    private String courseType;

    @Column(name = "group_or_year")
    private String groupOrYear;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "author")
    private String author;

    @Column(name = "type")
    @Builder.Default
    private String type = "1";
}
