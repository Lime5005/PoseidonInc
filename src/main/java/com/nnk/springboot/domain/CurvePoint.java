package com.nnk.springboot.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Digits(integer = 20, fraction = 0)
    @NotNull(message = "CurveId is mandatory")
    @Min(value = 0L, message = "The value must be positive")
    private Integer curveId;

    @Digits(integer = 20, fraction = 2)
    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Numbers has to be present")
    private Double term;

    @Digits(integer = 20, fraction = 2)
    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Numbers has to be present")
    private Double value;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
