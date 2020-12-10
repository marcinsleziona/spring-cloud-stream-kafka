package pl.ims.spring.cloud.stream;

import com.jayway.jsonpath.JsonPath;

import java.time.LocalDate;

/*
 * Created on 2020-12-09 11:43
 */
public class ResultBuilder {

    public static Result of(String json, String countryName, String date) {
        String jsonPathPath = "$.dates." + date + ".countries." + countryName;
        return Result.builder()
                .date(LocalDate.parse(JsonPath.read(json, jsonPathPath + ".date")))
                .countryId(JsonPath.read(json, jsonPathPath + ".id"))
                .countryName(JsonPath.read(json, jsonPathPath + ".name"))
                .totalConfirmed(JsonPath.read(json, jsonPathPath + ".today_confirmed"))
                .totalDeaths(JsonPath.read(json, jsonPathPath + ".today_deaths"))
                .totalOpenCases(JsonPath.read(json, jsonPathPath + ".today_open_cases"))
                .totalRecovered(JsonPath.read(json, jsonPathPath + ".today_recovered"))
                .newConfirmed(JsonPath.read(json, jsonPathPath + ".today_new_confirmed"))
                .newDeaths(JsonPath.read(json, jsonPathPath + ".today_new_deaths"))
                .newOpenCases(JsonPath.read(json, jsonPathPath + ".today_new_open_cases"))
                .newRecovered(JsonPath.read(json, jsonPathPath + ".today_new_recovered"))
                .build();
    }
}
