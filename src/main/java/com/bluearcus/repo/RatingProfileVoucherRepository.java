package com.bluearcus.repo;

import com.bluearcus.model.RatingProfileVoucher;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingProfileVoucherRepository extends JpaRepository<RatingProfileVoucher, Integer> {

	Optional<RatingProfileVoucher> findByPackName(String packName);
	
	Optional<RatingProfileVoucher> findByDataBalanceAndCallBalance(Integer dataBalance, Integer callBalance);

	Optional<RatingProfileVoucher> findByDataBalance(Integer dataBalance);

	Optional<RatingProfileVoucher> findByCallBalance(Integer callBalance);
	
	List<RatingProfileVoucher> findByCategoryName(String ctgName);
	
	List<RatingProfileVoucher> findByPackFor(String packFor);

	@Query("select ratingProfileVoucher from RatingProfileVoucher ratingProfileVoucher where (ratingProfileVoucher.packName) like LOWER(CONCAT('%', :keyword, '%')) or (ratingProfileVoucher.packType) like LOWER(CONCAT('%', :keyword, '%')) or (ratingProfileVoucher.categoryName) like LOWER(CONCAT('%', :keyword, '%'))")
	List<RatingProfileVoucher> searchVoucherByName(@Param("keyword") String keyword);
}
