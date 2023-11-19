package com.doan.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "masters")
@Data
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "master_id", unique = true, nullable = false)
    private Long masterId;

    @Column(name = "master_name", nullable = false)
    private String masterName;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "status_department")
    private Long statusDepartment;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL)
    private List<MasterDetail> masterDetails;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Coucil> coucils;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonBackReference
    private Subject subject;

}
