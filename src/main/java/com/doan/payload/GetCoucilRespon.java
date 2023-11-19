package com.doan.payload;

import com.doan.dto.CoucilDTO;
import com.doan.dto.MasterDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetCoucilRespon {
    private int code;
    private String message;
    private List<CoucilDTO> coucilDTOS;
}
