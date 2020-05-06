package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;

public abstract class Request {
    public static List<Request> requests = new ArrayList<>();
    protected Account account;
    protected String requestId;
    protected String details;
    protected String[] inputs;
    protected static Controller controller;
    private boolean isAccepted = false;

    public Request(Account account, String requestId, String[] inputs, String details) {
        requests.add(this);
        this.account = account;
        this.requestId = requestId;
        this.inputs = inputs;
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

    public static void setController(Controller controller) {
        Request.controller = controller;
    }

    abstract public void acceptRequest();

    public void declineRequest() {
        Request.requests.remove(this);
    }

}
