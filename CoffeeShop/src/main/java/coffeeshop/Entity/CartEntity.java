package coffeeshop.Entity;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS )
public class CartEntity {

    private List<CartItem> cartItems = new ArrayList<>();

    public void addItem(ProductEntity product)
    {
        boolean isFound = Optional.of(cartItems)
                .orElse( new ArrayList<>())
                .stream()
                .anyMatch(detail -> Objects.equals(detail.getProduct().getId(), product.getId()));

        if(isFound) {
            cartItems.forEach(details -> {
                if (Objects.equals(details.getProduct().getId(), product.getId())) {
                    details.setQuantity(details.getQuantity() + 1);
                }
            });
        } else{
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(1);
            cartItems.add(item);
        }
    }

    public List<CartItem> getAll(){
        return cartItems;
    }

    public int count(){
        return cartItems
                .stream()
                .map(CartItem::getQuantity)
                .reduce(0,Integer::sum);
    }

    public double total(){
        return cartItems
                .stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }

    public void emptyCart() {
        cartItems.clear();
    }

    public void removeItem(ProductEntity productEntity) {
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem detail = iterator.next();
            if (Objects.equals(detail.getProduct().getId(), productEntity.getId())) {
                if (detail.getQuantity() > 1) {
                    detail.setQuantity(detail.getQuantity() - 1);
                } else {
                    iterator.remove();
                }
            }
        }
    }

}
