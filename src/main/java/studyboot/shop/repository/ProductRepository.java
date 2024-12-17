package studyboot.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyboot.shop.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Автоматически сгенерирован запрос для получения всех продуктов, у которых
    // поле active имеет значение true
    List<Product> findByActiveTrue();

}


