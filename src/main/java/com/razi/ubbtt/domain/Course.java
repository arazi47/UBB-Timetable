package com.razi.ubbtt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "day")
    private String day;

    @Column(name = "hours")
    private String hours;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "room")
    private String room;

    @Column(name = "group_or_year")
    private String groupOrYear;

    @Column(name = "type")
    private String type;

    @Column(name = "discipline")
    private String discipline;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "year")
    private int year;

    @Column(name = "semester")
    private int semester;

    public static Course parseFromFile(String unparsedCourseData) {
        String[] courseData = unparsedCourseData.split(",");
        return Course.builder()
                .day(courseData[0])
                .hours(courseData[1])
                .frequency(courseData[2])
                .room(courseData[3])
                .groupOrYear(courseData[4])
                .type(courseData[5])
                .discipline(courseData[6])
                .teacher(courseData[7]).build();
    }
}