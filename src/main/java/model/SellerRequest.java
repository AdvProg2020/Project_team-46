package model;

public class SellerRequest extends Request {
    public SellerRequest(Account account, String requestId, String[] inputs, String details) {
        super(account, requestId, inputs, details);
    }

    @Override
    public void acceptRequest() {
        Product product = controller.getProductById(inputs[0]);
        switch (details) {
            case "edit name of product":
                product.setName(inputs[1]);
                product.setProductStatus(ProductStatus.CONFIRMED);
                break;
            case "edit company of product":
                product.setBrandOrCompany(inputs[1]);
                product.setProductStatus(ProductStatus.CONFIRMED);
                break;
            case "edit description of product":
                product.setDescription(inputs[1]);
                product.setProductStatus(ProductStatus.CONFIRMED);
                break;
            case "edit availability of product":
                if (inputs[1].equals("1")) {
                    product.setAvailable(true);
                    product.setProductStatus(ProductStatus.CONFIRMED);
                } else if (inputs[1].equals("2")) {
                    product.setProductStatus(ProductStatus.CONFIRMED);
                    product.setAvailable(false);
                }
                break;
            case "add product":
                controller.addProduct(inputs, account);
        }
    }


}
