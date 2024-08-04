package com.stock.market.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity(name = "Contracts")
@Data
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String contractCode;
    private String contractDescription;
    private String contractType;
}
