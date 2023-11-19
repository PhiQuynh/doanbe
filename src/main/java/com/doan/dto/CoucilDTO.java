package com.doan.dto;

import com.doan.entity.Master;
import com.doan.entity.Teacher;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;

@Data
public class CoucilDTO implements Serializable {
    private Long coucilId;

    private String coucilName;

    private String masterName;

    private String subjectName;
    private List<Teacher> teachers;
}
