package model;

public class SellerRequest extends Request {
    public SellerRequest(Account account, String requestId, String[] inputs, String details) {
        super(account, requestId, inputs, details);
    }

    @Override
    public void acceptRequest() {
        switch (details) {
            case "edit name of product":
                controller.setNameOfProduct(inputs[0], inputs[1]);
                break;
            case "edit company of product":
                controller.setCompanyOfProduct(inputs[0], inputs[1]);
                break;
            case "edit description of product":
                controller.setDescriptionOfProduct(inputs[0], inputs[1]);
                break;
            case "edit availability of product":
                if (inputs[1].equals("1")) {
                    controller.setAvailabilityOfProduct(inputs[0], true);
                } else if (inputs[1].equals("2")) {
                    controller.setAvailabilityOfProduct(inputs[0], false);
                }
                break;
            case "edit price of product":
                controller.setValueOfProduct(inputs[0], Long.parseLong(inputs[1]));
                break;
            case "edit amount of product":
                controller.setAmountOfProduct(inputs[0], Integer.parseInt(inputs[1]));
                break;
            case "add product":
                controller.addProduct(inputs, account);
        }
    }


}