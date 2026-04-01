package uz.pdp.springboot_mailing_freemarker.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendMessage(String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setSubject("Hello " + username + " !!!");
            mimeMessage.setText("G58 Spring boot module 8.3");
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, username + "@gmail.com");
            mimeMessage.setFrom("from@gamil.com");

            javaMailSender.send(mimeMessage);
            System.out.println("Email send successfuly: " + username + "@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendHtmlContent(String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setSubject("Hello " + username + " !!!");
            mimeMessage.setContent("<h1>Assalomu alaykum <span style=\"color:red\">Ali</span></h1>", "text/html");
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, username + "@gmail.com");
            mimeMessage.setFrom("from@gamil.com");

            javaMailSender.send(mimeMessage);
            System.out.println("Email send successfuly: " + username + "@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendHtmlPageV1(String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setSubject("Hello " + username + " !!!");

            Path htmpPath = Path.of("src/main/resources/welcome.html");
            String htmlPageStringFormated = Files.readString(htmpPath).formatted(username);


            mimeMessage.setContent(htmlPageStringFormated, "text/html");
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, username + "@gmail.com");
            mimeMessage.setFrom("from@gamil.com");

            javaMailSender.send(mimeMessage);
            System.out.println("Email send successfuly: " + username + "@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendAttachment(String username) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject("Hello " + username + " !!!");

            Path htmpPath = Path.of("src/main/resources/welcome.html");
            String htmlPageStringFormated = Files.readString(htmpPath).formatted(username);
            helper.setText(htmlPageStringFormated, true);
            helper.setTo(username + "@gmail.com");
            helper.setFrom("from@gamil.com");

            Path imagePath = Path.of("src/main/resources/download.png");
            Path pdfPath = Path.of("src/main/resources/resume.pdf");

            FileSystemResource imageSystemResource = new FileSystemResource(imagePath);
            FileSystemResource pdfSystemResource = new FileSystemResource(pdfPath);

            helper.addAttachment("download.png", imageSystemResource);
            helper.addAttachment("resume.pdf", pdfSystemResource);

            javaMailSender.send(mimeMessage);
            System.out.println("Email send successfuly: " + username + "@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendHtmlPageWithImageV1(String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setSubject("Hello " + username + " !!!");

            Path htmpPath = Path.of("src/main/resources/image.html");
            Path imageUrl = Path.of("src/main/resources/download.png");

            Base64.Encoder encoder = Base64.getEncoder();
            String imageBase64 = encoder.encodeToString(Files.readAllBytes(imageUrl));


            String htmlPageStringFormated = Files.readString(htmpPath).formatted(imageBase64);


            mimeMessage.setContent(htmlPageStringFormated, "text/html");
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, username + "@gmail.com");
            mimeMessage.setFrom("from@gamil.com");

            javaMailSender.send(mimeMessage);
            System.out.println("Email send successfuly: " + username + "@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendHtmlPageWithImageV2(String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("Hello " + username + " !!!");

            helper.setTo(username + "@gmail.com");
            helper.setFrom("from@gamil.com");

            Path htmpPath = Path.of("src/main/resources/imageV2.html");
            Path imageUrl = Path.of("src/main/resources/download.png");

            String htmlContent = Files.readString(htmpPath);

            helper.setText(htmlContent, true);
            helper.addInline("image_id", new FileSystemResource(imageUrl));

            javaMailSender.send(mimeMessage);
            System.out.println("Email send successfuly: " + username + "@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
