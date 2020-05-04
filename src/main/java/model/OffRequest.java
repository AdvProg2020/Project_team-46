package model;

public class OffRequest extends Request {
    public OffRequest(Account account, String requestId, String details) {
        super(account, requestId, details);
    }

    @Override
    public void acceptRequest() {

    }

    @Override
    public void declineRequest() {

    }
}
