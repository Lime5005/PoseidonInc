package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer BidListId;

    @Pattern(regexp="^[A-Za-z]*$", message = "Input has to be text")
    @NotBlank(message = "Account is mandatory")
    @Column(name = "account", unique = true)
    private String account;

    @Pattern(regexp="^[A-Za-z]*$", message = "Input has to be text")
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Digits(integer = 20, fraction = 2)
    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Numbers has to be present")
    private Double bidQuantity;

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}
