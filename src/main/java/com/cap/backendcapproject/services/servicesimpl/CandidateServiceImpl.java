package com.cap.backendcapproject.services.servicesimpl;

import com.cap.backendcapproject.dto.AdminDto;
import com.cap.backendcapproject.dto.CandidateDto;
import com.cap.backendcapproject.entities.Admin;
import com.cap.backendcapproject.entities.Candidate;
import com.cap.backendcapproject.repository.CandidateRepository;
import com.cap.backendcapproject.services.CandidateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public CandidateDto createCandidate(CandidateDto candidateDto) {
        System.out.println("CandidateDto inside createCandidate : " + candidateDto);
        Candidate checkCandidate = candidateRepository.findByUsername(candidateDto.getUsername());
        System.out.println("checkCandidate : " + checkCandidate);

        if(checkCandidate != null){
            throw new RuntimeException(String.valueOf(HttpStatus.FOUND.value()));
        }

        Candidate candidateEntity = new Candidate();
        BeanUtils.copyProperties(candidateDto, candidateEntity);
        System.out.println("candidateEntity1 : " + candidateEntity);

        candidateEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(candidateDto.getPassword()));

        System.out.println("candidateEntity2 : " + candidateEntity);

        System.out.println("admin : " + candidateEntity.getEncryptedPassword());
        candidateEntity.setCreateAt(new Date());


        Candidate newCandidate = candidateRepository.save(candidateEntity);

        CandidateDto candidateDto1 = new CandidateDto();
        BeanUtils.copyProperties(newCandidate, candidateDto1);
        return candidateDto1;
    }

    @Override
    public CandidateDto getCandidate(String email) {
        return null;
    }

    @Override
    public CandidateDto getCandidateByUsername(String username) {
        Candidate candidateEntity = candidateRepository.findByUsername(username);

        if(candidateEntity == null){
            throw new UsernameNotFoundException(username);
        }

        CandidateDto candidateDto = new CandidateDto();

        BeanUtils.copyProperties(candidateEntity, candidateDto);
        System.out.println("candidateDto serviceImpl = " + candidateDto);
        return candidateDto;
    }

    @Override
    public CandidateDto updateCandidate(String username, CandidateDto candidateDto) {
        Candidate candidateEntity = candidateRepository.findByUsername(username);

        if(candidateEntity == null) throw new UsernameNotFoundException(username);

        if(candidateDto.getFirstName() != null) candidateEntity.setFirstName(candidateDto.getFirstName());
        if(candidateDto.getLastName() != null) candidateEntity.setLastName(candidateDto.getLastName());
        if(candidateDto.getEmail() != null) candidateEntity.setEmail(candidateDto.getEmail());
        if(candidateDto.getPassword() != null) candidateEntity.setPassword(candidateDto.getPassword());
        if(candidateDto.getAddress() != null) candidateEntity.setAddress(candidateDto.getAddress());
        if(candidateDto.getDateOfBirth() != null) candidateEntity.setDateOfBirth(candidateDto.getDateOfBirth());
        if(candidateDto.getGender() != null) candidateEntity.setGender(candidateDto.getGender());
        if(candidateDto.getPhone() != null) candidateEntity.setPhone(candidateDto.getPhone());
        if(candidateDto.getPicture() != null) candidateEntity.setPicture(candidateDto.getPicture());

        Candidate candidateUpdated = candidateRepository.save(candidateEntity);

        CandidateDto candidateDto1 = new CandidateDto();

        BeanUtils.copyProperties(candidateUpdated, candidateDto1);

        return candidateDto1;
    }

    @Override
    public void deleteCandidate(String username) {
        Candidate candidateEntity = candidateRepository.findByUsername(username);

        System.out.println("candidateEntity : " + candidateEntity);

        if(candidateEntity == null){
            throw new EntityNotFoundException("ce compte n'existe pas");
        }

        candidateRepository.delete(candidateEntity);
    }

    @Override
    public List<CandidateDto> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();

        List<CandidateDto> candidateDtoList = new ArrayList<>();

        for(Candidate c : candidates) {

            CandidateDto candidateDto = new CandidateDto() ;

            BeanUtils.copyProperties(c, candidateDto);

            candidateDtoList.add(candidateDto);
        }

        return candidateDtoList;
    }

    @Override
    public List<CandidateDto> getAllCandidates(int page, int limit) {
        if(page > 0) page -= 1;

        List<CandidateDto> candidateDtoList = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<Candidate> candidatePage = candidateRepository.findAll(pageableRequest);

        List<Candidate> candidates = candidatePage.getContent();

        for(Candidate c : candidates) {

            CandidateDto candidateDto = new CandidateDto() ;

            BeanUtils.copyProperties(c, candidateDto);

            candidateDtoList.add(candidateDto);
        }

        return candidateDtoList;
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        return false;
    }
}
