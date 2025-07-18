package lk.pasanhansaka.bank.core.email;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lk.pasanhansaka.bank.core.mailer.Mailable;

import javax.naming.Context;
import java.util.Base64;

public class CustomerAccountActivationEmail extends Mailable {

    private String to;
    private String verificationCode;
    private String baseURL;

    public CustomerAccountActivationEmail(String to, String verificationCode, String baseURL) {
        this.to = to;
        this.verificationCode = verificationCode;
        this.baseURL = baseURL;
    }

    @Override
    public void build(Message message) throws Exception {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Customer Account Activation Email");

        String id = Base64.getEncoder().encodeToString(to.getBytes());

        String activationLink = baseURL + "/activate?id=" + id + "&code=" + verificationCode;

        String emailContent = "<h2>Welcome to Hela Bank!</h2>" +
                "<p>Please activate your account by clicking the link below:</p>" +
                "<a href='" + activationLink + "'>Activate Account</a>";

        message.setContent(emailContent, "text/html; charset=utf-8");

    }
}
