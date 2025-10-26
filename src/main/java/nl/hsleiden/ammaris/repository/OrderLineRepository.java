package nl.hsleiden.ammaris.repository;

import nl.hsleiden.ammaris.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    List<OrderLine> findByOrderRef_Id(Long orderId);
}
