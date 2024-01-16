package com.bluearcus.repo;

import com.bluearcus.model.RatingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingPlanRepository extends JpaRepository<RatingPlan, Integer> {
    Optional<RatingPlan> findByRatingPlanId(Integer id);

    void deleteByRatingPlanId(Integer id);
}
