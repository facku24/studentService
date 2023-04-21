package com.kunix.studentservice.controller;

import com.kunix.studentservice.model.BinDetail;
import com.kunix.studentservice.repository.BinDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class BinDetailsController {
    @Autowired
    private BinDetailsRepository binDetailsRepository;

    @GetMapping(value = "/details/{bin}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BinDetail> findByBin(@PathVariable final Integer bin){
        Optional<BinDetail> binDetailOptional = binDetailsRepository.findByBinRangeContainingBin(bin);
        BinDetail binDetail = binDetailOptional.orElse(new BinDetail());
        return new ResponseEntity<>(binDetail, HttpStatus.OK);
    }

}
