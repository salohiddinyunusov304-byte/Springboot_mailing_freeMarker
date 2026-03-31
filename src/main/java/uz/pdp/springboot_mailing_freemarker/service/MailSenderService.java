package uz.pdp.springboot_mailing_freemarker.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendMessage(String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setSubject("Hello " + username + " !!!");
            mimeMessage.setText("This is test email send from Spring boot");
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, username + "@gmail.com");
            mimeMessage.setFrom("from@gamil.com");

            javaMailSender.send(mimeMessage);
            System.out.println("Email send successfuly: " + username + "@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
