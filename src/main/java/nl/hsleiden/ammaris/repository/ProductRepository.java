package nl.hsleiden.ammaris.repository;

import nl.hsleiden.ammaris.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByArchivedFalse();
    List<Product> findByArchivedFalseAndCategory(Product.Category category);
}
