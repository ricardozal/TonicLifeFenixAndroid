package mx.com.bigtechsolutions.toniclifefenix.api.requests;

public class ChangeQuantityRequest {

    private int quantity;
    private int productId;

    public ChangeQuantityRequest(int quantity, int productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
