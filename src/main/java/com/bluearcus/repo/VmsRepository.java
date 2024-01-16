package com.bluearcus.repo;

import com.bluearcus.dto.VmsDto;
import com.bluearcus.model.Vms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VmsRepository extends JpaRepository<Vms, Integer> {
    Optional<Vms> findByMsisdn(String msisdn);

    void deleteByMsisdn(String msisdn);

    @Query("select new com.bluearcus.dto.VmsDto(vms.vmsId,vms.msisdn,vms.systemId,vms.mailboxId,vms.registerFlag,vms.activeFlag,vms.lockedFlag,vms.language,vms.temporaryGreeting,vms.greetingTypeSystem,vms.passwordFlag,vms.callbackFlag,vms.cliFlag,vms.password,vms.callbackTimeout,vms.accessLogs.id) from Vms vms")
    List<VmsDto> fetchAllVmsRecord();
}
