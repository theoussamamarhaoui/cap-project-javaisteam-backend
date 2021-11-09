package com.cap.backendcapproject.services;

import com.cap.backendcapproject.dto.UserDto;
import com.cap.backendcapproject.entities.AppUser;
import com.cap.backendcapproject.entities.AppRole;

import java.util.List;

public interface AccountService {
    public AppUser saveUser(String email, String password, String confirmedPassword);
    public AppRole saveRole(AppRole role);
    public AppUser loadUserByEmail(String email);
    public UserDto findUserByUsername(String username);
    public AppUser addRoleToUser(String username, String role);
    public UserDto updateUser(String id, UserDto userDto);
    List<UserDto> getAllUsers(int page, int limit);
    public AppUser saveAdmin(String email, String password, String confirmedPassword);
    public AppUser saveCandidate(String email, String password, String confirmedPassword);
    public void deleteUser(Long id);
    public boolean changePassword(String email, String password);
}
