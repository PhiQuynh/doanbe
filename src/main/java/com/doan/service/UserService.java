package com.doan.service;

import com.doan.config.Constants;
import com.doan.dto.GetUser;
import com.doan.entity.Role;
import com.doan.entity.User;
import com.doan.payload.*;
import com.doan.repository.*;
import com.doan.validate.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    @Transactional
    public ResponseEntity<?> addUser(AddUserRequest addUserRequest) {
        Validate validate = new Validate(teacherRepository, subjectRepository, masterRepository, userRepository);
        try{
            ResponseEntity<?> validationResult;
            validationResult =  validate.validateUserName(addUserRequest.getUsername());
            if (validationResult != null) {
                return validationResult;
            }
            Role role = roleRepository.findByRoleName(addUserRequest.getName());
            var user = User.builder()
                    .username(addUserRequest.getUsername())
                    .password(passwordEncoder.encode(addUserRequest.getPassword()))
                    .role(role)
                    .build();
            user.setRole(role);
            userRepository.save(user);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_ADD), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseSucces(Constants.ERR_CODE, Constants.MESSAGE_ERR_ADD), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> editUser(EditUserRequest editUserRequest) {
        try {
            Optional<User> user = userRepository.findByUsername(editUserRequest.getUsername());

            if (user.isPresent()
                    && editUserRequest.getRoleName().equals(user.get().getRole().getRoleName())
                    && passwordEncoder.matches(editUserRequest.getPassword(), user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(editUserRequest.getNewPassword()));
                userRepository.save(user.get());
                return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_EDIT_PASSWORD_SUCCES), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseSucces(Constants.ERR_CODE, Constants.MESSAGE_ERR_EDIT_USER), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_EDIT), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  @Transactional
    public GetUser getAccount(String username){
        return getUsers(userRepository.findByUsername(username));
    }

    public GetUser getUsers(Optional<User> user){
        GetUser.GetUserBuilder builder = GetUser.builder();
                if(user.isPresent()){
                   builder .userId(Math.toIntExact(user.get().getUserId()))
                            .roleName(user.get().getRole().getRoleName());
                }
//        if(user.get().getTeacher() != null){
//              builder.teacherId(user.get().getTeacher().getTeacherId());
//        }
        return builder.build();
    }


}
