package com.cap.backendcapproject.controller;

import com.cap.backendcapproject.dto.UserDto;
import com.cap.backendcapproject.entities.AppRole;
import com.cap.backendcapproject.entities.AppUser;
import com.cap.backendcapproject.request.UserRequest;
import com.cap.backendcapproject.response.UserResponse;
import com.cap.backendcapproject.services.AccountService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "10") int limit){

        List<UserResponse> userRespense = new ArrayList<>();

        List<UserDto> userds= accountService.getAllUsers(page, limit);

        for(UserDto doc : userds) {

            UserResponse usrResp = new UserResponse();

            BeanUtils.copyProperties(doc, usrResp);

            userRespense.add(usrResp);
        }

        return userRespense;

    }
    @PutMapping("/users/{email}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String email, @RequestBody UserRequest userRequest){

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userRequest, userDto);

        UserDto updateUser = accountService.updateUser(email, userDto);

        UserResponse userResponse = new UserResponse();

        BeanUtils.copyProperties(updateUser, userResponse);

        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.ACCEPTED);
    }
    @PostMapping("/register")
    public AppUser register(@RequestBody UserForm userForm){
        System.out.println(userForm.getEmail() + " , " + userForm.getPassword() + " , "+ userForm.getConfirmedPassword());
       return accountService.saveUser(userForm.getEmail(), userForm.getPassword(),
               userForm.getConfirmedPassword());
    }
    @PostMapping("/user/addrole")
    public UserResponse addRoleToUser(@RequestBody RoleAtt roleAtt) {
        AppUser user = accountService.addRoleToUser(roleAtt.getEmail(), roleAtt.getRoleName());
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    @PostMapping("/user/saveRole")
    public AppRole addRole(@RequestBody AppRole role){
        System.out.println("role : " + role);
        return accountService.saveRole(role);
    }

    @PostMapping("/adminregister")
    public AppUser adminRegister(@RequestBody  UserForm userForm){
         System.out.println("admin in register");
        return  accountService.saveAdmin(
                userForm.getEmail(),userForm.getPassword(),userForm.getConfirmedPassword());
    }

    @PostMapping("/candidateregister")
    public AppUser candidateRegister(@RequestBody  UserForm userForm){
        System.out.println("candidate in register");
        return  accountService.saveCandidate(
                userForm.getEmail(),userForm.getPassword(),userForm.getConfirmedPassword());
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUserByUsername(@PathVariable String username){

        UserDto userDto = new UserDto();
        userDto = accountService.findUserByUsername(username);

        System.out.println("userDto "+userDto);

        UserResponse userResponse = new UserResponse();

        BeanUtils.copyProperties(userDto, userResponse);

        return userResponse;
    }

    @PatchMapping("/users/password")
    public boolean changePassword(@RequestBody PasswordChange change) {
        System.out.println("change " + change);
        return  accountService.changePassword(change.getEmail(), change.getNewPassword());
    }


}
@Data
class UserForm{
    private String email;
    private String password;
    private String confirmedPassword;
}

@Data
class RoleAtt{
    private String email;
    private String roleName;
}

@Data
class PasswordChange {
    private String email;
    private String newPassword;
}