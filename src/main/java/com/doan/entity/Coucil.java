package com.doan.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "coucils")
@Data
public class Coucil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coucil_id", unique = true, nullable = false)
    private Long coucilId;

    @Column(name = "coucil_name", nullable = false)
    private String coucilName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    @JsonBackReference
    private Master master;

    @OneToMany(mappedBy = "coucil", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<TeacherCoucil> teacherCoucilList;

    @Column(name = "status")
    private Long status;

    @OneToMany(mappedBy = "coucil", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MasterDetail> masterDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonBackReference
    private Subject subject;
}
