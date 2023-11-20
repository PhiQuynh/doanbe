package com.doan.controller;

import com.doan.dto.StudentAcceptDTO;
import com.doan.dto.StudentInvitationDTO;
import com.doan.dto.TeacherStudentRequest;
import com.doan.entity.TeacherSudent;
import com.doan.service.TeacherStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacherStudent")
public class TeacherStudentController {

    @Autowired
    TeacherStudentService teacherStudentService;

    @PostMapping("/invite")
    public ResponseEntity<String> send(@RequestBody TeacherStudentRequest teacherSudent){
        return teacherStudentService.sendInvitation(teacherSudent);
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<String> acceptInvitation(@PathVariable Long id) {
        return teacherStudentService.updateStatus(id);
    }

    @PutMapping("/not_accept/{id}")
    public ResponseEntity<String> not_acceptInvitation(@PathVariable Long id) {
        return teacherStudentService.not_acceptInvitation(id);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<List<StudentInvitationDTO>> getInvitations(@PathVariable Long teacherId) {
        return teacherStudentService.getStudentsByTeacherId(teacherId);
    }
    @GetMapping("/teacher_accept/{teacherId}")
    public ResponseEntity<List<StudentAcceptDTO>> getAccept(@PathVariable Long teacherId) {
        return teacherStudentService.getStudentsByTeacherIdAccept(teacherId);
    }
}
