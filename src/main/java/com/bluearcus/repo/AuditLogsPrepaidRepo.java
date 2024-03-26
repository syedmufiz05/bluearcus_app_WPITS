package com.bluearcus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluearcus.model.AuditLogsPrepaid;

public interface AuditLogsPrepaidRepo extends JpaRepository<AuditLogsPrepaid, Integer> {

}
