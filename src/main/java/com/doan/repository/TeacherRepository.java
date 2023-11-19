package com.doan.repository;

import com.doan.entity.Coucil;
import com.doan.entity.Subject;
import com.doan.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t LEFT JOIN t.subject s " +
            "WHERE (:teacherName IS NULL OR t.teacherName LIKE %:teacherName%) " +
            "AND (:subjectId IS NULL OR t.subject.subjectId = :subjectId)")
    Page<Teacher> findAll(@Param("teacherName") String teacherName,
                                   @Param("subjectId") Long subjectId,
                                   Pageable pageable);

//    List<Teacher> findAllByCoucil(Coucil coucil);

//    @Query("SELECT DISTINCT t FROM Teacher t " +
//            "INNER JOIN MasterDetail md ON t.teacherId = md.teacherHD.teacherId " +
//            "WHERE md.statusTeacher = 1 " +
//            "AND t.subject.subjectId = :subjectId")
//    List<Teacher> findTeachersHDWithStudents(@Param("subjectId") Long subjectId);

    @Query("SELECT DISTINCT t FROM Teacher t " +
            "INNER JOIN MasterDetail md ON t.teacherId = md.teacherPB.teacherId " +
            "AND t.subject.subjectId = :subjectId")
    List<Teacher> findTeachersPBWithStudents(@Param("subjectId") Long subjectId);

    Teacher findByUserUserId(Long userId);
}
