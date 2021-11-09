package com.cap.backendcapproject.services.servicesimpl;

import com.cap.backendcapproject.dto.AdminDto;
import com.cap.backendcapproject.dto.CandidateDto;
import com.cap.backendcapproject.dto.UserDto;
import com.cap.backendcapproject.entities.AppRole;
import com.cap.backendcapproject.entities.AppUser;
import com.cap.backendcapproject.entities.Candidate;
import com.cap.backendcapproject.repository.AdminRepository;
import com.cap.backendcapproject.repository.RoleRepository;
import com.cap.backendcapproject.repository.UserRepository;
import com.cap.backendcapproject.services.AccountService;
import com.cap.backendcapproject.services.AdminService;
import com.cap.backendcapproject.services.CandidateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    final String ADMIN = "Admin";
    final String CANDIDATE = "Candidate";
    @Autowired
    AdminService adminService;
    @Autowired
    CandidateService candidateService;

    AccountServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public AppUser saveUser(String email, String password, String confirmedPassword) {
        AppUser user = userRepository.findByEmail(email);
        if(user != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        if(!password.equals(confirmedPassword)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please confirm your password");
        AppUser user1 = new AppUser();
        user1.setEmail(email);
        user1.setActived(true);
        user1.setPassword(bCryptPasswordEncoder.encode(password));
        user1.setCreateAt(new Date());
        userRepository.save(user1);

        String role = "Candidate";
        AppRole role1 = roleRepository.findByRole(role);

        if(role1 == null){
            AppRole role2 = new AppRole();
            role2.setRole(role);
            this.saveRole(role2);
        }

        //addRoleToUser(email, "Candidate");
        return user1;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public AppUser loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto findUserByUsername(String username) {
        AppUser user = userRepository.findByUsername(username);
        System.out.println("User : " + user);
        UserDto userDto = new UserDto();
        if(user != null){
            BeanUtils.copyProperties(user, userDto);
        }else{
            System.out.println("user n'existe pas");
        }
        return userDto;
    }

    @Override
    public AppUser addRoleToUser(String username, String role) {
        AppUser user = userRepository.findByEmail(username);
        AppRole role1 = roleRepository.findByRole(role);

        if(role1.getRole().equals(ADMIN)){
            AdminDto adminDto = new AdminDto();
            adminDto.setEmail(username);
            adminDto.setPassword(user.getPassword());
            adminDto = adminService.createAdmin(adminDto);

            updateUser(user.getEmail(), adminDto);
        }else {
            CandidateDto candidateDto = new CandidateDto();
            candidateDto.setEmail(username);
            candidateDto.setPassword(user.getPassword());
            System.out.println("candidateDto : " + candidateDto);
            candidateDto = candidateService.createCandidate(candidateDto);

            updateUser(user.getEmail(), candidateDto);
        }

        user.getRoles().add(role1);

        return user;


    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        AppUser user1 = userRepository.findByEmail(email);
        if(userDto.getFirstName() != null) user1.setFirstName(userDto.getFirstName());
        if(userDto.getLastName() != null) user1.setLastName(userDto.getLastName());
        if(userDto.getUsername() != null) user1.setUsername(userDto.getUsername());
        if(userDto.getDateOfBirth() != null) user1.setDateOfBirth(userDto.getDateOfBirth());
        if(userDto.getGender() != null) user1.setGender(userDto.getGender());
        if(userDto.getAddress() != null) user1.setAddress(userDto.getAddress());
        if(userDto.getPhone() != null) user1.setPhone(userDto.getPhone());
        if(userDto.getPicture() != null) user1.setPicture(userDto.getPicture());
        if(userDto.getEmail() != null) user1.setEmail(userDto.getEmail());
        if(userDto.getPassword() != null) user1.setPassword(userDto.getPassword());
        if(userDto.getRoles() != null) user1.setRoles(userDto.getRoles());
        userRepository.save(user1);
        AppUser userUpdated = userRepository.save(user1);

        UserDto userDto1 = new UserDto();

        BeanUtils.copyProperties(userUpdated, userDto1);

        return userDto1;
    }

    @Override
    public List<UserDto> getAllUsers(int page, int limit) {

        if(page > 0) page -= 1;

        List<UserDto> usrListDto = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<AppUser> userPage = userRepository.findAll(pageableRequest);

        List<AppUser> users = userPage.getContent();

        for(AppUser d : users) {

            UserDto userDto = new UserDto() ;

            BeanUtils.copyProperties(d, userDto);

            usrListDto.add(userDto);
        }

        return usrListDto;
    }

    @Override
    public AppUser saveAdmin(String email, String password, String confirmedPassword) {
        AppUser user = userRepository.findByEmail(email);
        if(user != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        if(!password.equals(confirmedPassword)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please confirm your password");
        AppUser user1 = new AppUser();
        user1.setEmail(email);
        user1.setActived(true);
        user1.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user1);

        addRoleToUser(email, "ADMIN");
        return user;
    }

    @Override
    public AppUser saveCandidate(String email, String password, String confirmedPassword) {
        AppUser user = userRepository.findByEmail(email);
        if(user != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        if(!password.equals(confirmedPassword)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please confirm your password");
        AppUser user1 = new AppUser();
        user1.setEmail(email);
        user1.setActived(true);
        user1.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user1);

        addRoleToUser(email, "CANDIDATE");
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean changePassword(String email, String password) {
        boolean changed = false;
        AppUser user = userRepository.findByEmail(email);
        System.out.println("Origin : " + user);
        if(password != null){
            changed = true;
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        System.out.println("changed : " + user);
        userRepository.save(user);
        return changed;
    }
}
