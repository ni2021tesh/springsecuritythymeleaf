package info.niteshjha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;


    public void sendMail(String appUrl, String email, String subject, String text) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text + "\n" + appUrl);
        simpleMailMessage.setFrom(environment.getProperty("email.from"));
        javaMailSender.send(simpleMailMessage);
    }
}
