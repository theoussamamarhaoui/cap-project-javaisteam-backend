package com.cap.backendcapproject.dto;

import com.cap.backendcapproject.entities.enums.ActivityArea;
import com.cap.backendcapproject.entities.enums.Poste;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CandidateDto extends UserDto{
    private Poste poste;
    private ActivityArea activityArea;

}
