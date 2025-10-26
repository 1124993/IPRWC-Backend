package nl.hsleiden.ammaris.service;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.ammaris.dto.OrderDto;
import nl.hsleiden.ammaris.dto.OrderLineDto;
import nl.hsleiden.ammaris.entity.AppUser;
import nl.hsleiden.ammaris.entity.Order;
import nl.hsleiden.ammaris.repository.OrderLineRepository;
import nl.hsleiden.ammaris.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final CurrentUserService currentUserService;
    private final OrderRepository orders;
    private final OrderLineRepository lines;

    @Transactional(readOnly = true)
    public List<OrderDto> listMyOrders() {
        AppUser me = currentUserService.requireCurrentUser();
        return orders.findByUser_IdOrderByCreatedAtDesc(me.getId())
                .stream().map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderLineDto> listOrderLines(Long orderId) {
        // (Optional) You could verify the order belongs to current user.
        return lines.findByOrderRef_Id(orderId)
                .stream().map(ol ->
                        new OrderLineDto(
                                ol.getProduct().getId(),
                                ol.getProduct().getName(),
                                ol.getQuantity(),
                                ol.getLineTotal()
                        )
                ).toList();
    }

    private OrderDto toDto(Order o) {
        return new OrderDto(o.getId(), o.getCreatedAt());
    }
}
