package pl.ims.spring.cloud.stream;

import lombok.*;

import java.time.LocalDate;

/*
 * Created on 2020-12-01 08:30
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Result {
    private String countryId;
    private LocalDate date;
    private String countryName;
    private Integer totalConfirmed;
    private Integer totalDeaths;
    private Integer totalOpenCases;
    private Integer totalRecovered;
    private Integer newConfirmed;
    private Integer newDeaths;
    private Integer newOpenCases;
    private Integer newRecovered;
}
