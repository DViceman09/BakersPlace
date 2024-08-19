package com.sandesh.Online_Bakery.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long food_id;

    private String name;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;
}
