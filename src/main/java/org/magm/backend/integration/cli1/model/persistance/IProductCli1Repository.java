package org.magm.backend.integration.cli1.model.persistance;

import java.util.Optional;
import org.magm.backend.integration.cli1.model.ProductCli1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCli1Repository extends JpaRepository<ProductCli1, Long>{
	
	public Optional<ProductCli1> findOneByCodCli1(String codCli1);
	
}
