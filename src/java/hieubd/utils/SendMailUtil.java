/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.utils;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author CND
 */
public class SendMailUtil implements Serializable {

    public static void sendMail(String toEmail, String nameUser, int codeConfirm) throws AddressException, MessagingException {
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", 587);

        Session s = Session.getInstance(p,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyConstants.EMAIL, MyConstants.PASSWORD_EMAIL);
            }
        });

        Message msg = new MimeMessage(s);
        msg.setFrom(new InternetAddress("share"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject("Confirm email Resource Sharing");
        msg.setContent("<h1>Welcome " + nameUser + "</h1>"
                + "<p>Thank you for registering an account on Resource Sharing. "
                + "For the best service and support experience, you need to complete account verification.</p>"
                + "<p>Please enter the Verification Code below to complete the registration process. after " + MyConstants.TIME_OUT_CONFIRM_CODE + " minutes, this code will be expired</p>"
                + "<code>" + codeConfirm + "<code>", "text/html");
        Transport.send(msg);
    }
}
