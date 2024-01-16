package com.bluearcus.repo;

import com.bluearcus.dto.DestinationDto;
import com.bluearcus.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    @Query("select new com.bluearcus.dto.DestinationDto(dest.id,dest.name,dest.type,dest.remarks,dest.active,dest.accessLogs.id) from Destination dest")
    List<DestinationDto> fetchAllDestination();
}
