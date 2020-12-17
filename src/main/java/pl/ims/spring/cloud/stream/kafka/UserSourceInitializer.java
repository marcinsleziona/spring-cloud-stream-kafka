package pl.ims.spring.cloud.stream.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.ims.spring.cloud.stream.kafka.infrastructure.UserRepository;

import java.util.stream.Stream;

/*
 * Created on 2020-12-17 07:16
 */
@Component
public class UserSourceInitializer {

    private String bindingName;
    private StreamBridge streamBridge;
    private UserRepository repository;

    @Autowired
    public UserSourceInitializer(@Value("${spring.cloud.stream.bindings.pullUsers-out-0.label}") String bindingName,
                                 StreamBridge streamBridge,
                                 UserRepository repository) {
        this.bindingName = bindingName;
        this.streamBridge = streamBridge;
        this.repository = repository;
    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent applicationEvent) {
        final SpringApplication springApplication = applicationEvent.getSpringApplication();
        if (WebApplicationType.NONE.equals(springApplication.getWebApplicationType())) {
            return;
        }
        Stream.of(repository.findAll()).forEach(user -> streamBridge.send(bindingName, user));
    }

}
