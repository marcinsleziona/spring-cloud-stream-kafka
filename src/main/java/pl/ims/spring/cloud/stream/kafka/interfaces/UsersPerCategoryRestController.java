package pl.ims.spring.cloud.stream.kafka.interfaces;

import lombok.AllArgsConstructor;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/*
 * Created on 2020-12-18 18:29
 */
@RestController
@AllArgsConstructor
public class UsersPerCategoryRestController {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @GetMapping(path = "/count")
    @CrossOrigin(origins = "*")
    public List<UsersPerCategory> count() {
        final ReadOnlyKeyValueStore<String, Long> store = interactiveQueryService.getQueryableStore("count-store", QueryableStoreTypes.<String, Long>keyValueStore());
        List<UsersPerCategory> list = new ArrayList<>();
        store.all().forEachRemaining(stringLongKeyValue -> list.add(UsersPerCategory.builder().category(stringLongKeyValue.key).count(stringLongKeyValue.value).build()));
        return list;
    }

    @GetMapping(path = "/countFlux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin(origins = "*")
    public Flux<List<UsersPerCategory>> countFlux() {
        final ReadOnlyKeyValueStore<String, Long> store = interactiveQueryService.getQueryableStore("count-store", QueryableStoreTypes.<String, Long>keyValueStore());
        return Flux.fromStream(Stream.generate(() -> {
            List<UsersPerCategory> list = new ArrayList<>();
            store.all().forEachRemaining(stringLongKeyValue -> list.add(UsersPerCategory.builder().category(stringLongKeyValue.key).count(stringLongKeyValue.value).build()));
            return list;
        })).delayElements(Duration.ofSeconds(1));
    }
}
