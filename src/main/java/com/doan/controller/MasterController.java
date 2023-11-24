package com.doan.controller;

import com.doan.config.Constants;
import com.doan.dto.UpdateMaster;
import com.doan.entity.Master;
import com.doan.entity.Subject;
import com.doan.payload.AddMasterRequest;
import com.doan.payload.AssignCriticismRequest;
import com.doan.payload.EditMasterByDepartment;
import com.doan.payload.ResponseSucces;
import com.doan.repository.MasterRepository;
import com.doan.repository.SubjectRepository;
import com.doan.service.MasterService;
import com.doan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/master")
public class MasterController {

    @Autowired
    private MasterService masterService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private MasterRepository masterRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMaster(@RequestBody AddMasterRequest addMasterRequest) {
        return masterService.addMaster(addMasterRequest);
    }
    @PutMapping("/edit-by-department")
    public ResponseEntity<?> editByDepartment(@RequestBody EditMasterByDepartment editMasterByDepartment) {
        return masterService.editByDepartment(editMasterByDepartment);
    }
    @GetMapping("/by-department/{subjectid}")
    public ResponseEntity<?> getMasterDetailByStatusDepartment(@PathVariable Long subjectid, @RequestParam(required = false) Long status) {
        Optional<Subject> subject = subjectRepository.findById(subjectid);
        return masterService.getMasterByStatusDepartment(subject.get() ,status);
    }

    @GetMapping("/by-subject/{subjectid}")
    public ResponseEntity<?> getMasterDetailBySubject(@PathVariable Long subjectid) {
        Optional<Subject> subject = subjectRepository.findById(subjectid);
        return masterService.getMasterBySubject(subject.get());
    }
     @GetMapping()
    public List<Master> getMaterByYear(int year){
        return masterService.getMaterByYear(year);
    }

    @GetMapping("/getAll")
    public List<Master> getMaster(){
        return masterRepository.findAll();
    }

    @PutMapping("/{id}")
       public ResponseEntity<?> updateMaster(@PathVariable Long id, @RequestBody UpdateMaster updatedMaster) {
       Master optionalMaster = masterRepository.findById(id).orElseThrow();
        Subject subject = subjectRepository.findById(updatedMaster.getSubjectId()).orElseThrow();
        optionalMaster.setMasterName(updatedMaster.getMasterName());
        optionalMaster.setStartDate(updatedMaster.getStartDate());
        optionalMaster.setEndDate(updatedMaster.getEndDate());
        optionalMaster.setSubject(subject);
        masterRepository.save(optionalMaster);
        return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_EDIT), HttpStatus.OK);
    }

    @DeleteMapping("/{masterId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long masterId) throws Exception{
        if(!masterRepository.existsById(masterId)){

        }
        masterRepository.deleteById(masterId);
        return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.DELETE_SUCCESS), HttpStatus.OK);

    }
}
