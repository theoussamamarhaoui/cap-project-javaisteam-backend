package com.cap.backendcapproject.services;

import com.cap.backendcapproject.dto.AdminDto;
import com.cap.backendcapproject.dto.CandidateDto;

import java.util.List;

public interface CandidateService {
    CandidateDto createCandidate(CandidateDto candidateDto);

    CandidateDto getCandidate(String email);

    CandidateDto getCandidateByUsername(String username) ;

    CandidateDto updateCandidate(String username, CandidateDto candidateDto);

    void deleteCandidate(String username);


    List<CandidateDto> getAllCandidates();

    List<CandidateDto> getAllCandidates(int page, int limit);

    boolean changePassword(String email, String newPassword);
}
