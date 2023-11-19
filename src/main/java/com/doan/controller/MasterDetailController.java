package com.doan.controller;

import com.doan.config.Constants;
import com.doan.dto.GetStudent;
import com.doan.entity.Coucil;
import com.doan.entity.Master;
import com.doan.entity.MasterDetail;
import com.doan.entity.Teacher;
import com.doan.payload.*;
import com.doan.repository.CoucilRepository;
import com.doan.repository.MasterDetailRepository;
import com.doan.repository.MasterRepository;
import com.doan.repository.TeacherRepository;
import com.doan.service.MasterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/masterdetail")
public class MasterDetailController {
    @Autowired
    private MasterDetailService masterDetailService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private CoucilRepository coucilRepository;

    @Autowired
    MasterDetailRepository masterDetailRepository;

//    @GetMapping("/gvhd/{teacherId}")
//    public ResponseEntity<?> getMasterDetailByGVHD(@PathVariable Long teacherId) {
//        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
//        if(teacher.isPresent()) {
//            return masterDetailService.getMasterDetailByGVHD(teacher);
//        }
//        return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR_2), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @GetMapping("/gvpb/{teacherId}")
    public ResponseEntity<?> getMasterDetailByGVPB(@PathVariable Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if(teacher.isPresent()) {
            return masterDetailService.getMasterDetailByGVPB(teacher);
        }
        return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR_2), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/sv<5")
    public ResponseEntity<?> getMasterDetailByScore() {
        return masterDetailService.getMasterDetailByScore();
    }

    @GetMapping("/successful-defense")
    public ResponseEntity<?> getSuccessfulDefenseStudents() {
        return masterDetailService.getSuccessfulDefenseStudents();
    }

    @GetMapping("/sv<5/{masterId}")
    public ResponseEntity<?> getMasterDetailByScore(@PathVariable Long masterId) {
        Optional<Master> master = masterRepository.findById(masterId);
        return masterDetailService.getMasterDetailByScore1(master.get());
    }

    @GetMapping("/successful-defense/{masterId}")
    public ResponseEntity<?> getSuccessfulDefenseStudents(@PathVariable Long masterId) {
        Optional<Master> master = masterRepository.findById(masterId);
        return masterDetailService.                         getSuccessfulDefenseStudents1(master.get());
    }

    @PostMapping()
    public ResponseEntity<?> addMasterDetail(@RequestBody AddMasterDetailRequest addMasterDetailRequest) {
        return masterDetailService.addMasterDetail(addMasterDetailRequest);
    }

    @PutMapping("/edit_title")
    public ResponseEntity<?> editMasterDetail(@RequestBody EditMasterDetailRequest editMasterDetailRequest) {
        return masterDetailService.editMasterDetail(editMasterDetailRequest);
    }

    @PutMapping("/edit_score_coucil")
    public ResponseEntity<?> addScoreCoucil(@RequestBody EditScoreRequest editScoreCoucilRequest) {
        return masterDetailService.editScoreCoucil(editScoreCoucilRequest);
    }

    @PutMapping("/edit_score_argument")
    public ResponseEntity<?> addScoreArgument(@RequestBody EditScoreRequest editScoreCoucilRequest) {
        return masterDetailService.editScoreArgument(editScoreCoucilRequest);
    }

    @PutMapping("/edit_gvpb")
    public ResponseEntity<?> assignCriticism(@RequestBody AssignCriticismRequest assignCriticismRequest) {
        return masterDetailService.assignCriticism(assignCriticismRequest);
    }

    @PutMapping("/edit_gvhd")
    public ResponseEntity<?> editByGvhd(@RequestBody EditMasterDetailByGVHDRequest editMasterDetailByGVHDRequest) {
        return masterDetailService.editByGvhd(editMasterDetailByGVHDRequest);
    }

    @PutMapping("/{councliId}/master_detail/{masterDetailId}")
    public ResponseEntity<String> addMasterDetailToCouncli(
            @PathVariable Long councliId,
            @PathVariable Long masterDetailId) {

        Coucil councli = coucilRepository.findById(councliId).orElseThrow();

        MasterDetail masterDetail = masterDetailRepository.findById(masterDetailId) .orElseThrow();

        masterDetail.setCoucil(councli);

        masterDetailRepository.save(masterDetail);

        return ResponseEntity.ok("MasterDetail added to Councli successfully");
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return masterDetailService.getAllMasterDetails();
    }


    @GetMapping("/getById/{masterDetailId}")
    public GetStudent getMater(@PathVariable Long masterDetailId){
        return masterDetailService.getMaterById(masterDetailId);
    }

}