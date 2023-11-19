package com.doan.payload;

import com.doan.dto.MasterDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetMasterDetailRespon {
    private int code;
    private String message;
    private int totalRecords;
    private List<MasterDetailDTO> masterDetails;
}
