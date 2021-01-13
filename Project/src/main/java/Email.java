
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;
import static java.lang.ProcessBuilder.Redirect.from;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Email {
      
       
    public static void Email(String email,String password,String path) throws IOException, MessagingException{
      
      final String username = email;
    final String pass = password;
    Properties props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, pass);
                }
            });
    try {
         Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(email));
    message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(email));
    message.setSubject("Your data is saved");
    MimeBodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setText("Please check attachment below");
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
  
        
            attachmentBodyPart.attachFile(path);
     
   
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);
    multipart.addBodyPart(attachmentBodyPart);
    message.setContent(multipart);
    System.out.println("The message multi part is ="+multipart);
    System.out.println("Sending");
    Transport.send(message);
     System.out.println("Email Sent");
    } catch (MessagingException e) {
           System.out.println("Can't send Email");
    }



    }
    
}

    

