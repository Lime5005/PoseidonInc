package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tradeId;

    @Pattern(regexp="^[A-Za-z]*$", message = "Input has to be text")
    @NotBlank(message = "Account is mandatory")
    private String account;

    @Pattern(regexp="^[A-Za-z]*$", message = "Input has to be text")
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Digits(integer = 20, fraction = 2)
    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Numbers has to be present")
    private Double buyQuantity;

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public Trade(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}
