package ru.itis.technicalstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.technicalstore.entity.Cart;
import ru.itis.technicalstore.entity.Product;
import ru.itis.technicalstore.exception.exceptions.CartItemNotFoundException;
import ru.itis.technicalstore.exception.exceptions.ProductNotFoundException;
import ru.itis.technicalstore.repository.CartRepository;
import ru.itis.technicalstore.repository.ProductRepository;
import ru.itis.technicalstore.service.CartService;
import ru.itis.technicalstore.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public void addCartItem(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Cart cartItem = cartRepository.findByUserAndProduct(userService.getUser(), product);

        if (cartItem != null) {
            cartItem.setAmount(cartItem.getAmount() + 1);
            cartRepository.save(cartItem);
        } else {
            cartItem = new Cart();
            cartItem.setProduct(product);
            cartItem.setUser(userService.getUser());
            cartItem.setAmount(1);
            cartRepository.save(cartItem);
        }
    }

    public void removeItemFromCart(long id) {
        Cart cartItem = cartRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(id));
        cartRepository.delete(cartItem);
    }

    public void removeAllItems() {
        cartRepository.deleteAllByUser(userService.getUser());
    }

    public Cart increaseAmount(long id) {
        Cart cartItem = cartRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(id));
        cartItem.setAmount(cartItem.getAmount() + 1);
        cartRepository.save(cartItem);
        return cartItem;
    }

    public Cart decreaseAmount(long id) {
        Cart cartItem = cartRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(id));
        if (cartItem.getAmount() > 1) {
            cartItem.setAmount(cartItem.getAmount() - 1);
            cartRepository.save(cartItem);
        } else {
            cartItem.setAmount(cartItem.getAmount() - 1);
            cartRepository.delete(cartItem);
        }
        return cartItem;
    }

    public String cartAmount() {
        int amount = getAmountOfCartItems();
        if (amount >= 2) return "(" + amount + " товара)";
        else if (amount == 1) return "(" + amount + " товар)";
        else return "";
    }

    public int getTotalPrice(List<Cart> cartItems) {
        int total = 0;
        for (Cart cartItem : cartItems) {
            total += getPrice(cartItem);
        }
        return total;
    }

    public int getPrice(Cart cartItem) {
        return cartItem.getProduct().getPrice() * cartItem.getAmount();
    }

    public int getAmountOfCartItems() {
        List<Cart> list = cartRepository.findAllByUserOrderById(userService.getUser());
        int totalItems = 0;
        for (Cart cartItem : list) {
            totalItems = totalItems + cartItem.getAmount();
        }
        return totalItems;
    }

    public List<Cart> getItemsByUser() {
        return cartRepository.findAllByUserOrderById(userService.getUser());
    }
}
