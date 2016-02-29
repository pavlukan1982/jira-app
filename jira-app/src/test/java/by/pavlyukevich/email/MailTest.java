package by.pavlyukevich.email;

import org.junit.Test;

import javax.mail.*;
import java.util.Properties;

public class MailTest {

    @Test
    public void recieveEmail() {
        Properties properties = new Properties();

        // server setting
        properties.put("mail.imap.host", "imap.gmal.com");
        properties.put("mail.imap.port", 993);

        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port", "993");

        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore("imap");
            store.connect("pavlukan1983", "andreiandrei");

            Folder[] folders = store.getDefaultFolder().list();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
