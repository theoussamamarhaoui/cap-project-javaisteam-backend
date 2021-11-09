package com.cap.backendcapproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor @ToString @Data
@DiscriminatorValue("admins")
public class Admin extends AppUser{

    private String organization;

}
