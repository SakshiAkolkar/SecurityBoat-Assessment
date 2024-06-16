public interface NotificationService {
    void sendBookingConfirmation(String recipientEmail);
    void sendSeatAvailabilityAlert(String recipientPhoneNumber, String message);
}
