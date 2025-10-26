package nl.hsleiden.ammaris.controller;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.CheckoutResponse;
import nl.hsleiden.ammaris.dto.OrderDto;
import nl.hsleiden.ammaris.dto.OrderLineDto;
import nl.hsleiden.ammaris.service.CheckoutService;
import nl.hsleiden.ammaris.service.OrderQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CheckoutService checkoutService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout() {
        Long orderId = checkoutService.checkout();
        return ResponseEntity
                .created(URI.create("/api/orders/" + orderId))
                .body(new CheckoutResponse(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> myOrders() {
        return ResponseEntity.ok(orderQueryService.listMyOrders());
    }

    @GetMapping("/{orderId}/lines")
    public ResponseEntity<List<OrderLineDto>> lines(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderQueryService.listOrderLines(orderId));
    }
}
