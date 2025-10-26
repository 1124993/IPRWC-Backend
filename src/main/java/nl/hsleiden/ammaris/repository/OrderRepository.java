package nl.hsleiden.ammaris.repository;

import nl.hsleiden.ammaris.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // existing method — for users (shows only their own orders)
    List<Order> findByUser_IdOrderByCreatedAtDesc(Long userId);

    // new method — for admins (shows all orders, newest first)
    List<Order> findAllByOrderByCreatedAtDesc();
}
