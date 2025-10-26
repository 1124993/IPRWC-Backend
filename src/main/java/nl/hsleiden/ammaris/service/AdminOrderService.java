package nl.hsleiden.ammaris.service;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.AdminOrderSummaryDto;
import nl.hsleiden.ammaris.dto.OrderLineDto;
import nl.hsleiden.ammaris.entity.Order;
import nl.hsleiden.ammaris.repository.OrderLineRepository;
import nl.hsleiden.ammaris.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orders;
    private final OrderLineRepository lines;

    @Transactional(readOnly = true)
    public List<AdminOrderSummaryDto> listAllOrders() {
        return orders.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toSummary)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderLineDto> listOrderLines(Long orderId) {
        return lines.findByOrderRef_Id(orderId)
                .stream()
                .map(ol -> new OrderLineDto(
                        ol.getProduct().getId(),
                        ol.getProduct().getName(),
                        ol.getQuantity(),
                        ol.getLineTotal()
                ))
                .toList();
    }

    private AdminOrderSummaryDto toSummary(Order o) {
        String email = (o.getUser() != null) ? o.getUser().getEmail() : null;
        return new AdminOrderSummaryDto(o.getId(), email, o.getCreatedAt());
    }
}
