package celebre.entities;

public class PaymentCheckoutUrlDto {
    private String url;

    public PaymentCheckoutUrlDto (String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
