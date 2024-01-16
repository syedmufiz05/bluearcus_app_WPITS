package com.bluearcus.repo;

import com.bluearcus.dto.HssProvDto;
import com.bluearcus.model.HssProv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HssProvRepository extends JpaRepository<HssProv, Integer> {
    void deleteByImsiOrMsisdn(String imsi, String msisdn);

    Optional<HssProv> findByImsiOrMsisdn(String imsi, String msisdn);

    @Query("select new com.bluearcus.dto.HssProvDto(hssProv.hssprovId,hssProv.imsi,hssProv.imsiFlag,hssProv.msisdn,hssProv.nam,hssProv.odb,hssProv.baoc,hssProv.boic,hssProv.osb1,hssProv.osb2,hssProv.baic,hssProv.roaming,hssProv.bearerService,hssProv.telephone,hssProv.sms,hssProv.cfuA,hssProv.cfuR,hssProv.cfuP,hssProv.cfbP,hssProv.cfnryP,hssProv.cfnryT,hssProv.cfnrcP,hssProv.cwA,hssProv.cwP,hssProv.chP,hssProv.camel,hssProv.oCsi,hssProv.tCsi,hssProv.ssCsi,hssProv.smsCsi,hssProv.oCsiScfNo,hssProv.tCsiScfNo,hssProv.ssCsiScfNo,hssProv.smsSciScfNo,hssProv.gprsFlag,hssProv.epsFlag,hssProv.ard,hssProv.epsUserTpl,hssProv.defEps,hssProv.contextD,hssProv.apnCtxtList,hssProv.imsFlag,hssProv.subscriberProfId,hssProv.accessLogs.id) from HssProv hssProv")
    List<HssProvDto> fetchAllHssProvRecord();
}
