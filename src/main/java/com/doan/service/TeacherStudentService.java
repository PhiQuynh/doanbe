package com.doan.service;


import com.doan.dto.StudentAcceptDTO;
import com.doan.dto.StudentInvitationDTO;
import com.doan.dto.TeacherStudentRequest;
import com.doan.entity.MasterDetail;
import com.doan.entity.Teacher;
import com.doan.entity.TeacherSudent;
import com.doan.entity.TeachershipStatus;
import com.doan.repository.MasterDetailRepository;
import com.doan.repository.TeacherRepository;
import com.doan.repository.TeacherStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherStudentService {
    @Autowired
    TeacherStudentRepository teacherStudentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    MasterDetailRepository masterDetailRepository;

    public ResponseEntity<String> sendInvitation(@RequestBody TeacherStudentRequest teacherStudent) {
        Teacher teacher = teacherRepository.findById(teacherStudent.getTeacherId()).orElseThrow();
        MasterDetail masterDetail = masterDetailRepository.findById(teacherStudent.getMasterDetailId()).orElseThrow();
        TeacherSudent sudent = new TeacherSudent();
        // Kiểm tra và xử lý dữ liệu truyền vào (nếu cần)
        sudent.setTeacher(teacher);
        sudent.setDetail(masterDetail);
        sudent.setStatus(TeachershipStatus.PENDING);
        teacherStudentRepository.save(sudent);
        return new ResponseEntity<>("Chờ xác nhận", HttpStatus.OK);
    }

    public ResponseEntity<String> acceptInvitation(@RequestBody TeacherStudentRequest request) {
        Long teacherId = request.getTeacherId();
        Long masterDetailId = request.getMasterDetailId();

        // Kiểm tra và xử lý dữ liệu truyền vào (nếu cần)

        TeacherSudent teacherStudent = new TeacherSudent();

        if (teacherId != null) {
            teacherStudent = teacherStudentRepository.findByTeacherTeacherId(teacherId);
        } else if (masterDetailId != null) {
            teacherStudent = teacherStudentRepository.findByDetailMasterDetailId(masterDetailId);
        }

        if (teacherStudent == null) {
            // Xử lý lỗi khi không tìm thấy TeacherStudent
            return new ResponseEntity<>("Không tìm thấy TeacherStudent", HttpStatus.NOT_FOUND);
        }

        teacherStudent.setStatus(TeachershipStatus.ACCEPTED);
        teacherStudentRepository.save(teacherStudent);

        return new ResponseEntity<>("Lời mời đã được chấp nhận", HttpStatus.OK);
    }

    public ResponseEntity<String> not_acceptInvitation(@RequestBody TeacherStudentRequest request) {
        Long teacherId = request.getTeacherId();
        Long masterDetailId = request.getMasterDetailId();

        // Kiểm tra và xử lý dữ liệu truyền vào (nếu cần)

        TeacherSudent teacherStudent = new TeacherSudent();

        if (teacherId != null) {
            teacherStudent = teacherStudentRepository.findByTeacherTeacherId(teacherId);
        } else if (masterDetailId != null) {
            teacherStudent = teacherStudentRepository.findByDetailMasterDetailId(masterDetailId);
        }

        if (teacherStudent == null) {
            // Xử lý lỗi khi không tìm thấy TeacherStudent
            return new ResponseEntity<>("Không tìm thấy TeacherStudent", HttpStatus.NOT_FOUND);
        }

        teacherStudent.setStatus(TeachershipStatus.NOTACCEPTED);
        teacherStudentRepository.save(teacherStudent);

        return new ResponseEntity<>("Không chấp nhận lời mời", HttpStatus.OK);
    }

    public ResponseEntity<List<StudentInvitationDTO>> getStudentsByTeacherId(Long teacherId) {
        List<TeacherSudent> teacherStudents = teacherStudentRepository.findByStudentTeacherId(teacherId);
        List<StudentInvitationDTO> students = new ArrayList<>();

        for (TeacherSudent teacherStudent : teacherStudents) {
            StudentInvitationDTO student = new StudentInvitationDTO();
            student.setMasterDetailId(teacherStudent.getDetail().getMasterDetailId());
            student.setMssv(teacherStudent.getDetail().getMssv());
            student.setStudentName(teacherStudent.getDetail().getStudentName());
            student.setStudentClass(teacherStudent.getDetail().getStudentClass());
            student.setTitleNameVn(teacherStudent.getDetail().getTitleNameVn());
            student.setTitleNameEn(teacherStudent.getDetail().getTitleNameEn());

            students.add(student);
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    public ResponseEntity<List<StudentAcceptDTO>> getStudentsByTeacherIdAccept(Long teacherId) {
        List<TeacherSudent> teacherStudents = teacherStudentRepository.findByStudentTeacherId(teacherId);
        List<StudentAcceptDTO> students = new ArrayList<>();

        for (TeacherSudent teacherStudent : teacherStudents) {
            StudentAcceptDTO student = new StudentAcceptDTO();
            student.setMasterDetailId(teacherStudent.getDetail().getMasterDetailId());
            student.setMssv(teacherStudent.getDetail().getMssv());
            student.setStudentName(teacherStudent.getDetail().getStudentName());
            student.setStudentClass(teacherStudent.getDetail().getStudentClass());
            student.setTitleNameVn(teacherStudent.getDetail().getTitleNameVn());
            student.setTitleNameEn(teacherStudent.getDetail().getTitleNameEn());
            student.setTeacherPB(teacherStudent.getDetail().getTeacherPB().getTeacherName());
            student.setScoreArgument(teacherStudent.getDetail().getScoreArgument());
            student.setScoreCoucil(teacherStudent.getDetail().getScoreCoucil());
            students.add(student);
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }



}
