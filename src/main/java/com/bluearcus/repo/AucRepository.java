package com.bluearcus.repo;

import com.bluearcus.model.Auc;
import com.bluearcus.dto.AucDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AucRepository extends JpaRepository<Auc, Integer> {
    Optional<Auc> findByImsi(String imsi);

    void deleteByImsi(String imsi);

    @Query("select new com.bluearcus.dto.AucDto(auc.aucId,auc.imsi,auc.ki,auc.opc,auc.a3a8Version,auc.status,auc.accessLogs.id) from Auc auc")
    List<AucDto> fetchAllAucRecord();
}

