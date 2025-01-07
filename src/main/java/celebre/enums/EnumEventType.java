package celebre.enums;

public enum EnumEventType {
    CHARGE_SUCCEEDED("charge.succeeded"),
    PAYMENT_INTENT_SUCCEEDED("payment_intent.succeeded"),
    CHECKOUT_SESSION_COMPLETED("checkout.session.completed"),
    PAYMENT_INTENT_CREATED("payment_intent.created");

    private final String value;

    EnumEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
