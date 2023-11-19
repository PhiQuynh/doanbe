package com.doan.payload;

import com.doan.entity.Master;
import lombok.Data;

@Data
public class AddCoucilRequest {
    private String coucilName;
    private Long masterId;
    private Long subjectId;
}
