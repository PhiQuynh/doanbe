package com.doan.repository;


import com.doan.entity.Teacher;
import com.doan.entity.TeacherCoucil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherCoucliRepository extends JpaRepository<TeacherCoucil, Long> {
    List<TeacherCoucil> findByCoucilCoucilId(Long councilId);

    List<TeacherCoucil> findByTeacherTeacherId(Long teacherId);


}
