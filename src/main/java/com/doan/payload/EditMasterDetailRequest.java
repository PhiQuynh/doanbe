package com.doan.payload;

import lombok.Data;

@Data
public class EditMasterDetailRequest {
    private Long masterDetailId;
    private String titleNameVn;
    private String titleNameEn;
    private Long teacherHDId;
}
