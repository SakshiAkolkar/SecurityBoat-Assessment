import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private NotificationService notificationService;

    // Booking confirmation logic
    public void confirmBooking(String userEmail, String userPhoneNumber) {
        // Logic to save booking in database

        // Send email notification
        notificationService.sendBookingConfirmation(userEmail);

        // Send SMS notification (optional)
        notificationService.sendSeatAvailabilityAlert(userPhoneNumber, "Seats booked successfully.");
    }

    // Other booking management methods
}
