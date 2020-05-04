package model;

public class SellerRequest extends Request {
    public SellerRequest(Account account, String requestId, String details) {
        super(account, requestId, details);
    }

    @Override
    public void acceptRequest() {

    }

    @Override
    public void declineRequest() {

    }
}
