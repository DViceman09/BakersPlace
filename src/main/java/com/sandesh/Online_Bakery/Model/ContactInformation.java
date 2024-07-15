package com.sandesh.Online_Bakery.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ContactInformation {
    private String mobile_number;
    private String email_id;
    private String twitter;
    private String instagram;
}
