package info.niteshjha;

import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = "classpath*:message.properties", reader = PropertiesBeanDefinitionReader.class)
public class SpringsecuritywithbootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecuritywithbootstrapApplication.class, args);
    }

}
