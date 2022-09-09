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

	@Autowired
	private IBillCli2Repository billDAO;

	@Override
	public List<BillCli2> list() throws BusinessException {
		try {
			return billDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public BillCli2 load(long id) throws NotFoundException, BusinessException {
		Optional<BillCli2> r;
		try {
			r = billDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
		if (r.isEmpty()) {
			throw NotFoundException.builder().message("No se encuentra el Producto id=" + id).build();
		}
		return r.get();
	}

	@Override
	public BillCli2 add(BillCli2 bill) throws FoundException, BusinessException {
		try {
			load(bill.getId());
			throw FoundException.builder().message("Se encuentró el Producto id=" + bill.getId()).build();
		} catch (NotFoundException e) {
		}
		try {
			return billDAO.save(bill);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public BillCli2 update(BillCli2 bill) throws NotFoundException, BusinessException {
		load(bill.getId());
		try {
			return billDAO.save(bill);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public List<BillCli2> listNoAnulled() throws BusinessException {
		List<BillCli2> listNoAnulled = list();

		for (BillCli2 bill : listNoAnulled) {
			if (bill.isAnnulled())
				listNoAnulled.remove(bill);
		}

		return listNoAnulled;
	}

}