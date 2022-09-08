package org.magm.backend.integration.cli2.model.persistence;

import java.util.Optional;

import javax.transaction.Transactional;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillCli2Repository extends JpaRepository<BillCli2, Long>{
	
	Optional<BillCli2> findByBill(BillCli2 bill);

	@Transactional
	@Modifying
	@Query(value = "UPDATE bills SET annulled=? WHERE id=?", nativeQuery = true)
	public boolean setAnnullation(boolean state, long idBill);
	
}
