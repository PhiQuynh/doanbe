package com.doan.repository;

import com.doan.entity.Master;
import com.doan.entity.MasterDetail;
import com.doan.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterDetailRepository extends JpaRepository<MasterDetail, Long> {
//    @Query("SELECT md FROM MasterDetail md WHERE md.teacherHD = :teacher")
//    List<MasterDetail> findMasterDetailsByTeacherHD(@Param("teacher") Optional<Teacher> teacher);

    @Query("SELECT md FROM MasterDetail md WHERE md.teacherPB = :teacher")
    List<MasterDetail> findMasterDetailsByTeacherPB(@Param("teacher") Optional<Teacher> teacher);


    @Query("SELECT md FROM MasterDetail md WHERE (md.scoreArgument < 5 " +
            "OR md.scoreCoucil < 5 ) AND md.master = :master")
    List<MasterDetail> findByScoreArgumentLessThanAndMaster(@Param("master") Master master);

    @Query("SELECT md FROM MasterDetail md WHERE (md.scoreArgument >= 5 " +
            "AND md.scoreCoucil >= 5 ) AND md.master = :master")
    List<MasterDetail> getSuccessfulDefenseStudentsAndMaster(@Param("master") Master master);

    @Query("SELECT md FROM MasterDetail md WHERE (md.scoreArgument < 5 " +
            "OR md.scoreCoucil < 5 )")
    List<MasterDetail> findByScoreArgumentLessThan();

    @Query("SELECT md FROM MasterDetail md WHERE (md.scoreArgument >= 5 " +
            "AND md.scoreCoucil >= 5 )")
    List<MasterDetail> getSuccessfulDefenseStudents();

//    @Query(value = "select *\n" +
//            "From masters_details\n" +
//            "where masters_details.teacher_hd_id = ?", nativeQuery = true)
//    List<MasterDetail> listUserByUserId(Long teacher_hd_id);

    @Query("SELECT t FROM MasterDetail t LEFT JOIN t.master s " +
            "WHERE (:studentName IS NULL OR t.studentName LIKE %:studentName%) " +
            "AND (:master IS NULL OR s = :master)")
    Page<MasterDetail> findAllByMater(@Param("studentName") String studentName,
                                      @Param("master") Master master,
                                      Pageable pageable);
}
