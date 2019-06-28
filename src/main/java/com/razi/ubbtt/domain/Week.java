package com.razi.ubbtt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "week")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "week_number")
    private int weekNumber;

    @Column(name = "semester")
    private int semester;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date endDate;

    public static Week parseFromFile(String line) {
        try {
            Week week = new Week();
            week.setStartDate(new SimpleDateFormat("dd.MM.yyyy").parse(line.split("-")[0]));
            week.setEndDate(new SimpleDateFormat("dd.MM.yyyy").parse(line.split("-")[1]));

            return week;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
