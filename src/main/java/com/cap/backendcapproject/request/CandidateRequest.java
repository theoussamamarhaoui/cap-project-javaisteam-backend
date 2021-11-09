package com.cap.backendcapproject.request;

import com.cap.backendcapproject.entities.enums.ActivityArea;
import com.cap.backendcapproject.entities.enums.Poste;
import com.cap.backendcapproject.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CandidateRequest extends UserRequest {
    private Poste poste;
    private ActivityArea activityArea;
}
