package org.magm.backend.integration.cli2.model.persistence;

import java.util.List;
import java.util.Optional;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillCli2Repository extends JpaRepository<BillCli2, Long>{
	
	public Optional<BillCli2> findByNumber(BillCli2 bill);
	

	@Modifying
	@Query(value = "UPDATE bills SET annulled=? WHERE id=?", nativeQuery = true)
	public boolean setAnnullation(boolean state, long idBill);
	
}
