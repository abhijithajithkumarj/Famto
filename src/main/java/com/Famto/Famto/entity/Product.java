package com.Famto.Famto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product extends BaseEntity{

    private String productName;
    private Long quantity;
    private String Price;


    @ManyToOne
    private  User user;


}
