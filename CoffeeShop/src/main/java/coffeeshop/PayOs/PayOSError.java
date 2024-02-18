package coffeeshop.PayOs;

public class PayOSError extends Exception{
    private String code;

    public PayOSError(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
