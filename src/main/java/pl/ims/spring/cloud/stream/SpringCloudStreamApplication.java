package pl.ims.spring.cloud.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@SpringBootApplication
@Slf4j
public class SpringCloudStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PollableBean(splittable = true)
    public Supplier<Flux<Country>> pullCountries(RestTemplate restTemplate) {
        return () -> {
            try {
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.covid19tracking.narrativa.com/api/countries");
                Countries countries = restTemplate.getForObject(builder.toUriString(), Countries.class);

                if (countries != null && countries.getCountries().size() > 0) {
                    return Flux.just(countries.getCountries().toArray(new Country[0]));
                } else {
                    return null;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Flux.empty();
            }
        };
    }

    @Bean
    public Consumer<KStream<String, Country>> printCountriesK() {
        return input ->
                input.foreach((key, value) -> {
                    System.out.println("Key: " + key + " Value: " + value);
                });
    }

    @Bean
    public Function<Flux<Country>, Flux<Result>> pullResults(RestTemplate restTemplate) {
        LocalDate startDate = LocalDate.now().minusDays(5);
        return value ->
                value
                        .filter(country -> country.getId().equals("poland") || country.getId().equals("germany"))
                        .map(country -> getResultsFromDate(restTemplate, country, startDate))
                        .flatMapIterable(results -> results);
    }


    List<Result> getResultsFromDate(RestTemplate restTemplate, Country country, LocalDate startDate) {
        LocalDate now = LocalDate.now();
        LocalDate date = startDate;
        List<Result> list = new ArrayList<>();
        for (; ; ) {
            if (date.isBefore(now)) {
                Result result = getResultForDate(restTemplate, country, date);
                if (result != null) {
                    list.add(result);
                }
            } else {
                break;
            }
            date = date.plusDays(1);
        }
        return list;
    }

    /**
     * https://api.covid19tracking.narrativa.com/api/country/poland?date_from=2020-11-01&date_to=2020-11-01
     */
    Result getResultForDate(RestTemplate restTemplate, Country country, LocalDate localDate) {
        try {
            String dateFrom = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String dateTo = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.covid19tracking.narrativa.com/api/country/" + country.getId() + "?date_from=" + dateFrom + "&date_to=" + dateTo);
            String result = restTemplate.getForObject(builder.toUriString(), String.class);

            if (result == null) {
                return null;
            } else {
                return ResultBuilder.of(result, country.getName(), dateFrom);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Bean
    public Consumer<Flux<Result>> printResults() {
        return value -> value.subscribe(System.out::println);
    }

    @Bean
    public Function<KStream<String, Result>, KStream<String, Integer>> sumResultsByCountry() {
        return input -> input
                .map((key, value) -> new KeyValue<>(value.getCountryName(), value.getNewConfirmed()))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Integer()))
                .reduce(Integer::sum)
                .toStream();
    }

    @Bean
    public Consumer<KStream<String, Integer>> printSumResultsByCountry() {
        return input ->
                input
                        //.toStream()
                        .foreach((key, value) -> {
                            System.out.println("Country: " + key + " Sum: " + value);
                        });
    }

}
