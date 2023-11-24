package com.doan.repository;

import com.doan.entity.MasterDetail;
import com.doan.entity.Teacher;
import com.doan.entity.TeacherSudent;
import com.doan.entity.TeachershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//public interface TeacherStudentRepository extends JpaRepository<TeacherSudent, Long> {
//
//    TeacherSudent findFirstByTeacherTeacherId(Long teacherId);
//
//    TeacherSudent findFirstByDetailMasterDetailId(Long masterDetailId);
//
//    TeacherSudent findByTeacherAndDetail(Teacher teacher, MasterDetail detail);

//    @Query("SELECT md FROM MasterDetail md WHERE md.teacherHD.id = :teacherId AND md.statusTeacher = :status")
//    List<MasterDetail> findByTeacherIdAndStatus(Long teacherId, TeachershipStatus status);



//    @Query(value = "SELECT * FROM teacher_student as ts WHERE ts.teacher_id = 1 and ts.status = \"PENDING\" ", nativeQuery = true)
//    List<TeacherSudent> findByStudentTeacherId(Long teacherId);
//
//    @Query(value = "SELECT * FROM teacher_student as ts WHERE ts.teacher_id = 1 and ts.status = \"ACCEPTED\" ", nativeQuery = true)
//    List<TeacherSudent> findByStudentTeacherIdAccept(Long teacherId);

//
//    List<MasterDetail> findByTeacherTeacherIdAndStatus (Long teacherId,TeachershipStatus status);
//}
