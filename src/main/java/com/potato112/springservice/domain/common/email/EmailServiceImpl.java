package com.potato112.springservice.domain.common.email;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendSimpleMessage(MailContent mailContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            String content = mailContent.build(templateEngine);
            helper.setSubject(mailContent.getSubject());
            helper.setText(content, true);
            helper.addInline("sysLogo", new ClassPathResource("images/sys_logo.png"), "image/png");
            helper.setTo(mailContent.getEmail());
            emailSender.send(message);
        } catch (MailException | MessagingException e) {
            log.error("Error when email sending", e);
        }
    }
}
