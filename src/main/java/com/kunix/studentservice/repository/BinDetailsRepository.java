package com.kunix.studentservice.repository;

import com.kunix.studentservice.model.BinDetail;
import com.kunix.studentservice.model.hypersistence.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BinDetailsRepository extends JpaRepository<BinDetail, Range<Integer>> {

    @Query(value = "SELECT * FROM bin_details bd WHERE bd.bin_range @> :bin", nativeQuery = true)
    Optional<BinDetail> findByBinRangeContainingBin(@Param("bin") Integer bin);
}
