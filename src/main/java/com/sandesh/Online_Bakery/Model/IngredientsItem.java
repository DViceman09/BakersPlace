/*
package com.sandesh.Online_Bakery.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ingredient_id;

    private String ingredient_name;

    @ManyToOne
    private IngredientCategory category;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    private boolean isStock = true;
}
*/

