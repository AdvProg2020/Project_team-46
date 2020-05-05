package model;

public class SellerRequest extends Request {
    public SellerRequest(Account account, String requestId, String details) {
        super(account, requestId, details);
    }

    @Override
    public void acceptRequest() {
        String[] request = requestId.split("-");
        if (request[0].equals("1")) {
            Product product = controller.getProductById(request[2]);
            switch (request[1]) {
                case "1":
                    product.setName(details);
                    break;
                case "2":
                    product.setBrandOrCompany(details);
                    break;
                case "3":
                    product.setDescription(details);
                    break;
            }
        }
    }


}
