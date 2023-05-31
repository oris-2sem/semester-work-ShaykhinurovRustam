package ru.itis.technicalstore.service;

import ru.itis.technicalstore.entity.Cart;

import java.util.List;

public interface CartService {
    void addCartItem(long productId);
    void removeItemFromCart(long id);
    void removeAllItems();
    String cartAmount();
    Cart increaseAmount(long id);
    Cart decreaseAmount(long id);
    int getTotalPrice(List<Cart> cartItems);
    int getPrice(Cart cartItem);
    int getAmountOfCartItems();
    List<Cart> getItemsByUser();
}
