package com.cap.backendcapproject.controller;

import com.cap.backendcapproject.dto.AdminDto;
import com.cap.backendcapproject.dto.CandidateDto;
import com.cap.backendcapproject.exceptions.AdminException;
import com.cap.backendcapproject.exceptions.CandidateException;
import com.cap.backendcapproject.request.AdminRequest;
import com.cap.backendcapproject.request.CandidateRequest;
import com.cap.backendcapproject.response.AdminResponse;
import com.cap.backendcapproject.response.CandidateResponse;
import com.cap.backendcapproject.response.enums.ErrorMessages;
import com.cap.backendcapproject.services.CandidateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @PostMapping("/candidates")
    public ResponseEntity<CandidateResponse> createCandidate(@RequestBody @Valid CandidateRequest candidateRequest) throws Exception {

        if(candidateRequest.getFirstName().isEmpty()) throw new CandidateException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());

        CandidateDto candidateDto = new CandidateDto();
        CandidateResponse candidateResponse = new CandidateResponse();
        BeanUtils.copyProperties(candidateRequest, candidateDto);
        CandidateDto CreatedCandidateDto = new CandidateDto();
        if((CreatedCandidateDto = candidateService.createCandidate(candidateDto)) != null) {
            System.out.println("CreatedAdminDto" );
            //CreatedDoctorDto = doctorService.createDoctor(doctorDto);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        //if(CreatedDoctorDto == null)
        BeanUtils.copyProperties(CreatedCandidateDto, candidateResponse);
        return new ResponseEntity<CandidateResponse>(candidateResponse, HttpStatus.CREATED);
        //return doctorRespense.getDoctorId();
    }

    @GetMapping("/candidates/{username}")
    public CandidateResponse getCandidateByUsername(@PathVariable String username) {

//		modelMapper modelMapper = new ModelMapper();
        CandidateDto candidateDto = new CandidateDto();

        CandidateResponse candidateResponse = new CandidateResponse();
        try {

            candidateDto = candidateService.getCandidateByUsername(username);

            BeanUtils.copyProperties(candidateDto, candidateResponse);

        }catch(IllegalStateException e) {
            e.printStackTrace();
        }

        return candidateResponse;
    }

    @PutMapping("/candidates/{id}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable String id,@RequestBody CandidateRequest candidateRequest){

        CandidateDto candidateDto = new CandidateDto();

        BeanUtils.copyProperties(candidateRequest, candidateDto);

        CandidateDto updateCandidate = candidateService.updateCandidate(id, candidateDto);

        CandidateResponse candidateResponse = new CandidateResponse();

        BeanUtils.copyProperties(updateCandidate, candidateResponse);

        return new ResponseEntity<CandidateResponse>(candidateResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/candidates/{username}")
    public ResponseEntity <Map<String,Boolean>> deleteCandidate(@PathVariable String username){

        candidateService.deleteCandidate(username);

        Map<String,Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return  new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }
    //recevoir des données apartir de l'URL
    @GetMapping("/candidates")
    public List<CandidateResponse> getAllCandidates(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "limit", defaultValue = "10") int limit){

        List<CandidateResponse> userResponse = new ArrayList<>();

        List<CandidateDto> candidates= candidateService.getAllCandidates(page, limit);

        for(CandidateDto doc : candidates) {

            CandidateResponse candidateResponse = new CandidateResponse();
            BeanUtils.copyProperties(doc, candidateResponse);
            userResponse.add(candidateResponse);
        }

        return userResponse;

    }
}
