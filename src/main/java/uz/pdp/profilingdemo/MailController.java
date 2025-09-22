package uz.pdp.profilingdemo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/simple-mail")
    public String sendSimpleMail(@RequestParam String username) {
        mailService.sendSimpleMail(username);
        return "Mail sent successfully";
    }

    @PostMapping("/html-content")
    public String sendHtmlContent(@RequestParam String username) {
        mailService.sendHtmlContent(username);
        return "Mail sent successfully";
    }

    @PostMapping("/html-page")
    public String sendHtmlPage(@RequestParam String username) {
        mailService.sendHtmlPage(username);
        return "Mail sent successfully";
    }

    @PostMapping("/send-attachment")
    public String sendAttachment(@RequestParam String username) {
        mailService.sendAttachment(username);
        return "Mail sent successfully";
    }

    @PostMapping("/send-image")
    public String sendImage(@RequestParam String username) {
        mailService.sendImageOnHtml(username);
        return "Mail sent successfully";
    }

    @PostMapping("/send-ftlh")
    public String sendFtlh(@RequestParam String username) {
        mailService.sendFtlh(username);
        return "Mail sent successfully";
    }
}
