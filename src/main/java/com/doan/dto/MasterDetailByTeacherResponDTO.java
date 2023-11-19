package com.doan.dto;

import lombok.Data;

@Data
public class MasterDetailByTeacherResponDTO {
    private Long masterDetailId;
    private Long mssv;
    private String studentName;
    private String studentClass;
    private String titleNameVn;
    private String titleNameEn;
    private Double scoreArgument;
    private Double scoreCoucil;
    private String masterName;
    private String startDate;
    private String endDate;
    private String teacherPB;
    private String teacherHD;
}
