package com.doan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "teachers")
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", unique = true, nullable = false)
    private Long teacherId;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "sex", nullable = false)
    private String sex;
    @Column(name = "image")
    private String image;
    @Column(name = "research_direction")
    private String researchDirection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIgnore
    private Subject subject;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TeacherCoucil> teacherCoucils;

//    @OneToMany(mappedBy = "teacherHD", cascade = CascadeType.ALL)
//    private List<MasterDetail> masterDetailHDs;

    @OneToMany(mappedBy = "teacherPB", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MasterDetail> masterDetailPBs;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user ;

    @Column(name = "max_students_hd")
    private Integer maxStudentsHD;

    @Column(name = "max_students_pb")
    private Integer maxStudentsPB;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TeacherSudent> sudentList;
}
