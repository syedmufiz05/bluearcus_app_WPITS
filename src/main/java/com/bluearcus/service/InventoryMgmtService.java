package com.bluearcus.service;

import com.bluearcus.dto.InventoryMgmtDto;
import com.bluearcus.dto.InventoryMgmtDtoList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryMgmtService {
    ResponseEntity saveInventory(InventoryMgmtDto inventoryMgmtDto);

    ResponseEntity editInventory(Integer inventoryId, InventoryMgmtDtoList inventoryMgmtDtoList);

    ResponseEntity deleteInventory(Integer inventoryId);

    List<InventoryMgmtDto> getAllInventory();

    List<InventoryMgmtDto> searchRecord(String keyword);

}
