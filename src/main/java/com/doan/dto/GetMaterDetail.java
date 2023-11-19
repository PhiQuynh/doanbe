package com.doan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMaterDetail {
    private Long masterDetailId;
    private Long mssv;
    private String studentName;
    private String studentClass;
    private String titleNameVn;
    private String titleNameEn;
    private Double scoreArgument;
    private Double scoreCoucil;
    private Long statusTeacher;
    private Long statusDepartment;
    private String teacherHDName;
    private String teacherPBName;
    private String materName;

    //    private User userId;
    private String image;
}
