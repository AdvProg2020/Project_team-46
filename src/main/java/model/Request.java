package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Request {
    public static List<Request> requests = new ArrayList<>();
    private Account account;
    private String requestId;
    private String details;
    private boolean isAccepted = false;

    public Request(Account account, String requestId, String details) {
        requests.add(this);
        this.account = account;
        this.requestId = requestId;
        this.details = details;
    }

    public static Request getRequestById(String requestId) {
        for (Request request : requests) {
            if (request.requestId.equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    abstract public void acceptRequest();

    abstract public void declineRequest();

}
