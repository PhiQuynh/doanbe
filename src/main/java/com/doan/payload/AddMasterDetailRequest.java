package com.doan.payload;

import lombok.Data;

@Data
public class AddMasterDetailRequest {
    private Long mssv;
    private String studentName;
    private String studentClass;
    private String titleNameVn;
    private String titleNameEn;
    private Long masterId;
    private Long teacherHDId;
}
