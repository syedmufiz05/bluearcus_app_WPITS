package com.bluearcus.repo;

import com.bluearcus.dto.RatingProfileDto;
import com.bluearcus.model.RatingProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingProfileRepository extends JpaRepository<RatingProfile, Integer> {
    @Query("select new com.bluearcus.dto.RatingProfileDto(ratingProfile.id,ratingProfile.category.name,ratingProfile.callingParty,ratingProfile.ratingPlan.ratingPlanId) from RatingProfile ratingProfile")
    List<RatingProfileDto> fetchAll();
}
