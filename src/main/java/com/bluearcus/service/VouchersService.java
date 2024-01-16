package com.bluearcus.service;

import com.bluearcus.dto.VouchersDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VouchersService {
    public ResponseEntity saveVoucher(VouchersDto vouchersDto);

    public ResponseEntity editVoucher(String voucherNo, VouchersDto vouchersDto);

    public ResponseEntity deleteVoucher(String voucherNo);

    public ResponseEntity getVoucher(String voucherNo);

    public List<VouchersDto> getAllVoucher();
}
