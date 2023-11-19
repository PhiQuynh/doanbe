package com.doan.dto;

import lombok.Data;

@Data
public class UpdateCouncli {
    public String coucilName;
    public Long masterId;
    public Long subjectId;
    public  Long status;
}
