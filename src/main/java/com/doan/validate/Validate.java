package com.doan.validate;

import com.doan.config.Constants;
import com.doan.payload.ResponErr;
import com.doan.repository.MasterRepository;
import com.doan.repository.SubjectRepository;
import com.doan.repository.TeacherRepository;
import com.doan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class Validate {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final MasterRepository masterRepository;
    private final UserRepository userRepository;
    @Autowired
    public Validate(TeacherRepository teacherRepository, SubjectRepository subjectRepository, MasterRepository masterRepository, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.masterRepository = masterRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity validateUserName(String userName) {
        if(userRepository.existsByUsername(userName)) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_VALIDATE_LOGINID), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

//    public ResponseEntity validateEndDate(String startDate, String endDate) {
//        if(endDate.before(startDate)) {
//            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_VALIDATE_ENDDATE), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return null;
//    }
}
