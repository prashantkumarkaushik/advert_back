package com.prashant.BlogApp.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
@Table
public class Role {
   
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

   
}
