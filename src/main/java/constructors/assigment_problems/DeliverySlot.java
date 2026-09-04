package constructors.assigment_problems;

/**
 * Problem 2: ASAP or Scheduled — Delivery Slot Booking
 * Constructor chaining via this(...) with default ASAP slot and peak-hour detection.
 */
public class DeliverySlot {
    private static final String DEFAULT_SLOT = "ASAP";

    private final String orderId;
    private final String timeSlot;

    /**
     * Primary constructor setting both fields.
     */
    public DeliverySlot(String orderId, String timeSlot) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty.");
        }
        this.orderId = orderId.trim();
        this.timeSlot = (timeSlot == null || timeSlot.trim().isEmpty()) ? DEFAULT_SLOT : timeSlot.trim();
    }

    /**
     * Secondary constructor chaining to primary with default "ASAP" slot.
     */
    public DeliverySlot(String orderId) {
        this(orderId, DEFAULT_SLOT);
    }

    /**
     * Flags bookings in peak-hour slots:
     * "12:00-13:00", "13:00-14:00", "19:00-20:00", "20:00-21:00".
     */
    public boolean isPeakHour() {
        return "12:00-13:00".equals(timeSlot) ||
               "13:00-14:00".equals(timeSlot) ||
               "19:00-20:00".equals(timeSlot) ||
               "20:00-21:00".equals(timeSlot);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 2: Delivery Slot Booking Demo ===");
        DeliverySlot slot1 = new DeliverySlot("ORD101", "13:00-14:00");
        System.out.println("slot1 isPeakHour: " + slot1.isPeakHour()); // true

        DeliverySlot slot2 = new DeliverySlot("ORD102");
        System.out.println("slot2 timeSlot: " + slot2.getTimeSlot()); // ASAP
        System.out.println("slot2 isPeakHour: " + slot2.isPeakHour()); // false
    }
}
