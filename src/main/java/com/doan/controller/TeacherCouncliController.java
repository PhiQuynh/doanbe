package com.doan.controller;

import com.doan.config.Constants;
import com.doan.dto.GetCouncli;
import com.doan.dto.ListTeacherByCouncli;
import com.doan.entity.Coucil;
import com.doan.entity.Teacher;
import com.doan.entity.TeacherCoucil;
import com.doan.payload.ResponseSucces;
import com.doan.repository.CoucilRepository;
import com.doan.repository.TeacherCoucliRepository;
import com.doan.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> addTeacherToCouncil(
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

            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_TEACHER_ADD_COUNCLI), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/council/{councilId}/teachers")
    public ResponseEntity<List<ListTeacherByCouncli>> getTeachersByCouncilId(@PathVariable Long councilId) {
        List<TeacherCoucil> teacherCouncils = repository.findByCoucilCoucilId(councilId);
        List<ListTeacherByCouncli> teachers = new ArrayList<>();
        for (TeacherCoucil teacherCouncil : teacherCouncils) {
            Teacher teacher = teacherCouncil.getTeacher();
            ListTeacherByCouncli listTeacherByCouncli = ListTeacherByCouncli.builder()
                    .teacherId(teacher.getTeacherId())
                    .teacherName(teacher.getTeacherName())
                    .email(teacher.getEmail())
                    .phone(teacher.getPhone())
                    .researchDirection(teacher.getResearchDirection())
                    .build();

            teachers.add(listTeacherByCouncli);
        }
        if (!teachers.isEmpty()) {
            return ResponseEntity.ok(teachers);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<GetCouncli>> getCouncliByTeacher(@PathVariable Long teacherId) {
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