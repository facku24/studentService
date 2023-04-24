package com.kunix.studentservice.model;

import com.kunix.studentservice.model.hypersistence.PostgresSQLRangeType;
import com.kunix.studentservice.model.hypersistence.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;


import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "BinDetail")
@Table(name = "bin_details")
@TypeDef(typeClass = PostgresSQLRangeType.class, defaultForType = Range.class)
public class BinDetail {
    @Id
    @Column(name = "bin_range", columnDefinition = "int4range")
    private Range<Integer> binRange;

    @Column(name = "card_type")
    private String cardType;

    private String brand;

    private String country;
}
