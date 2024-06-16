import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendBookingConfirmation(String recipientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Booking Confirmation");
        message.setText("Your booking has been confirmed. Thank you!");

        emailSender.send(message);
    }

    // You can add other email notification methods here

}
