package com.sandesh.Online_Bakery.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandesh.Online_Bakery.DTO.RestaurantDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long user_id;

    private String user_name;

    private String email;

    private String password;

    private USER_ROLE role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDTO> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address = new ArrayList<>();

}
