package com.cap.backendcapproject.response;

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
public class CandidateResponse extends UserResponse{
    private Poste poste;
    private ActivityArea activityArea;
}
