package com.app.perfumeshop.service;

import com.app.perfumeshop.model.entity.CartItem;
import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.repository.CartItemRepository;
import com.app.perfumeshop.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public ShoppingCart addProductToCart(Product product, User user, int quantity) {

        ShoppingCart shoppingCart = findByUserId(user.getId());

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

    public ShoppingCart updateItemInCart(Product product, int quantity, User user) {

        ShoppingCart shoppingCart = findByUserId(user.getId());

        List<CartItem> cartItems = shoppingCart.getCartItems();

        CartItem item = findCartItem(cartItems, product.getId());

        item.setQuantity(quantity);
        BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(item.getQuantity()));
        item.setTotalPrice(totalPrice);
        cartItemRepository.save(item);

        int totalItems = totalItems(cartItems);
        BigDecimal totalPriceBig = totalPrice(cartItems);

        shoppingCart.setTotalItems(totalItems);
        shoppingCart.setTotalPrice(totalPriceBig);


        return shoppingCartRepository.save(shoppingCart);
    }
    public ShoppingCart removeItemFromCart(Product product, User user) {

        ShoppingCart shoppingCart = findByUserId(user.getId());

        List<CartItem> cartItems = shoppingCart.getCartItems();

        CartItem item = findCartItem(cartItems, product.getId());

        cartItems.remove(item);

        cartItemRepository.delete(item);

        BigDecimal totalPriceBig = totalPrice(cartItems);
        int totalItems = totalItems(cartItems);

        shoppingCart.setCartItems(cartItems);
        shoppingCart.setTotalPrice(totalPriceBig);
        shoppingCart.setTotalItems(totalItems);

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
        return shoppingCartRepository.findShoppingCartByCustomer_Id(id);
    }


    @Transactional
    public void deleteCartById(Long id) {
        List<CartItem> cartItems = shoppingCartRepository.findById(id).get().getCartItems();
        cartItemRepository.deleteAll(cartItems);
        shoppingCartRepository.deleteById(id);
    }

    public void clearCurrentCartById(Long id) {
        ShoppingCart cart = shoppingCartRepository.findById(id).get();
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.valueOf(0.00));
        cart.setTotalItems(0);
        shoppingCartRepository.save(cart);
    }
}
