package ru.itis.technicalstore.controller;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.technicalstore.entity.Cart;
import ru.itis.technicalstore.service.CartService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String getCartPage(Model model) {
        List<Cart> cartItems = cartService.getItemsByUser();
        Integer totalPrice = cartService.getTotalPrice(cartItems);
        BigDecimal exchangedPrice;

        if (totalPrice != 0) {
            exchangedPrice = exchangePrice(totalPrice);
        } else {
            exchangedPrice = new BigDecimal(0);
        }
        //exchangedPrice = new BigDecimal(1).setScale(0, RoundingMode.UP);

        model.addAttribute("items", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("exchangedPrice", exchangedPrice);

        return "cart";
    }

    @GetMapping("/cart/remove")
    public String removeItemFromCart(@RequestParam long id) {
        cartService.removeItemFromCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove_all")
    public String removeAllItems() {
        cartService.removeAllItems();
        return "redirect:/cart";
    }

    @GetMapping("/increase_amount")
    public ResponseEntity<Map<String, Object>> increaseAmount(@RequestParam("cartItemId") Long id) {
        Map<String, Object> response = new HashMap<>();

        Cart cartItem = cartService.increaseAmount(id);
        List<Cart> cartItems = cartService.getItemsByUser();
        Integer totalPrice = cartService.getTotalPrice(cartItems);
        BigDecimal exchangedPrice;

        if (totalPrice != 0) {
            exchangedPrice = exchangePrice(totalPrice);
        } else {
            exchangedPrice = new BigDecimal(0);
        }
        //exchangedPrice = new BigDecimal(1).setScale(0, RoundingMode.UP);
        response.put("newCartAmount", cartService.cartAmount());
        response.put("newAmount", cartItem.getAmount());
        response.put("newPrice", cartService.getPrice(cartItem));
        response.put("totalPrice", totalPrice);
        response.put("exchangedPrice", exchangedPrice);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/decrease_amount")
    public ResponseEntity<Map<String, Object>> decreaseAmount(@RequestParam("cartItemId") Long id) {
        Map<String, Object> response = new HashMap<>();

        Cart cartItem = cartService.decreaseAmount(id);
        List<Cart> cartItems = cartService.getItemsByUser();
        Integer totalPrice = cartService.getTotalPrice(cartItems);
        BigDecimal exchangedPrice;

        if (totalPrice != 0) {
            exchangedPrice = exchangePrice(totalPrice);
        } else {
            exchangedPrice = new BigDecimal(0);
        }
        //exchangedPrice = new BigDecimal(1).setScale(0, RoundingMode.UP);

        response.put("newCartAmount", cartService.cartAmount());
        response.put("newAmount", cartItem.getAmount());
        response.put("newPrice", cartService.getPrice(cartItem));
        response.put("totalPrice", totalPrice);
        response.put("exchangedPrice", exchangedPrice);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add_item")
    public String addProductToCart(
            @RequestParam long productId,
            RedirectAttributes redirectAttributes
    ) {
        cartService.addCartItem(productId);
        redirectAttributes.addFlashAttribute("message", "message");
        return "redirect:/products/" + productId;
    }

    private BigDecimal exchangePrice(Integer totalPrice) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        JSONObject json;

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/convert?to=USD&from=RUB&amount=" + totalPrice)
                .addHeader("apikey", "oHM0SWmdOsCPsplhAG563gAb7m6iaW6j")
            .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            json = new JSONObject(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return json.getBigDecimal("result").setScale(0, RoundingMode.UP);
    }
}
