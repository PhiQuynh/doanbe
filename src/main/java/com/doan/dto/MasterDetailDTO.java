package com.doan.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class MasterDetailDTO implements Serializable {
    private Long masterDetailId;
    private Long mssv;
    private String studentName;
    private String studentClass;
    private String titleNameVn;
    private String titleNameEn;
    private Double scoreArgument;
    private Double scoreCoucil;
    private String masterName;
    private String teacherNameHd;
    private String teacherNamePb;
    private Long masterId;
    private String startDate;
    private String endDate;
    private Long statusDepartment;
    private String teacherHD;
    private String teacherPB;
}
