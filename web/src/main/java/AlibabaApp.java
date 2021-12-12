import com.alibaba.service.Email.EmailSender.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.mail.MessagingException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.alibaba.*"})
@EntityScan(basePackages = {"com.alibaba.*"})
@EnableJpaRepositories(basePackages = {"com.alibaba.*"})
public class AlibabaApp {

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(AlibabaApp.class);
    }

    //(2) MimeMethod to send email with attachment and html text
    @EventListener(ApplicationReadyEvent.class)
    public void sendMimeMail() throws MessagingException {
        emailService.sendMimeMail("akinolusheyi@gmail.com", "MIME MESSAGE SENDING", "Today, 7th October, 2021," +
                        "I was able to write a mail with attachment...YouTube and Resilience and God's help was very key to this.",
                "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\Training Pictures\\Screenshots\\Screenshot_20210223-114440_Gallery.jpg");
    }
}
