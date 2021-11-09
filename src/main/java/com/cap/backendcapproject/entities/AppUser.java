package com.cap.backendcapproject.entities;

import com.cap.backendcapproject.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String firstName;
    private String lastName;
    private String username;
    private String picture;
    private Date dateOfBirth;
    private Gender gender;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String encryptedPassword;
    private String phone;
    private String address;
    private boolean state;
    private boolean actived;
    @Column(nullable = false)
    private Date createAt;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new ArrayList<>();
}
