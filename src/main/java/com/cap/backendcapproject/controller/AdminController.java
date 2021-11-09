package com.cap.backendcapproject.controller;

import com.cap.backendcapproject.dto.AdminDto;
import com.cap.backendcapproject.exceptions.AdminException;
import com.cap.backendcapproject.request.AdminRequest;
import com.cap.backendcapproject.response.AdminResponse;
import com.cap.backendcapproject.response.enums.ErrorMessages;
import com.cap.backendcapproject.services.AdminService;
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
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/admins")
    public ResponseEntity<AdminResponse> createAdmin(@RequestBody @Valid AdminRequest adminRequest) throws Exception {

        if(adminRequest.getFirstName().isEmpty()) throw new AdminException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessages());

        AdminDto adminDto = new AdminDto();
        AdminResponse adminResponse = new AdminResponse();
        BeanUtils.copyProperties(adminRequest, adminDto);
        AdminDto CreatedAdminDto = new AdminDto();
        if((CreatedAdminDto = adminService.createAdmin(adminDto)) != null) {
            System.out.println("CreatedAdminDto" );
            //CreatedDoctorDto = doctorService.createDoctor(doctorDto);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        //if(CreatedDoctorDto == null)
        BeanUtils.copyProperties(CreatedAdminDto, adminResponse);
        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.CREATED);
        //return doctorRespense.getDoctorId();
    }

    @GetMapping("/admins/{username}")
    public AdminResponse getAdminByUsername(@PathVariable String username) {

//		modelMapper modelMapper = new ModelMapper();
        AdminDto adminDto = new AdminDto();

        AdminResponse adminResponse = new AdminResponse();
        try {

            adminDto = adminService.getAdminByUsername(username);

            BeanUtils.copyProperties(adminDto, adminResponse);

        }catch(IllegalStateException e) {
            e.printStackTrace();
        }

        return adminResponse;
    }

    @PutMapping("/admins/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(@PathVariable String id,@RequestBody AdminRequest adminRequest){

        AdminDto adminDto = new AdminDto();

        BeanUtils.copyProperties(adminRequest, adminDto);

        AdminDto updateAdmin = adminService.updateAdmin(id, adminDto);

        AdminResponse adminResponse = new AdminResponse();

        BeanUtils.copyProperties(updateAdmin, adminResponse);

        return new ResponseEntity<AdminResponse>(adminResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity <Map<String,Boolean>> deleteAdmin(@PathVariable String adminId){

        adminService.deleteAdmin(adminId);

        Map<String,Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);

        return  new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }
    //recevoir des données apartir de l'URL
    @GetMapping("/admins")
    public List<AdminResponse> getAllAdmins(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit){

        List<AdminResponse> userResponse = new ArrayList<>();

        List<AdminDto> adminds= adminService.getAllAdmins(page, limit);

        for(AdminDto doc : adminds) {

            AdminResponse admResp = new AdminResponse();
            BeanUtils.copyProperties(doc, admResp);
            userResponse.add(admResp);
        }

        return userResponse;

    }

}
