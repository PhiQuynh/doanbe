package com.doan.payload;

import lombok.Data;

import java.util.Date;

@Data
public class AddMasterRequest {
    private String masterName;
    private String startDate;
    private String endDate;
    private Long subjectId;
}
