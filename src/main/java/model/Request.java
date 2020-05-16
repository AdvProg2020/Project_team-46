package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;

public abstract class Request {
    public static List<Request> requests = new ArrayList<>();
    protected Account account;
    protected String requestId;
    protected String details;
    protected static Controller controller;
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

    public String getRequestId() {
        return requestId;
    }

    public Account getAccount() {
        return account;
    }

    public String getDetails() {
        return details;
    }

    public static void setController(Controller controller) {
        Request.controller = controller;
    }

    abstract public void acceptRequest();

    public void declineRequest() {
        Request.requests.remove(this);
    }

}
