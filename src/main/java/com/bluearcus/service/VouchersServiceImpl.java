package com.bluearcus.service;

import com.bluearcus.dto.VouchersDto;
import com.bluearcus.dto.VouchersDtoList;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.Vouchers;
import com.bluearcus.repo.VouchersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VouchersServiceImpl implements VouchersService {
    @Autowired
    private VouchersRepository vouchersRepository;

    @Override
    public ResponseEntity saveVoucher(VouchersDto vouchersDto) {
        Optional<Vouchers> voucherDb = vouchersRepository.findByVoucherNo(vouchersDto.getVoucherNo() != null ? vouchersDto.getVoucherNo() : "0");
        if (!voucherDb.isPresent()) {
            Vouchers voucher = new Vouchers();
            voucher.setActivatedDate(new Date());
            voucher.setAmount(vouchersDto.getAmount() != null ? vouchersDto.getAmount() : Float.valueOf(""));
            voucher.setBatchId(vouchersDto.getBatchId() != null ? vouchersDto.getBatchId() : "");
            voucher.setCreatedDate(new Date());
            voucher.setCurrency(vouchersDto.getCurrency() != null ? vouchersDto.getCurrency() : "");
            voucher.setExpiryDate(new Date());
            voucher.setGroupCode(vouchersDto.getGroupCode() != null ? vouchersDto.getGroupCode() : "");
            voucher.setInstructionId(vouchersDto.getInstructionId() != null ? vouchersDto.getInstructionId() : "");
            voucher.setPayeeFunctionalId(vouchersDto.getPayeeFunctionalId() != null ? vouchersDto.getPayeeFunctionalId() : "");
            voucher.setRegisteringInstitutionId(vouchersDto.getRegisteringInstitutionId() != null ? vouchersDto.getRegisteringInstitutionId() : "");
            voucher.setRequestId(vouchersDto.getRequestId() != null ? vouchersDto.getRequestId() : "");
            voucher.setSerialNo(vouchersDto.getSerialNo() != null ? vouchersDto.getSerialNo() : "");
            voucher.setStatus(vouchersDto.getStatus() != null ? vouchersDto.getStatus() : "");
            voucher.setVoucherNo(vouchersDto.getVoucherNo() != null ? vouchersDto.getVoucherNo() : "");
            vouchersRepository.save(voucher);
            VouchersDto vouchersDtoNew = new VouchersDto(voucher.getId(), fetchReadableDateTime(voucher.getActivatedDate()), voucher.getAmount(), voucher.getBatchId(), fetchReadableDateTime(voucher.getCreatedDate()), voucher.getCurrency(), fetchReadableDateTime(voucher.getExpiryDate()), voucher.getGroupCode(), voucher.getInstructionId(), voucher.getPayeeFunctionalId(), voucher.getRegisteringInstitutionId(), voucher.getRequestId(), voucher.getSerialNo(), voucher.getStatus(), voucher.getVoucherNo());
            return new ResponseEntity<>(vouchersDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomMessage(HttpStatus.CONFLICT.value(), "Duplicate Voucher No."));
    }

    @Override
    public ResponseEntity editVoucher(String voucherNo, VouchersDto vouchersDto) {
        Optional<Vouchers> voucherDb = vouchersRepository.findByVoucherNo(voucherNo);
        if (voucherDb.isPresent()) {
            Vouchers voucher = voucherDb.get();
            voucher.setAmount(vouchersDto.getAmount() != null ? vouchersDto.getAmount() : voucher.getAmount());
            voucher.setBatchId(vouchersDto.getBatchId() != null ? vouchersDto.getBatchId() : voucher.getBatchId());
            voucher.setCurrency(vouchersDto.getCurrency() != null ? vouchersDto.getCurrency() : voucher.getCurrency());
            voucher.setGroupCode(vouchersDto.getGroupCode() != null ? vouchersDto.getGroupCode() : voucher.getGroupCode());
            voucher.setInstructionId(vouchersDto.getInstructionId() != null ? vouchersDto.getInstructionId() : voucher.getInstructionId());
            voucher.setPayeeFunctionalId(vouchersDto.getPayeeFunctionalId() != null ? vouchersDto.getPayeeFunctionalId() : voucher.getPayeeFunctionalId());
            voucher.setRegisteringInstitutionId(vouchersDto.getRegisteringInstitutionId() != null ? vouchersDto.getRegisteringInstitutionId() : voucher.getRegisteringInstitutionId());
            voucher.setRequestId(vouchersDto.getRequestId() != null ? vouchersDto.getRequestId() : voucher.getRequestId());
            voucher.setSerialNo(vouchersDto.getSerialNo() != null ? vouchersDto.getSerialNo() : voucher.getSerialNo());
            voucher.setStatus(vouchersDto.getStatus() != null ? vouchersDto.getStatus() : voucher.getStatus());
            vouchersRepository.save(voucher);
            VouchersDto vouchersDtoNew = new VouchersDto(voucher.getId(), fetchReadableDateTime(voucher.getActivatedDate()), voucher.getAmount(), voucher.getBatchId(), fetchReadableDateTime(voucher.getCreatedDate()), voucher.getCurrency(), fetchReadableDateTime(voucher.getExpiryDate()), voucher.getGroupCode(), voucher.getInstructionId(), voucher.getPayeeFunctionalId(), voucher.getRegisteringInstitutionId(), voucher.getRequestId(), voucher.getSerialNo(), voucher.getStatus(), voucher.getVoucherNo());
            return new ResponseEntity<>(vouchersDtoNew, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Voucher No."));
    }

    @Transactional
    @Override
    public ResponseEntity deleteVoucher(String voucherNo) {
        Optional<Vouchers> voucher = vouchersRepository.findByVoucherNo(voucherNo);
        if (voucher.isPresent()) {
            Vouchers voucherDb = voucher.get();
            vouchersRepository.deleteById(voucherDb.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Voucher No."));
    }

    @Override
    public ResponseEntity getVoucher(String voucherNo) {
        Optional<Vouchers> voucher = vouchersRepository.findByVoucherNo(voucherNo);
        if (voucher.isPresent()) {
            Vouchers vouchersDb = voucher.get();
            VouchersDto vouchersDto = new VouchersDto();
            vouchersDto.setId(vouchersDb.getId());
            vouchersDto.setActivatedDate(fetchReadableDateTime(vouchersDb.getActivatedDate()));
            vouchersDto.setAmount(vouchersDb.getAmount());
            vouchersDto.setBatchId(vouchersDb.getBatchId());
            vouchersDto.setCreatedDate(fetchReadableDateTime(vouchersDb.getCreatedDate()));
            vouchersDto.setCurrency(vouchersDb.getCurrency());
            vouchersDto.setExpiryDate(fetchReadableDateTime(vouchersDb.getExpiryDate()));
            vouchersDto.setGroupCode(vouchersDb.getGroupCode());
            vouchersDto.setInstructionId(vouchersDb.getInstructionId());
            vouchersDto.setPayeeFunctionalId(vouchersDb.getPayeeFunctionalId());
            vouchersDto.setRegisteringInstitutionId(vouchersDb.getRegisteringInstitutionId());
            vouchersDto.setRequestId(vouchersDb.getRequestId());
            vouchersDto.setSerialNo(vouchersDb.getSerialNo());
            vouchersDto.setStatus(vouchersDb.getStatus());
            vouchersDto.setVoucherNo(vouchersDb.getVoucherNo());
            return new ResponseEntity<>(vouchersDto, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid Voucher No."));
    }

    @Override
    public List<VouchersDto> getAllVoucher() {
        List<VouchersDtoList> vouchersDbList = vouchersRepository.fetchAllVouchers();
        List<VouchersDto> vouchersDtoList = new ArrayList<>();
        for (VouchersDtoList vouchersDb : vouchersDbList) {
            VouchersDto vouchersDto = new VouchersDto();
            vouchersDto.setId(vouchersDb.getId());
            vouchersDto.setActivatedDate(fetchReadableDateTime(vouchersDb.getActivatedDate()));
            vouchersDto.setAmount(vouchersDb.getAmount());
            vouchersDto.setBatchId(vouchersDb.getBatchId());
            vouchersDto.setCreatedDate(fetchReadableDateTime(vouchersDb.getCreatedDate()));
            vouchersDto.setCurrency(vouchersDb.getCurrency());
            vouchersDto.setExpiryDate(fetchReadableDateTime(vouchersDb.getExpiryDate()));
            vouchersDto.setGroupCode(vouchersDb.getGroupCode());
            vouchersDto.setInstructionId(vouchersDb.getInstructionId());
            vouchersDto.setPayeeFunctionalId(vouchersDb.getPayeeFunctionalId());
            vouchersDto.setRegisteringInstitutionId(vouchersDb.getRegisteringInstitutionId());
            vouchersDto.setRequestId(vouchersDb.getRequestId());
            vouchersDto.setSerialNo(vouchersDb.getSerialNo());
            vouchersDto.setStatus(vouchersDb.getStatus());
            vouchersDto.setVoucherNo(vouchersDb.getVoucherNo());
            vouchersDtoList.add(vouchersDto);
        }
        return vouchersDtoList;
    }

    private String fetchReadableDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
