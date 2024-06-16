import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSService implements NotificationService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @Override
    public void sendBookingConfirmation(String recipientPhoneNumber) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                new PhoneNumber(recipientPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                "Your booking has been confirmed. Thank you!")
                .create();

        System.out.println("SMS sent successfully. SID: " + message.getSid());
    }

    @Override
    public void sendSeatAvailabilityAlert(String recipientPhoneNumber, String message) {
        Twilio.init(accountSid, authToken);

        Message sms = Message.creator(
                new PhoneNumber(recipientPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                message)
                .create();

        System.out.println("SMS sent successfully. SID: " + sms.getSid());
    }
}
