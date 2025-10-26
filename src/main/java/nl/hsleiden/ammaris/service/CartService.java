package nl.hsleiden.ammaris.service;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.AddCartItemRequest;
import nl.hsleiden.ammaris.dto.CartItemDto;
import nl.hsleiden.ammaris.dto.UpdateCartItemRequest;
import nl.hsleiden.ammaris.entity.AppUser;
import nl.hsleiden.ammaris.entity.CartItem;
import nl.hsleiden.ammaris.entity.Product;
import nl.hsleiden.ammaris.repository.CartItemRepository;
import nl.hsleiden.ammaris.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cart;
    private final ProductRepository products;
    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    public List<CartItemDto> listMyCart() {
        AppUser me = currentUserService.requireCurrentUser();
        return cart.findByUser_Id(me.getId())
                .stream().map(this::toDto)
                .toList();
    }

    @Transactional
    public void add(AddCartItemRequest req) {
        AppUser me = currentUserService.requireCurrentUser();
        Product p = products.findById(req.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (p.isArchived()) {
            throw new IllegalArgumentException("Product is archived");
        }

        // If exists, increase; else create
        CartItem item = cart.findByUser_IdAndProduct_Id(me.getId(), p.getId())
                .orElseGet(() -> CartItem.builder()
                        .user(me)
                        .product(p)
                        .quantity(0)
                        .build());

        int newQty = item.getQuantity() + req.getQuantity();

        // Basic stock check (cart-side check; final check at checkout)
        if (p.getStock() < newQty) {
            throw new IllegalArgumentException("Requested quantity exceeds stock");
        }

        item.setQuantity(newQty);
        cart.save(item);
    }

    @Transactional
    public void updateQuantity(Long productId, UpdateCartItemRequest req) {
        AppUser me = currentUserService.requireCurrentUser();
        CartItem item = cart.findByUser_IdAndProduct_Id(me.getId(), productId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        Product p = item.getProduct();
        if (p.getStock() < req.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds stock");
        }

        item.setQuantity(req.getQuantity());
        cart.save(item);
    }

    @Transactional
    public void remove(Long productId) {
        AppUser me = currentUserService.requireCurrentUser();
        cart.deleteByUser_IdAndProduct_Id(me.getId(), productId);
    }

    @Transactional
    public void clearMyCart() {
        AppUser me = currentUserService.requireCurrentUser();
        cart.deleteByUser_Id(me.getId());
    }

    // ------- mapping -------
    private CartItemDto toDto(CartItem ci) {
        BigDecimal lineTotal = ci.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(ci.getQuantity()));
        return new CartItemDto(
                ci.getProduct().getId(),
                ci.getProduct().getName(),
                ci.getProduct().getPrice(),
                ci.getQuantity(),
                lineTotal
        );
    }
}
