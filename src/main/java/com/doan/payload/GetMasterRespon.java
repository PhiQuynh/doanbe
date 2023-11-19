package com.doan.payload;

import com.doan.dto.MasterDto;
import com.doan.entity.Master;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMasterRespon {
    private int code;
    private String message;
    private int totals;
    private List<MasterDto> masterDtos;
}
