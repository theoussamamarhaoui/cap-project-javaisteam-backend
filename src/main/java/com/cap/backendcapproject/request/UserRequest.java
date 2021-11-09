package com.cap.backendcapproject.request;

import com.cap.backendcapproject.entities.AppRole;
import com.cap.backendcapproject.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
    @NotNull(message="le champ prénom ne doit pas être null !")
    @Size(min = 3)
    private String firstName;
    @NotNull(message="le champ nom ne doit pas être null !")
    @Size(min=2, message="le champ doit avoir au moins 3 Caracteres !")
    private String lastName;
    private String username;
    private String picture;
    private Date dateOfBirth;
    private Gender gender;
    @NotNull(message="le champ email ne doit pas être null !")
    @Email
    private String email;
    @NotNull(message="le champ mot de passe ne doit pas être null !")
    @Size(min=6, max= 14)
    private String password;
    private String phone;
    private String address;
    private boolean state;
    private Date createAt;
    private Collection<AppRole> roles = new ArrayList<>();

}
