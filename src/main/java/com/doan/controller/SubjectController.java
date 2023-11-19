package com.doan.controller;

import com.doan.payload.AddSubjectRequest;
import com.doan.payload.EditSubjectRequest;
import com.doan.payload.GetSubjectRespon;
import com.doan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSubject(@RequestBody AddSubjectRequest addSubjectRequest) {
        return subjectService.addSubject(addSubjectRequest);
    }

    @PutMapping()
    public ResponseEntity<?> editSubject(@RequestBody EditSubjectRequest editPasswordSubjectRequest) {
        return subjectService.editSubject(editPasswordSubjectRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return subjectService.getSubject();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/count-student-success/{subjectId}")
    public ResponseEntity<?> countSuccessfulDefenseStudentsBySubjectId(@PathVariable Long subjectId) {
        return subjectService.countSuccessfulDefenseStudentsBySubjectId(subjectId);
    }

    @GetMapping("/count-student/{subjectId}")
    public ResponseEntity<?> countStudentsBySubjectId(@PathVariable Long subjectId) {
        return subjectService.countStudentsBySubjectId(subjectId);
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
//        return subjectService.deleteSubject(id);
//    }
}
