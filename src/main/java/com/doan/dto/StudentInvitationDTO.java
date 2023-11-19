package com.doan.dto;

import lombok.Data;

@Data
public class StudentInvitationDTO {

    private Long masterDetailId;
    private Long mssv;
    private String studentName;
    private String studentClass;
    private String titleNameVn;
    private String titleNameEn;
}
