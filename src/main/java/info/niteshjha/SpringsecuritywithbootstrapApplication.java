package info.niteshjha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"info/niteshjha/repository"})
@EntityScan(basePackages = {"info/niteshjha/model"})
public class SpringsecuritywithbootstrapApplication {

    @Bean
    public Formatter<LocalDateTime> localDateFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) throws ParseException {
                return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE);
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                return DateTimeFormatter.ISO_DATE.format(object);
            }
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringsecuritywithbootstrapApplication.class, args);
    }

}


