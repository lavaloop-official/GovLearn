package com.unimuenster.govlearnapi.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${frontend.url}")
    private String frontendUrl;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("govlearn@uhlit.de");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendResetEmail(String to, String token) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("govlearn@uhlit.de", "Govlearn - Weiterbildung einfach gemacht"));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Passwort zurücksetzen");

        String safetoken = Base64.getEncoder().encodeToString(token.getBytes());

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"de\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Passwort zurücksetzen</title>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;\">\n" +
                "\n" +
                "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"background-color: #ffffff; margin-top: 20px;\">\n" +
                "        <tr>\n" +
                "            <td style=\"padding: 20px;\">\n" +
                "                <h2 style=\"color: #333333;\">Passwort zurücksetzen</h2>\n" +
                "                <p style=\"color: #666666;\">Hallo,</p>\n" +
                "                <p style=\"color: #666666;\">Sie haben eine Anfrage zum Zurücksetzen Ihres Passworts auf Govlearn gestellt. Klicken Sie auf den unten stehenden Button, um Ihr Passwort zurückzusetzen:</p>\n" +
                "                <p style=\"text-align: center; padding: 20px;\">\n" +
                "                    <a href=\""+ frontendUrl + "/reset-password/" + safetoken + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #4caf50; color: #ffffff; text-decoration: none; border-radius: 5px;\">Passwort zurücksetzen</a>\n" +
                "                </p>\n" +
                "                <p style=\"color: #666666;\">Wenn Sie diese Anfrage nicht gestellt haben, können Sie diese E-Mail einfach ignorieren.</p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #333333; padding: 10px; color: #ffffff; text-align: center;\">\n" +
                "                © 2024 Govlearn. Alle Rechte vorbehalten.\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";

        message.setContent(html, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}