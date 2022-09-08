package org.magm.backend.integration.cli2.model.business;

import java.util.List;
import java.util.Optional;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.magm.backend.integration.cli2.model.persistence.IBillCli2Repository;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.FoundException;
import org.magm.backend.model.business.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BillCli2Business implements IBillCli2Business {

	private IBillCli2Repository billDAO;

	@Override
	public BillCli2 generateBill(BillCli2 bill) throws FoundException, BusinessException{

		if(billDAO.findById(bill.getId()).isPresent()) {
			throw FoundException.builder().message("Se encontró el Bill id=" + bill.getId()).build();	
		}
		
		try {
			return billDAO.save(bill);
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
		
	}

	@Autowired(required = false)
	private IBillCli2Business billCli2Business;
	@Override
	public BillCli2 modifyBill(BillCli2 bill) throws NotFoundException, BusinessException{

		try {
			billCli2Business.generateBill(bill.getId());
			throw FoundException.builder().message("Se encontro la Factura id=" + bill.getId()).build();
		}catch (NotFoundException e) {
			throw NotFoundException.builder().message("La factura no existe");
		}

		try {
			return billDAO.save(bill);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}

	}

	@Override
	public BillCli2 deleteBill(long id) throws NotFoundException, BusinessException {
		BillCli2 bill = getBill(id);
		try {
			billDAO.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
		return bill;
	}

	@Override
	public BillCli2 getBill(long id) throws NotFoundException, BusinessException {
		Optional<BillCli2> r;
		try {
			r = billDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
		if(r.isEmpty())
			throw NotFoundException.builder().message("no se encuentra la factura con id '" + id + "'").build();

		return r.get();
	}

	@Override
	public List<BillCli2> getBillListNotAnnulled() {
				
		List <BillCli2> lista = billDAO.findAll();
		
		for(BillCli2 b : lista) {
			
			if(b.isAnnulled()) {
				lista.remove(b);
			}	
		}
		
		return lista;
	}

}
