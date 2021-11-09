package com.cap.backendcapproject.entities;

import com.cap.backendcapproject.entities.enums.ActivityArea;
import com.cap.backendcapproject.entities.enums.Poste;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@DiscriminatorValue("candidats")
public class Candidate extends AppUser{

    private Poste poste;
    private ActivityArea activityArea;

}
