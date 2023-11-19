package com.doan.dto;
import lombok.Data;

@Data
public class UpdateMaster {
    public String masterName;
    public String startDate;
    public String endDate;
    public Long subjectId;
}
