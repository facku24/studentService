package com.kunix.studentservice.repository;

import com.kunix.studentservice.model.BinDetail;

public interface CustomRepository {
    BinDetail findBinDetail(Integer id);

    void saveBinDetail(Integer lowBound, Integer upperBound);
}
