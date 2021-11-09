package com.cap.backendcapproject.dto;

import com.cap.backendcapproject.entities.AppRole;
import com.cap.backendcapproject.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class UserDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String picture;
    private Date dateOfBirth;
    private Gender gender;
    private String email;
    private String password;
    private String encryptedPassword;
    private String phone;
    private String address;
    private boolean state;
    private boolean actived;
    private Date createAt;
    private Collection<AppRole> roles = new ArrayList<>();

}
