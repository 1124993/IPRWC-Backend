package nl.hsleiden.ammaris.controller;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.AdminOrderSummaryDto;
import nl.hsleiden.ammaris.dto.OrderLineDto;
import nl.hsleiden.ammaris.service.AdminOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public ResponseEntity<List<AdminOrderSummaryDto>> list() {
        return ResponseEntity.ok(adminOrderService.listAllOrders());
    }

    @GetMapping("/{orderId}/lines")
    public ResponseEntity<List<OrderLineDto>> lines(@PathVariable Long orderId) {
        return ResponseEntity.ok(adminOrderService.listOrderLines(orderId));
    }
}
