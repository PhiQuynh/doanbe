package com.doan.payload;

import lombok.Data;

@Data
public class EditMasterDetailByGVHDRequest {
    private Long masterDetailId;
    private Long teacherHDId;
//    private String status;
//    private Long statusTeacher;
}
