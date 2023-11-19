package com.doan.repository;

import com.doan.entity.Subject;
import com.doan.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT COUNT(md) " +
            "FROM Subject s " +
            "JOIN s.masters m " +
            "JOIN m.masterDetails md " +
            "WHERE md.scoreArgument >= 5 " +
            "AND md.scoreCoucil >= 5 " +
            "AND s.subjectId = :subjectId")
    int countSuccessfulDefenseStudentsBySubjectId(Long subjectId);

    @Query("SELECT COUNT(md) " +
            "FROM Subject s " +
            "JOIN s.masters m " +
            "JOIN m.masterDetails md " +
            "WHERE s.subjectId = :subjectId")
    int countStudentsBySubjectId(Long subjectId);
}
