package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String moodysRating;

    private String sendPRating;

    private String fitchRating;

    private Integer orderNumber;

    public Rating(String moodysRating, String sendPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sendPRating = sendPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
