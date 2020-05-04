package model;

public class ProductRequest extends Request {
    public ProductRequest(Account account, String requestId, String details) {
        super(account, requestId, details);
    }

    @Override
    public void acceptRequest() {

    }

    @Override
    public void declineRequest() {

    }
}
