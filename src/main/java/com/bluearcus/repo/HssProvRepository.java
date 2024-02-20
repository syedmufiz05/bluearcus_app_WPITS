package com.bluearcus.repo;

import com.bluearcus.model.HssProv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HssProvRepository extends JpaRepository<HssProv, Integer> {
    void deleteByImsiOrMsisdn(String imsi, String msisdn);

    Optional<HssProv> findByImsiOrMsisdn(String imsi, String msisdn);

   
}
