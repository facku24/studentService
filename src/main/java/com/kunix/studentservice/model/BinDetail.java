package com.kunix.studentservice.model;

import io.hypersistence.utils.hibernate.type.range.PostgreSQLRangeType;
import io.hypersistence.utils.hibernate.type.range.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;


import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "BinDetail")
@Table(name = "bin_details")
@TypeDef(typeClass = PostgreSQLRangeType.class, defaultForType = Range.class)
public class BinDetail {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     @JsonSubTypes.Type(PostgreSQLRangeType.class)
    @Column(name = "bin_range", columnDefinition = "int4range")*/
    @Id
    //@Type(PostgreSQLRangeType.class)
    @Column(name = "bin_range", columnDefinition = "int4range")
    private Range<Integer> binRange;

    @Column(name = "card_type")
    private String cardType;

    private String brand;

    private String country;
}
