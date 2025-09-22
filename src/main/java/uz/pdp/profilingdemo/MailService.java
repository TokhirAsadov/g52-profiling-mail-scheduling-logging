package uz.pdp.profilingdemo;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class MailService {
    private final JavaMailSender javaMailSender;
    private final FreeMarkerConfig freeMarkerConfig;

    public MailService(JavaMailSender javaMailSender, FreeMarkerConfig freeMarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.freeMarkerConfig = freeMarkerConfig;
    }

    @Async
    public void sendSimpleMail(String fullName) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom("archvizor@gmail.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, "to@gmail.com");
            mimeMessage.setSubject("Welcome...");
            mimeMessage.setText("Hello, " + fullName + "!");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendHtmlContent(String fullName) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom("archvizor@gmail.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, "to@gmail.com");
            mimeMessage.setSubject("Welcome...");
//            mimeMessage.setText("Hello, " + fullName + "!");
            mimeMessage.setContent("<h1 style=\"color: red;\">Welcome...</h1>", "text/html");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendHtmlPage(String fullName) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom("archvizor@gmail.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, "to@gmail.com");
            mimeMessage.setSubject("Welcome...");
            Path path = Path.of("src/main/resources/active.html");
            String htmlContent = Files.readString(path);
            htmlContent = htmlContent.formatted(fullName);
            mimeMessage.setContent(htmlContent, "text/html");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendAttachment(String fullName) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessage.setFrom("archvizor@gmail.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, "to@gmail.com");
            mimeMessage.setSubject("Welcome...");
            Path path = Path.of("src/main/resources/active.html");

            Path imagePath = Path.of("src/main/resources/CleanCode_back.png");
            Path pdfPath = Path.of("src/main/resources/autocode-intro_ENG.pdf");

            FileSystemResource imageSource = new FileSystemResource(imagePath);
            FileSystemResource pdfSource = new FileSystemResource(pdfPath);

            String htmlContent = Files.readString(path);
            htmlContent = htmlContent.formatted(fullName);

            mimeMessageHelper.addAttachment("Book.png", imageSource);
            mimeMessageHelper.addAttachment("File.pdf", pdfSource);

            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendImageOnHtml(String fullName) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessage.setFrom("archvizor@gmail.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, "to@gmail.com");
            mimeMessage.setSubject("Welcome...");
            Path path = Path.of("src/main/resources/active.html");

            Path imagePath = Path.of("src/main/resources/CleanCode_back.png");

            FileSystemResource imageSource = new FileSystemResource(imagePath);

            String htmlContent = Files.readString(path);
            htmlContent = htmlContent.formatted(fullName);

            mimeMessageHelper.addAttachment("Book.png", imageSource);
            mimeMessageHelper.setText(htmlContent, true);
            mimeMessageHelper.addInline("image_id", imageSource);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendFtlh(String fullName) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessage.setFrom("archvizor@gmail.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, "to@gmail.com");
            mimeMessage.setSubject("FTLH Welcome...");

            Template template = freeMarkerConfig.getConfiguration().getTemplate("simple_page.ftlh");
            String token = Base64.getEncoder().encodeToString((fullName + System.currentTimeMillis()).getBytes());
            Map<String, String> objectModel = Map.of(
                    "username", fullName,
                    "token", token
            );
            String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, objectModel);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
