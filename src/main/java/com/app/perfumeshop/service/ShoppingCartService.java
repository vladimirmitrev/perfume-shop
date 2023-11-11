package com.app.perfumeshop.service;

import com.app.perfumeshop.model.entity.CartItem;
import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart addProductToCart(Product product, User user, int quantity) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomerId(user.getId());

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart()
                    .setCustomer(user);
        }

        List<CartItem> cartItemList = shoppingCart.getCartItems();

        CartItem cartItem = findCartItem(cartItemList, product.getId());


        BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(quantity));

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(totalPrice);
            cartItem.setShoppingCart(shoppingCart);
            cartItemList.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getTotalPrice().add(totalPrice));
        }

        shoppingCart.setCartItems(cartItemList);
        shoppingCart.setTotalItems(totalItems(cartItemList));
        shoppingCart.setTotalPrice(totalPrice(cartItemList));

        return shoppingCartRepository.save(shoppingCart);
    }



    private CartItem findCartItem(List<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;

        for (CartItem item : cartItems) {
            if (Objects.equals(item.getProduct().getId(), productId)) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(List<CartItem> cartItems) {
        int totalItems = 0;

        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }

        return totalItems;
    }

    private BigDecimal totalPrice(List<CartItem> cartItems) {
        BigDecimal totalPrice = BigDecimal.valueOf(0.0);

        for (CartItem item : cartItems) {
            totalPrice = totalPrice.add(item.getTotalPrice());
        }

        return totalPrice;
    }
    public ShoppingCart findByUserId(Long id) {
        return shoppingCartRepository.findByCustomerId(id);
    }
}
