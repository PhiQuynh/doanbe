package com.doan.service;

import com.doan.config.Constants;
import com.doan.dto.CoucilDTO;
import com.doan.entity.Coucil;
import com.doan.entity.Department;
import com.doan.entity.Subject;
import com.doan.entity.User;
import com.doan.payload.*;
import com.doan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public ResponseEntity<?> addSubject(AddSubjectRequest addSubjectRequest) {
        try{
            Subject subject = new Subject();
            subject.setSubjectName(addSubjectRequest.getSubjectName());
            Department department = departmentRepository.findById(addSubjectRequest.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid subjectId: " + addSubjectRequest.getDepartmentId()));
            subject.setDepartment(department);
            User user = userRepository.findById(addSubjectRequest.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid UserId: " + addSubjectRequest.getUserId()));
            subject.setUser(user);
            subjectRepository.save(subject);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_ADD), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_ADD), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editSubject(EditSubjectRequest editPasswordSubjectRequest) {
        try{
            Subject subject = subjectRepository.findById(editPasswordSubjectRequest.getSubjectId())
                    .orElseThrow();
            subject.setSubjectName(editPasswordSubjectRequest.getSubjectName());
            subjectRepository.save(subject);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_EDIT), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_EDIT), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSubject() {
        List<Subject> subjects = new ArrayList<>();
        GetSubjectRespon respon = new GetSubjectRespon();
        try{
            List<Subject> subjects1 = subjectRepository.findAll();
            for(Subject subject : subjects1) {
                Subject subject1 = new Subject();
                subject1.setSubjectId(subject.getSubjectId());
                subject1.setSubjectName(subject.getSubjectName());
                Optional<User> user = userRepository.findById(subject.getUser().getUserId());
                if(user.isPresent()) {
                    subject1.setUser(user.get());
                }
                subjects.add(subject1);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setTotalSubject(subjects.size());
            respon.setMessage(Constants.MESSAGE_GET_ALL_SUCCES);
            respon.setSubjects(subjects);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            respon.setCode(Constants.ERR_CODE);
            respon.setMessage(Constants.MESSAGE_GET_ALL_ERR);
            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSubjectById(Long id) {
        try{
            Optional<Subject> subject = subjectRepository.findById(id);
            if(subject.isPresent()) {
                return new ResponseEntity<>(new GetSubjectByIdRespon(Constants.SUCCCES_CODE, Constants.MESSAGE_GET_SUBJECT_BY_ID_SUCCES, subject.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_SUBJECT_BY_ID_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_SUBJECT_BY_ID_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> countSuccessfulDefenseStudentsBySubjectId(Long subjectId) {
        try{
            int num = subjectRepository.countSuccessfulDefenseStudentsBySubjectId(subjectId);
            return new ResponseEntity<>(new GetCountMasterDetailBySubjectRespon(Constants.SUCCCES_CODE, num), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_COUNT_Successful_Defense_BY_SUBJECT_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> countStudentsBySubjectId(Long subjectId) {
        try{
            int num = subjectRepository.countStudentsBySubjectId(subjectId);
            return new ResponseEntity<>(new GetCountMasterDetailBySubjectRespon(Constants.SUCCCES_CODE, num), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_COUNT_Successful_Defense_BY_SUBJECT_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
