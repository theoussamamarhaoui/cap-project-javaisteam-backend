package com.cap.backendcapproject.services.servicesimpl;

import com.cap.backendcapproject.dto.AdminDto;
import com.cap.backendcapproject.entities.Admin;
import com.cap.backendcapproject.entities.AppUser;
import com.cap.backendcapproject.repository.AdminRepository;
import com.cap.backendcapproject.services.AdminService;
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
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
        Admin checkAdmin = adminRepository.findByEmail(adminDto.getEmail());
        System.out.println("checkAdmin : " + checkAdmin);
        if(checkAdmin != null){
            throw new RuntimeException(String.valueOf(HttpStatus.FOUND.value()));

        }
        Admin adminEntity = new Admin();
        BeanUtils.copyProperties(adminDto, adminEntity);
        System.out.println("adminEntity1 : " + adminEntity);

        adminEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(adminDto.getPassword()));

        System.out.println("adminEntity2 : " + adminEntity);

        System.out.println("admin : " + adminEntity.getEncryptedPassword());
        adminEntity.setCreateAt(new Date());


        Admin newAdmin = adminRepository.save(adminEntity);

        AdminDto adminDto1 = new AdminDto();
        BeanUtils.copyProperties(newAdmin, adminDto1);
        return adminDto1;
    }

    @Override
    public AdminDto getAdmin(String email) {
        return null;
    }

    @Override
    public AdminDto getAdminByUsername(String username) {
        Admin adminEntity = adminRepository.findByUsername(username);

        if(adminEntity == null){
            throw new UsernameNotFoundException(username);
        }

        AdminDto adminDto = new AdminDto();

        BeanUtils.copyProperties(adminEntity, adminDto);
        System.out.println("adminDto serviceImpl = " + adminDto);
        return adminDto;
    }

    @Override
    public AdminDto updateAdmin(String username, AdminDto adminDto) {
        Admin adminEntity = adminRepository.findByUsername(username);

        if(adminEntity == null) throw new UsernameNotFoundException(username);

        if(adminDto.getFirstName() != null) adminEntity.setFirstName(adminDto.getFirstName());
        if(adminDto.getLastName() != null) adminEntity.setLastName(adminDto.getLastName());
        if(adminDto.getEmail() != null) adminEntity.setEmail(adminDto.getEmail());
        if(adminDto.getPassword() != null) adminEntity.setPassword(adminDto.getPassword());
        if(adminDto.getAddress() != null) adminEntity.setAddress(adminDto.getAddress());
        if(adminDto.getDateOfBirth() != null) adminEntity.setDateOfBirth(adminDto.getDateOfBirth());
        if(adminDto.getGender() != null) adminEntity.setGender(adminDto.getGender());
        if(adminDto.getPhone() != null) adminEntity.setPhone(adminDto.getPhone());
        if(adminDto.getPicture() != null) adminEntity.setPicture(adminDto.getPicture());
        if(adminDto.getOrganization()!=null) adminEntity.setOrganization(adminDto.getOrganization());

        Admin adminUpdated = adminRepository.save(adminEntity);

        AdminDto adminDto1 = new AdminDto();

        BeanUtils.copyProperties(adminUpdated, adminDto1);

        return adminDto1;
    }

    @Override
    public void deleteAdmin(String username) {
        Admin adminEntity = adminRepository.findByUsername(username);

        System.out.println("adminEntity : " + adminEntity);

        if(adminEntity == null){
            throw new EntityNotFoundException("ce compte n'existe pas");
        }

        adminRepository.delete(adminEntity);


    }

    @Override
    public List<AdminDto> getAllAdmins() {
        List<Admin> admList = adminRepository.findAll();

        List<AdminDto> adminDtoList = new ArrayList<>();

        for(Admin d : admList) {

            AdminDto admDto = new AdminDto() ;

            BeanUtils.copyProperties(d, admDto);

            adminDtoList.add(admDto);
        }

        return adminDtoList;
    }

    @Override
    public List<AdminDto> getAllAdmins(int page, int limit) {
        if(page > 0) page -= 1;

        List<AdminDto> admListDto = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<Admin> adminPage = adminRepository.findAll(pageableRequest);

        List<Admin> admins = adminPage.getContent();

        for(Admin d : admins) {

            AdminDto admDto = new AdminDto() ;

            BeanUtils.copyProperties(d, admDto);

            admListDto.add(admDto);
        }

        return admListDto;
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        return false;
    }
}
