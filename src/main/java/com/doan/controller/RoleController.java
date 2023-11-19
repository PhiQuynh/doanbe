package com.doan.controller;

import com.doan.config.Constants;
import com.doan.entity.Master;
import com.doan.entity.Role;
import com.doan.entity.Teacher;
import com.doan.payload.*;
import com.doan.repository.MasterRepository;
import com.doan.repository.RoleRepository;
import com.doan.repository.TeacherRepository;
import com.doan.service.MasterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}