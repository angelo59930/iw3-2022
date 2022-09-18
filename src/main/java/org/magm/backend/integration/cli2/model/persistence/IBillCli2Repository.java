package org.magm.backend.integration.cli2.model.persistence;

import java.util.List;
import java.util.Optional;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.magm.backend.integration.cli2.model.BillCli2SlimView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillCli2Repository extends JpaRepository<BillCli2, Long>{

	@Modifying
	@Query(value = "UPDATE bills SET annulled=? WHERE id=?", nativeQuery = true)
	public boolean setAnnullation(boolean state, long idBill);
	
	@Modifying
	@Query(value = "SELECT b.id FROM bills as b \n"
			+ "	INNER JOIN items as i ON i.id_bill = b.id\n"
			+ "    INNER JOIN products as p ON p.id = i.id_product\n"
			+ "    WHERE p.id = ?", nativeQuery = true)
	public List<Long> getByBillByProduct(long id);
	
	public BillCli2SlimView getById(long id);
}
