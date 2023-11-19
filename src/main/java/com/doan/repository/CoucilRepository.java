package com.doan.repository;

import com.doan.entity.Coucil;
import com.doan.entity.Department;
import com.doan.entity.MasterDetail;
import com.doan.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoucilRepository extends JpaRepository<Coucil, Long> {
    @Query("SELECT c FROM Coucil c WHERE ((:status IS NULL AND c.status IS NULL) OR (c.status = :status)) AND c.subject = :subject")
    List<Coucil> findCoucilsByStatus(@Param("subject") Subject subject, @Param("status") Long status);
}
