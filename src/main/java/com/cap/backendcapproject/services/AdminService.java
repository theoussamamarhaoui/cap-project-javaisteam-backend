package com.cap.backendcapproject.services;

import com.cap.backendcapproject.dto.AdminDto;
import com.cap.backendcapproject.entities.Admin;

import java.util.List;

public interface AdminService {
    AdminDto createAdmin(AdminDto adminDto);

    AdminDto getAdmin(String email);

    AdminDto getAdminByUsername(String username) ;

    AdminDto updateAdmin(String username, AdminDto adminDto);

    void deleteAdmin(String username);


    List<AdminDto> getAllAdmins();

    List<AdminDto> getAllAdmins(int page, int limit);

    boolean changePassword(String email, String newPassword);
}
