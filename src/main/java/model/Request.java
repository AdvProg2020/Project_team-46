package model;

import java.util.ArrayList;
import java.util.List;

public class Request {
    public static List<Request> requests = new ArrayList<>();
    private Account account;
    private String requestId;
    private String details;

    public static Request getRequestById(String requestId) {
        for (Request request : requests) {
            if (request.requestId.equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    public void acceptRequest() {

    }

    public void declineRequest() {

    }

}
