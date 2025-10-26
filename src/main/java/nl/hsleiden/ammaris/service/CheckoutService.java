package nl.hsleiden.ammaris.service;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.entity.AppUser;
import nl.hsleiden.ammaris.entity.CartItem;
import nl.hsleiden.ammaris.entity.Order;
import nl.hsleiden.ammaris.entity.OrderLine;
import nl.hsleiden.ammaris.repository.CartItemRepository;
import nl.hsleiden.ammaris.repository.OrderLineRepository;
import nl.hsleiden.ammaris.repository.OrderRepository;
import nl.hsleiden.ammaris.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CurrentUserService currentUserService;
    private final CartItemRepository cart;
    private final ProductRepository products;
    private final OrderRepository orders;
    private final OrderLineRepository lines;

    @Transactional
    public Long checkout() {
        AppUser me = currentUserService.requireCurrentUser();
        List<CartItem> items = cart.findByUser_Id(me.getId());

        if (items.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Validate stock first (all-or-nothing)
        items.forEach(ci -> {
            var p = ci.getProduct();
            if (p.getStock() < ci.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + p.getName());
            }
        });

        // Create order header
        Order order = Order.builder()
                .user(me)
                .createdAt(OffsetDateTime.now())
                .build();
        orders.save(order);

        // Create lines + decrement stock
        for (CartItem ci : items) {
            var p = ci.getProduct();
            p.setStock(p.getStock() - ci.getQuantity());
            products.save(p);

            BigDecimal lineTotal = p.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
            OrderLine ol = OrderLine.builder()
                    .orderRef(order)
                    .product(p)
                    .quantity(ci.getQuantity())
                    .lineTotal(lineTotal)
                    .build();
            lines.save(ol);
        }

        // Clear cart
        cart.deleteByUser_Id(me.getId());

        return order.getId();
    }
}
