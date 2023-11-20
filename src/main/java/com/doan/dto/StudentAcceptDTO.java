package com.doan.dto;

import lombok.Data;

@Data
public class StudentAcceptDTO {
    private Long masterDetailId;
    private Long mssv;
    private String studentName;
    private String studentClass;
    private String titleNameVn;
    private String titleNameEn;
    private String teacherPB;
    private Double scoreCoucil;
    private Double scoreArgument;
    private String coucilName;
    private String masterName;
}
