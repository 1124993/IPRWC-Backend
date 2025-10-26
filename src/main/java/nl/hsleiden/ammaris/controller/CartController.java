package nl.hsleiden.ammaris.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.AddCartItemRequest;
import nl.hsleiden.ammaris.dto.CartItemDto;
import nl.hsleiden.ammaris.dto.UpdateCartItemRequest;
import nl.hsleiden.ammaris.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> list() {
        return ResponseEntity.ok(cartService.listMyCart());
    }

    @PostMapping("/items")
    public ResponseEntity<Void> add(@Valid @RequestBody AddCartItemRequest body) {
        cartService.add(body);
        return ResponseEntity.created(URI.create("/api/cart/items")).build();
    }

    @PatchMapping("/items/{productId}")
    public ResponseEntity<Void> update(@PathVariable Long productId,
                                       @Valid @RequestBody UpdateCartItemRequest body) {
        cartService.updateQuantity(productId, body);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> remove(@PathVariable Long productId) {
        cartService.remove(productId);
        return ResponseEntity.noContent().build();
    }
}
