package com.doan.service;

import com.doan.config.Constants;
import com.doan.dto.CoucilDTO;
import com.doan.dto.GetCouncli;
import com.doan.entity.*;
import com.doan.payload.*;
import com.doan.repository.CoucilRepository;
import com.doan.repository.MasterRepository;
import com.doan.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoucilService {

    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private CoucilRepository coucilRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public ResponseEntity<?> addCoucil(AddCoucilRequest addCoucilRequest) {
        try{
            Coucil coucil = new Coucil();
            coucil.setCoucilName(addCoucilRequest.getCoucilName());
            Master master = masterRepository.findById(addCoucilRequest.getMasterId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid masterId: " + addCoucilRequest.getMasterId()));
            coucil.setMaster(master);
            Subject subject = subjectRepository.findById(addCoucilRequest.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid SubjectId: " + addCoucilRequest.getSubjectId()));
            coucil.setSubject(subject);
            coucilRepository.save(coucil);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_ADD_COUCIL_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ADD_COUCIL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getCoucilByStatusDepartment(Long subjectId, Long status) {
        List<CoucilDTO> coucilDTOS = new ArrayList<>();
        GetCoucilRespon respon = new GetCoucilRespon();
        try{
            Optional<Subject> subject = subjectRepository.findById(subjectId);
            List<Coucil> coucils = coucilRepository.findCoucilsByStatus(subject.get(), status);
            for(Coucil coucil : coucils) {
                CoucilDTO coucilDTO = new CoucilDTO();
                coucilDTO.setCoucilId(coucil.getCoucilId());
                coucilDTO.setCoucilName(coucil.getCoucilName());
                coucilDTO.setMasterName(coucil.getMaster().getMasterName());
                coucilDTO.setSubjectName(coucil.getSubject().getSubjectName());
//                coucilDTO.setTeachers(coucil.getTeachers());
                coucilDTOS.add(coucilDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_COUCIL_BY_STATUS_SUCCES);
            respon.setCoucilDTOS(coucilDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_COUCIL_BY_STATUS_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editByDepartment(EditCoucilByDepartment editCoucilByDepartment) {
        try{
            Coucil coucil = coucilRepository.findById(editCoucilByDepartment.getCoucilId())
                    .orElseThrow();
            coucil.setStatus(editCoucilByDepartment.getStatus());
            coucilRepository.save(coucil);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_EDIT_COUCIL_BY_DEPARTMENT_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_EDIT_COUCIL_BY_DEPARTMENT_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public List<GetCouncli> getCounclis() {
        List<Coucil> councliList = coucilRepository.findAll();
        List<GetCouncli> getCounclis = new ArrayList<>();

        for (Coucil councli : councliList) {
            getCounclis.add(getCouncli(councli));
        }

        return getCounclis;
    }

    public GetCouncli getCouncli(Coucil councli) {
        return GetCouncli.builder()
                .coucilId(councli.getCoucilId())
                .coucilName(councli.getCoucilName())
                .masterName(councli.getMaster().getMasterName())
                .subjectName(councli.getSubject().getSubjectName())
                .build();
    }

}
