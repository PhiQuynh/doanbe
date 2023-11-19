package com.doan.controller;

import com.doan.dto.GetCouncli;
import com.doan.entity.Coucil;
import com.doan.entity.Teacher;
import com.doan.entity.TeacherCoucil;
import com.doan.repository.CoucilRepository;
import com.doan.repository.TeacherCoucliRepository;
import com.doan.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/teachercoucil")
public class TeacherCouncliController {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    CoucilRepository coucilRepository;

    @Autowired
    TeacherCoucliRepository repository;

     // add giảng viên theo hội đồng

    @PostMapping("/teacher/{teacherId}/council/{councilId}")
    public ResponseEntity<String> addTeacherToCouncil(
            @PathVariable Long teacherId,
            @PathVariable Long councilId
    ) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        Optional<Coucil> optionalCouncil = coucilRepository.findById(councilId);

        if (optionalTeacher.isPresent() && optionalCouncil.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            Coucil council = optionalCouncil.get();

            TeacherCoucil teacherCouncil = new TeacherCoucil();
            teacherCouncil.setTeacher(teacher);
            teacherCouncil.setCoucil(council);
            repository.save(teacherCouncil);

            return ResponseEntity.ok("Thêm giáo viên vào hội đồng thành công!");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/council/{councilId}/teachers")
    public ResponseEntity<List<Teacher>> getTeachersByCouncilId(@PathVariable Long councilId) {
        List<TeacherCoucil> teacherCouncils = repository.findByCoucilCoucilId(councilId);
        List<Teacher> teachers = new ArrayList<>();

        for (TeacherCoucil teacherCouncil : teacherCouncils) {
            teachers.add(teacherCouncil.getTeacher());
        }

        if (!teachers.isEmpty()) {
            return ResponseEntity.ok(teachers);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<GetCouncli>> getCouncliByTeacher(@PathVariable Long teacherId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        List<TeacherCoucil> teacherCouncils = repository.findByTeacherTeacherId(teacherId);
        List<GetCouncli> getCounclis = new ArrayList<>();

        for (TeacherCoucil teacherCouncil : teacherCouncils) {
            Coucil councli = teacherCouncil.getCoucil();
            GetCouncli getCouncli = GetCouncli.builder()
                    .coucilId(councli.getCoucilId())
                    .coucilName(councli.getCoucilName())
                    .masterName(councli.getMaster().getMasterName())
                    .subjectName(councli.getSubject().getSubjectName())
                    .build();
            getCounclis.add(getCouncli);
        }

        if (!getCounclis.isEmpty()) {
            return ResponseEntity.ok(getCounclis);
        }

        return ResponseEntity.notFound().build();
    }

}