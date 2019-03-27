package info.niteshjha;

import info.niteshjha.model.SecurityQuestionDefinition;
import info.niteshjha.model.User;
import info.niteshjha.service.SecurityQuestionDefinitionService;
import info.niteshjha.service.UserCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"info/niteshjha/repository"})
@EntityScan(basePackages = {"info/niteshjha/model"})
public class SpringsecuritywithbootstrapApplication implements CommandLineRunner {

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

    @Autowired
    private SecurityQuestionDefinitionService securityQuestionDefinitionService;

    @Autowired
    private UserCreateService userService;

    @Override
    public void run(String... args) throws Exception {
        this.userService.createSignUpUser(new User(5001L, "Nitesh", "niteshjha2021@gmail.com", "nitesh123", null, LocalDateTime.now(), LocalDateTime.now(), true));
        this.securityQuestionDefinitionService.saveAllSecurityQuestionDefinition(getListOfSecurityQuestionDefinition());
    }

    private List<SecurityQuestionDefinition> getListOfSecurityQuestionDefinition() {
        return Arrays.asList(
                new SecurityQuestionDefinition(10001L, "What is the last name of the teacher who gave you your first failing grade?"),
                new SecurityQuestionDefinition(10002L, "What is the first name of the person you first kissed?"),
                new SecurityQuestionDefinition(10003L, "What is the name of the place your wedding reception was held?"),
                new SecurityQuestionDefinition(10004L, "When you were young, what did you want to be when you grew up?"),
                new SecurityQuestionDefinition(10005L, "Where were you New Year' 's 2000?"),
                new SecurityQuestionDefinition(10006L, "Who was your childhood hero?"));
    }
}


