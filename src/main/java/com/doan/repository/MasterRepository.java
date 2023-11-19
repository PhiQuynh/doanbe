package com.doan.repository;

import com.doan.entity.Master;
import com.doan.entity.MasterDetail;
import com.doan.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    @Query("SELECT m FROM Master m WHERE ((:status IS NULL AND m.statusDepartment IS NULL) OR (m.statusDepartment = :status)) AND m.subject = :subject")
    List<Master> findMasterByStatusDepartment(@Param("subject") Subject subject, @Param("status") Long status);

    @Query("SELECT m FROM Master m WHERE m.subject = :subject")
    List<Master> findMasterBySubject(@Param("subject") Subject subject);

    List<Master> findByStartDateContains(int year);
}
