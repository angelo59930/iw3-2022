package org.magm.backend.model.persistence;

import java.util.Optional;

import org.magm.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long>{
	
	public Optional <Category> findOneByCategory(String category);

}
