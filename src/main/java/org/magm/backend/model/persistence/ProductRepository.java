package org.magm.backend.model.persistence;

import java.util.Optional;

import org.magm.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{ 
	Optional<Product> findByProduct(String product);

}
