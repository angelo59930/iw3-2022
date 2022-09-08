package org.magm.backend.integration.cli2.model.persistence;

import java.util.List;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillCli2Repository extends JpaRepository<BillCli2, Long>{
	
	public BillCli2 generateBill();
	
	public BillCli2 modifyBill(BillCli2 bill);
	
	public BillCli2 deleteBill(long id);
	
	public BillCli2 getBill(long id);
	
	public List<BillCli2> getBillListNotAnnulled();
	
}