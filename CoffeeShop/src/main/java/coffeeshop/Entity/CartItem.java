package coffeeshop.Entity;


public class CartItem {

    private ProductEntity product;

    private int quantity;

    public CartItem() {
    }


    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
