package com.doan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStudent {
    public String studentName;
    public Long mssv;
    public String studentClass;
    public String titleNameVn;
    public String titleNameEn;
    public String matername;
    public String teacherName;
    public Double scoreCoucil;
    public Double scoreArgument;
    public String startDate;
    public String endDate;
    public String teacherPBName;
    public String councliName;
}
