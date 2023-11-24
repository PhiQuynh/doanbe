package com.doan.service;

import com.doan.DoanApplication;
import com.doan.config.Constants;
import com.doan.config.jwt.AuthUserDetails;
import com.doan.dto.TeacherAcceptMasterDetail;
import com.doan.dto.TeacherDTO;
import com.doan.entity.*;
import com.doan.payload.*;
import com.doan.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CoucilRepository coucilRepository;
    private static final Logger log = LoggerFactory.getLogger(DoanApplication.class);
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> addTeacher(AddTeacherRequest addTeacherRequest) {
        try{
            Teacher teacher = new Teacher();
            teacher.setTeacherName(addTeacherRequest.getTeacherName());
            teacher.setEmail(addTeacherRequest.getEmail());
            teacher.setPhone(addTeacherRequest.getPhone());
            teacher.setResearchDirection(addTeacherRequest.getResearchDirection());
            teacher.setSex(addTeacherRequest.getSex());
            teacher.setImage(addTeacherRequest.getImage());
            Subject subject = subjectRepository.findById(addTeacherRequest.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid subjectId: " + addTeacherRequest.getSubjectId()));
            teacher.setSubject(subject);
            User user = userRepository.findById(addTeacherRequest.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid UserId: " + addTeacherRequest.getUserId()));
            teacher.setUser(user);
            teacherRepository.save(teacher);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_ADD), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_ADD), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> editTeacher(EditTeacherRequest editTeacherRequest) {
        try{
            Teacher teacher = teacherRepository.findById(editTeacherRequest.getTeacherId())
                    .orElseThrow();
            teacher.setTeacherName(editTeacherRequest.getTeacherName());
            teacher.setEmail(editTeacherRequest.getEmail());
            teacher.setPhone(editTeacherRequest.getPhone());
            if(!editTeacherRequest.getResearchDirection().equals("")) {
                teacher.setResearchDirection(editTeacherRequest.getResearchDirection());
            }
            Subject subject = subjectRepository.findById(editTeacherRequest.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid subjectId: " + editTeacherRequest.getSubjectId()));
            teacher.setSubject(subject);
            teacherRepository.save(teacher);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_EDIT), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_EDIT), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> findAllWithProperty(String teacherName, Long subjectId, Integer limit, Integer offset) {
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        GetAllTeacherRespon respon = new GetAllTeacherRespon();
        int totalRecords = 0;
        int page = offset / limit;
        try {
            Page<Teacher> teacherPage;

            teacherPage = teacherRepository.findAll(teacherName, subjectId, PageRequest.of(page, limit));

            List<Teacher> teachers = teacherPage.getContent();
            totalRecords = (int) teacherPage.getTotalElements();
            for (Teacher teacher : teachers) {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setTeacherId(teacher.getTeacherId());
                teacherDTO.setTeacherName(teacher.getTeacherName());
                teacherDTO.setEmail(teacher.getEmail());
                teacherDTO.setResearchDirection(teacher.getResearchDirection());
                teacherDTO.setPhone(teacher.getPhone());
                teacherDTO.setSex(teacher.getSex());
                teacherDTO.setMaxStudentsHD(teacher.getMaxStudentsHD());
                teacherDTO.setMaxStudentsPB(teacher.getMaxStudentsPB());
                if (teacher.getSubject() != null) {
                    teacherDTO.setSubjectName(teacher.getSubject().getSubjectName());
                }
                teacherDTOS.add(teacherDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_ALL_SUCCES);
            respon.setTotalRecords(totalRecords);
            respon.setTeachers(teacherDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_ALL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> addTeacherWithCoucil(AddTeacherWithCoucilRequest addTeacherWithCoucilRequest) {
        try{
            Teacher teacher = teacherRepository.findById(addTeacherWithCoucilRequest.getTeacherId())
                    .orElseThrow();
            Coucil coucil = coucilRepository.findById(addTeacherWithCoucilRequest.getCoucilId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid CoucilId: " + addTeacherWithCoucilRequest.getCoucilId()));
//            teacher.setCoucil(coucil);
            teacherRepository.save(teacher);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_ADD_TEACHER_WITH_COUCIL_SUCCES), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ADD_TEACHER_WITH_COUCIL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<?> getTeacherById(Long id) {
        try{
            Optional<Teacher> teacher = teacherRepository.findById(id);
            if(teacher.isPresent()) {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setTeacherId(teacher.get().getTeacherId());
                teacherDTO.setTeacherName(teacher.get().getTeacherName());
                teacherDTO.setEmail(teacher.get().getEmail());
                teacherDTO.setPhone(teacher.get().getPhone());
                teacherDTO.setResearchDirection(teacher.get().getResearchDirection());
                teacherDTO.setSubjectId(teacher.get().getSubject().getSubjectId());
                teacherDTO.setSubjectName(teacher.get().getSubject().getSubjectName());
                return new ResponseEntity<>(new GetTeacherByIdResponse(Constants.SUCCCES_CODE, Constants.MESSAGE_GET_TEACHER_BY_ID_SUCCESS, teacherDTO), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_TEACHER_BY_ID_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_TEACHER_BY_ID_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> editTeacherByMaxStudentsHDAndPB(EditTeacherByMaxStudentsRequest editTeacherByMaxStudentsHDRequest) {
        try{
            Teacher teacher = teacherRepository.findById(editTeacherByMaxStudentsHDRequest.getTeacherId())
                    .orElseThrow();
            if(editTeacherByMaxStudentsHDRequest.getMaxStudentsHD() != null) {
                teacher.setMaxStudentsHD(editTeacherByMaxStudentsHDRequest.getMaxStudentsHD());
            }
            if(editTeacherByMaxStudentsHDRequest.getMaxStudentsPB() != null) {
                teacher.setMaxStudentsPB(editTeacherByMaxStudentsHDRequest.getMaxStudentsPB());
            }
            teacherRepository.save(teacher);
            return new ResponseEntity<>(new ResponseSucces(Constants.SUCCCES_CODE, Constants.MESSAGE_SUCCES_EDIT), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_ERR_EDIT), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<?> findTeachersHDWithStudents(Long subjectId) {
//        List<TeacherDTO> teacherDTOS = new ArrayList<>();
//        GetAllTeacherRespon respon = new GetAllTeacherRespon();
//        try {
//            List<Teacher> teachers = teacherRepository.findTeachersHDWithStudents(subjectId);
//            for (Teacher teacher : teachers) {
//                TeacherDTO teacherDTO = new TeacherDTO();
//                teacherDTO.setTeacherId(teacher.getTeacherId());
//                teacherDTO.setTeacherName(teacher.getTeacherName());
//                teacherDTO.setEmail(teacher.getEmail());
//                teacherDTO.setResearchDirection(teacher.getResearchDirection());
//                teacherDTO.setPhone(teacher.getPhone());
//                if (teacher.getSubject() != null) {
//                    teacherDTO.setSubjectName(teacher.getSubject().getSubjectName());
//                }
//                teacherDTOS.add(teacherDTO);
//            }
//            respon.setCode(Constants.SUCCCES_CODE);
//            respon.setMessage(Constants.MESSAGE_GET_ALL_SUCCES);
//            respon.setTeachers(teacherDTOS);
//            return new ResponseEntity<>(respon, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_ALL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public ResponseEntity<?> findTeachersPBWithStudents(Long subjectId) {
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        GetAllTeacherRespon respon = new GetAllTeacherRespon();
        try {
            List<Teacher> teachers = teacherRepository.findTeachersPBWithStudents(subjectId);
            for (Teacher teacher : teachers) {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setTeacherId(teacher.getTeacherId());
                teacherDTO.setTeacherName(teacher.getTeacherName());
                teacherDTO.setEmail(teacher.getEmail());
                teacherDTO.setResearchDirection(teacher.getResearchDirection());
                teacherDTO.setPhone(teacher.getPhone());
                if (teacher.getSubject() != null) {
                    teacherDTO.setSubjectName(teacher.getSubject().getSubjectName());
                }
                teacherDTOS.add(teacherDTO);
            }
            respon.setCode(Constants.SUCCCES_CODE);
            respon.setMessage(Constants.MESSAGE_GET_ALL_SUCCES);
            respon.setTeachers(teacherDTOS);
            return new ResponseEntity<>(respon, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_ALL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    public ResponseEntity<?> deleteTeacher(Long id) {
//        try{
//            ResponseSucces respon = new ResponseSucces();
//            teacherRepository.deleteById(id);
//            respon.setCode(Constants.ERR_CODE);
//            respon.setMessage(Constants.MESSAGE_DELETE_TEACHER_SUCCES);
//            return new ResponseEntity<>(respon, HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            ResponErr responErr = new ResponErr();
//            responErr.setCode(Constants.ERR_CODE);
//            responErr.setMessage(Constants.MESSAGE_DELETE_TEACHER_ERR);
//            return new ResponseEntity<>(responErr, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    //    public ResponseEntity<?> findAllBySubject(String teacherName, Long subjectId, Integer limit, Integer offset) {
    //     List<TeacherDTO> teacherDTOS = new ArrayList<>();
    //     GetAllTeacherRespon respon = new GetAllTeacherRespon();
    //     int totalRecords = 0;
    //     int page = offset / limit;
    //     try {
    //         Page<Teacher> teacherPage;

    //         if (subjectId == null || subjectId.equals("")) {
    //             teacherPage = teacherRepository.findAll(PageRequest.of(page, limit));
    //         } else {
    //             Subject subject = subjectRepository.findById(subjectId)
    //                     .orElseThrow(() -> new IllegalArgumentException("Invalid subjectId: " + subjectId));
    //             teacherPage = teacherRepository.findAllBySubject(teacherName, subject, PageRequest.of(page, limit));
    //         }

    //         List<Teacher> teachers = teacherPage.getContent();
    //         totalRecords = (int) teacherPage.getTotalElements();
    //         for (Teacher teacher : teachers) {
    //             TeacherDTO teacherDTO = new TeacherDTO();
    //             teacherDTO.setTeacherId(teacher.getTeacherId());
    //             teacherDTO.setTeacherName(teacher.getTeacherName());
    //             teacherDTO.setEmail(teacher.getEmail());
    //             teacherDTO.setResearchDirection(teacher.getResearchDirection());
    //             teacherDTO.setPhone(teacher.getPhone());
    //             if (teacher.getSubject() != null) {
    //                 teacherDTO.setSubjectName(teacher.getSubject().getSubjectName());
    //             }
    //             teacherDTOS.add(teacherDTO);
    //         }
    //         respon.setCode(Constants.SUCCCES_CODE);
    //         respon.setMessage(Constants.MESSAGE_GET_ALL_SUCCES);
    //         respon.setTotalRecords(totalRecords);
    //         respon.setTeachers(teacherDTOS);
    //         return new ResponseEntity<>(respon, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(new ResponErr(Constants.ERR_CODE, Constants.MESSAGE_GET_ALL_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

      @Transactional
    public TeacherDTO getTeacherById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        return getTeachers(teacherRepository.findByUserUserId(userDetails.getUser().getUserId()));

    }

    private TeacherDTO getTeachers(Teacher teacher){
        TeacherDTO.TeacherDTOBuilder builder = TeacherDTO.builder()
                .teacherId(teacher.getTeacherId())
                .teacherName(teacher.getTeacherName())
                .email(teacher.getEmail())
                .phone(teacher.getPhone())
                .researchDirection(teacher.getResearchDirection());
        if(teacher.getSubject() != null){
            builder.subjectName(teacher.getSubject().getSubjectName());
        }
        return builder.build();
    }
}
