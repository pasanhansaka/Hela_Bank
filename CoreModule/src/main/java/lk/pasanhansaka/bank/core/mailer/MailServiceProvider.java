package lk.pasanhansaka.bank.core.mailer;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MailServiceProvider {
    private Properties properties = new Properties();
    private Authenticator authenticator;
    private static MailServiceProvider instance;
    private ThreadPoolExecutor executor;
    private BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();

    private MailServiceProvider() {
        properties.setProperty("mail.smtp.host", Env.getProperty("mailtrap.host"));
        properties.setProperty("mail.smtp.port", Env.getProperty("mailtrap.port"));
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", Env.getProperty("mailtrap.host"));
    }

    public static MailServiceProvider getInstance() {
        if (instance == null) {
            instance = new MailServiceProvider();
        }
        return instance;
    }

    public void start() {
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Env.getProperty("mailtrap.username"), Env.getProperty("mailtrap.password"));
            }
        };

        executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, blockingQueue,
                new ThreadPoolExecutor.AbortPolicy());
        executor.prestartAllCoreThreads();

        System.out.println("MailServiceProvider: Running...");
    }

    public void sendMail(Mailable mailable) {
        blockingQueue.offer(mailable);
    }

    public Properties getProperties() {
        return properties;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
