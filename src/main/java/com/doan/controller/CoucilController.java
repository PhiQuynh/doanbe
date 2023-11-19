package com.doan.controller;


import com.doan.dto.GetCouncli;
import com.doan.dto.UpdateCouncli;
import com.doan.entity.Coucil;
import com.doan.entity.Master;
import com.doan.entity.Subject;
import com.doan.payload.AddCoucilRequest;
import com.doan.payload.EditCoucilByDepartment;
import com.doan.repository.CoucilRepository;
import com.doan.repository.MasterRepository;
import com.doan.repository.SubjectRepository;
import com.doan.service.CoucilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/coucil")
public class CoucilController {
    @Autowired
    CoucilService coucilService;
    @Autowired
    CoucilRepository coucilRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    MasterRepository masterRepository;

    @PostMapping
    public ResponseEntity<?> addCoucil(@RequestBody AddCoucilRequest addCoucilRequest) {
        return coucilService.addCoucil(addCoucilRequest);
    }

    @GetMapping("/by-department/{subjectId}")
    public ResponseEntity<?> getMasterDetailByStatusDepartment(@PathVariable Long subjectId, @RequestParam(required = false) Long status) {
        return coucilService.getCoucilByStatusDepartment(subjectId, status);
    }
    @PutMapping("/edit-by-department")
    public ResponseEntity<?> editByDepartment(@RequestBody EditCoucilByDepartment editCoucilByDepartment) {
        return coucilService.editByDepartment(editCoucilByDepartment);
    }

      @PutMapping("/{id}")
    public ResponseEntity<String> updateCoucil(@PathVariable Long id, @RequestBody UpdateCouncli updatedCoucil) {
        Optional<Coucil> coucilOptional = coucilRepository.findById(id);

        Subject subject = subjectRepository.findById(updatedCoucil.getSubjectId()).orElseThrow();

        Master master = masterRepository.findById(updatedCoucil.getMasterId()).orElseThrow();

        if (!coucilOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Coucil existingCoucil = coucilOptional.get();
        existingCoucil.setCoucilName(updatedCoucil.getCoucilName());
        existingCoucil.setMaster(master);
        existingCoucil.setStatus(updatedCoucil.getStatus());
        existingCoucil.setSubject(subject);

         coucilRepository.save(existingCoucil);
        return ResponseEntity.ok("Coucil updated successfully");
    }

    @GetMapping("/getAll")
    public List<GetCouncli> getCounclis(){
        return coucilService.getCounclis();
    }
}
