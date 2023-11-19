package com.doan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "masters_details")
public class MasterDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "master_detail_id", unique = true, nullable = false)
    private Long masterDetailId;

    @Column(name = "mssv", nullable = false)
    private Long mssv;

    @Column(name = "studentName", nullable = false)
    private String studentName;

    @Column(name = "student_class", nullable = false)
    private String studentClass;

    @Column(name = "title_name_vn", nullable = false)
    private String titleNameVn;

    @Column(name = "title_name_en", nullable = false)
    private String titleNameEn;

    @Column(name = "score_argument")
    private Double scoreArgument;

    @Column(name = "score_coucil")
    private Double scoreCoucil;

    @Column(name = "status_teacher")
    private Long statusTeacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    @JsonBackReference
    private Master master;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacher_hd_id")
//    @JsonBackReference
//    private Teacher teacherHD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_pb_id")
    @JsonBackReference
    private Teacher teacherPB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coucil_id")
    @JsonBackReference
    private Coucil coucil;

    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TeacherSudent> sudentList;
}
