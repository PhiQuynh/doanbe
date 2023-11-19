package com.doan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCouncli {
    public Long coucilId;
    public String coucilName;
    public String masterName;
    public String subjectName;
}
