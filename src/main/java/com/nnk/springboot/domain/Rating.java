package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Moodys Rating is mandatory")
    private String moodysRating;

    @NotBlank(message = "SendP Rating is mandatory")
    private String sendPRating;

    @NotBlank(message = "Fitch Rating is mandatory")
    private String fitchRating;

    private Integer orderNumber;

    public Rating(String moodysRating, String sendPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sendPRating = sendPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
