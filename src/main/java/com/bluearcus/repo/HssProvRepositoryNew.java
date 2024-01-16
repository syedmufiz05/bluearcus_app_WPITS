package com.bluearcus.repo;

import com.bluearcus.model.HssProvNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HssProvRepositoryNew extends JpaRepository<HssProvNew, Integer> {
    Optional<HssProvNew> findByImsiOrMsisdn(String imsi, String msisdn);
}
