package com.doan.service;

import com.doan.config.Constants;
import com.doan.dto.MasterDetailDTO;
import com.doan.dto.MasterDto;
import com.doan.entity.*;
import com.doan.payload.*;
import com.doan.repository.*;
import com.doan.validate.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MasterService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public ResponseEntity<?> addMaster(AddMasterRequest addMasterRequest) {
        Validate validate = new Validate(teacherRepository, subjectRepository, masterRepository, userRepository);
        try{
            Master master = new Master();
            ResponseEntity<?> validationResult;
//            validationResult =  validate.validateEndDate(addMasterRequest.getStartDate(), addMasterRequest.getEndDate());
//            if (validationResult != null) {
//                return validationResult;
//            }
            master.setMasterName(addMasterRequest.getMasterName());
            master.setStartDate(addMasterRequest.getStartDate());
            master.setEndDate(addMasterRequest.getEndDate());
            Subject subject = subjectRepository.findById(addMasterRequest.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid SubjectId: " + addMasterRequest.getSubjectId()));
            master.setSubject(subject);
            masterRepository.save(master);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_ADD), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_ADD), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editByDepartment(EditMasterByDepartment editMasterByDepartment) {
        try{
            Master master =  masterRepository.findById(editMasterByDepartment.getMasterId())
                    .orElseThrow();
            master.setStatusDepartment(editMasterByDepartment.getStatusDepartment());
            masterRepository.save(master);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_EDIT_MASTER_DETAIL_BY_DEPARTMENT_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_EDIT_MASTER_DETAIL_BY_DEPARTMENT_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


public ResponseEntity<?> getMasterByStatusDepartment(Subject subject, Long status) {
    GetMasterRespon respon = new GetMasterRespon();
    try {
        List<Master> masters = masterRepository.findMasterByStatusDepartment(subject, status);
        List<MasterDto> masterListWithDetails = new ArrayList<>();

        for (Master master : masters) {
            MasterDto newMaster = new MasterDto();
            newMaster.setMasterId(master.getMasterId());
            newMaster.setMasterName(master.getMasterName());
            newMaster.setStartDate(master.getStartDate());
            newMaster.setEndDate(master.getEndDate());
            newMaster.setStatusDepartment(master.getStatusDepartment());
            List<MasterDetail> masterDetails = master.getMasterDetails();
            List<MasterDetailDTO> masterDetailsWithTeachers = new ArrayList<>();
            for (MasterDetail masterDetail : masterDetails) {
                MasterDetailDTO newMasterDetail = new MasterDetailDTO();
                newMasterDetail.setMasterDetailId(masterDetail.getMasterDetailId());
                newMasterDetail.setMssv(masterDetail.getMssv());
                newMasterDetail.setStudentName(masterDetail.getStudentName());
                newMasterDetail.setStudentClass(masterDetail.getStudentClass());
                newMasterDetail.setTitleNameVn(masterDetail.getTitleNameVn());
                newMasterDetail.setTitleNameEn(masterDetail.getTitleNameEn());
//                newMasterDetail.setTeacherNameHd(masterDetail.getTeacherHD().getTeacherName());
                masterDetailsWithTeachers.add(newMasterDetail);
            }
            newMaster.setMasterDetailDTOS(masterDetailsWithTeachers);
            masterListWithDetails.add(newMaster);
        }
        respon.setCode(Constants.SUCCCES_CODE);
        respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
        respon.setMasterDtos(masterListWithDetails);
        respon.setTotals(masterListWithDetails.size());
        return new ResponseEntity<>(respon, HttpStatus.OK);
    } catch (Exception e) {
        respon.setCode(Constants.ERR_CODE);
        respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
        return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



    public ResponseEntity<?> getMasterBySubject(Subject subject) {
        GetMasterRespon respon = new GetMasterRespon();
        try {
            List<Master> masters = masterRepository.findMasterBySubject(subject);
            List<MasterDto> masterListWithDetails = new ArrayList<>();

            for (Master master : masters) {
                MasterDto newMaster = new MasterDto();
                newMaster.setMasterId(master.getMasterId());
                newMaster.setMasterName(master.getMasterName());
                newMaster.setStartDate(master.getStartDate());
                newMaster.setEndDate(master.getEndDate());
                newMaster.setStatusDepartment(master.getStatusDepartment());
                masterListWithDetails.add(newMaster);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_SUCCCES);
            respon.setMasterDtos(masterListWithDetails);
            respon.setTotals(masterListWithDetails.size());
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_MASTER_DETAIL_BY_GVHD_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<Master> getMaterByYear(int year){
        return masterRepository.findByStartDateContains(year);
    }

}
