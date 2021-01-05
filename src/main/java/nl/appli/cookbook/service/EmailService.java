package nl.appli.cookbook.service;

import nl.appli.cookbook.domain.Chef;
import nl.appli.cookbook.domain.JoinCookbookRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.logging.Logger;

@Service
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    private final JavaMailSender emailSender;
    private final ChefService chefService;
    private final CookbookService cookbookService;

    public EmailService(JavaMailSender javaMailSender, ChefService chefService, CookbookService cookbookService) {
        this.emailSender = javaMailSender;
        this.chefService = chefService;
        this.cookbookService = cookbookService;
    }

    public void sendNewRequestMail(JoinCookbookRequest request) {
        Long chefId = cookbookService.getCookbook(request.getCookbookId()).getCreatorId();
        Chef chef = chefService.getChef(chefId);
        String subject = "Request to join cookbook";
        String mainText = "Someone wants to join your cookbook '" + request.getCookbookName() + "'. " +
                "Open the Cookbook app to accept or reject the request.";
        sendSimpleMessage(chef.getEmail(), subject, mainText);
    }

    public void sendRequestAcceptedMail(JoinCookbookRequest request) {
        Chef chef = chefService.getChef(request.getChefId());
        String subject = "Request to join cookbook accepted";
        String mainText = "Your request to join cookbook '" + request.getCookbookName() + "' has been accepted. " +
                "You can now add and edit recipes in this cookbook. Open the app for more details.";
        sendSimpleMessage(chef.getEmail(), subject, mainText);
    }

    public void sendRequestRejectedMail(JoinCookbookRequest request) {
        Chef chef = chefService.getChef(request.getChefId());
        String subject = "Request to join cookbook rejected";
        String mainText = "Unfortunately, your request to join cookbook '" + request.getCookbookName() + "' has been rejected. " +
                "The owner of the cookbook doesn't allow you to add or edit recipes in this cookbook. You can still view all recipes.";
        sendSimpleMessage(chef.getEmail(), subject, mainText);
    }

    public void sendSimpleMessage(String to, String subject, String mainText) {
        LOGGER.info("Sending e-mail to " + to);
        Session session = Session.getDefaultInstance(System.getProperties(),null);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject);
            message.setContent(getContent(subject, mainText));
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private MimeMultipart getContent(String subject, String mainText) {
        MimeMultipart content = new MimeMultipart("related");
        BodyPart bodyPart = new MimeBodyPart();

        try {
            String htmlText = getMailText(subject, mainText);
            bodyPart.setContent(htmlText, "text/html");
            content.addBodyPart(bodyPart);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return content;
    }

    private String getMailText(String subject, String mainText) {
        return "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>" +
                "<html xmlns='http://www.w3.org/1999/xhtml'>" +
                "<head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'/>" +
                "</head>" +
                "<body style='margin: 0; padding: 0;'>" +
                "    <table border='0' cellpadding='0' cellspacing='0' width='100%'> " +
                "        <tr>" +
                "            <td style='padding: 10px 0 30px 0;'>" +
                "                <table align='center' border='0' cellpadding='0' cellspacing='0' width='600' style='border: 1px solid #cccccc; border-collapse: collapse;'>" +
                "                    <tr>" +
                "                        <td bgcolor='#ffffff' style='padding: 40px 30px 40px 30px;'>" +
                "                            <table border='0' cellpadding='0' cellspacing='0' width='100%'>" +
                "                                <tr>" +
                "                                    <td style='color: #153643; font-family: Arial, sans-serif; font-size: 20px;'>" +
                                                        subject +
                "                                    </td>" +
                "                                </tr>" +
                "                                <tr>" +
                "                                    <td style='padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 14px; line-height: 20px;'>" +
                                                        mainText +
                "                                    </td>" +
                "                                </tr> " +
                "                            </table>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr>" +
                "                        <td bgcolor='#00a3a2' style='padding: 30px 30px 30px 30px;'>" +
                "                            <table border='0' cellpadding='0' cellspacing='0' width='100%'>" +
                "                                <tr>" +
                "                                    <td style='color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;' width='75%'>" +
                "                                        &reg; Cookbook<br/>" +
                "                                        Do you not want to receive further e-mails? Unsubscribe via the app." +
                "                                </tr>" +
                "                            </table>" +
                "                        </td>" +
                "                    </tr>" +
                "                </table>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";
    }
}
