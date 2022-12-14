package org.magm.backend.integration.cli2.model.business;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.magm.backend.controllers.BaseRestController;
import org.magm.backend.integration.cli2.model.BillCli2;
import org.magm.backend.integration.cli2.model.BillCli2SlimView;
import org.magm.backend.integration.cli2.model.persistence.IBillCli2Repository;
import org.magm.backend.model.Audit;
import org.magm.backend.model.business.AuditBusiness;
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

	@Autowired
	private AuditBusiness auditBusiness;

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
			throw FoundException.builder().message("Se encontrĂ³ la factura id=" + bill.getId()).build();
		} catch (NotFoundException e) {
		}

		try {
			BillCli2 billCli2 = billDAO.save(bill);

			generateAudit(bill, "ALTA");
			
			return billCli2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public BillCli2 update(BillCli2 bill) throws NotFoundException, BusinessException {
		load(bill.getId());
		try {

			BillCli2 billCli2 = billDAO.save(bill);

			generateAudit(bill, "MODIFICACION");

			return billCli2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw BusinessException.builder().ex(e).build();
		}
	}

	@Override
	public List<BillCli2> listNoAnulled() throws BusinessException {
		List<BillCli2> listNoAnulled = list();
		try {
			for (BillCli2 bill : listNoAnulled) {
				if (bill.isAnnulled())
					listNoAnulled.remove(bill);
			}
		} catch (Exception e) {
		}

		return listNoAnulled;
	}

	@Override
	public BillCli2 anulledBill(long id) throws NotFoundException, BusinessException {
		BillCli2 bill = load(id);

		try {
			generateAudit(bill, "BAJA");
		} catch (FoundException e) {
			e.printStackTrace();
		}

		bill.setAnnulled(true);
		return billDAO.save(bill);
	}

	@Override
	public List<Long> loadBillByProduct(long id) {

		return billDAO.getByBillByProduct(id);
	}

	@Override
	public BillCli2SlimView loadSlimView(long id) {
		return billDAO.readById(id);
	}

	private void generateAudit(BillCli2 bill, String type) throws FoundException, BusinessException {
		Audit audit = new Audit();

		String defaultFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
		Date date = new Date();
		DateTimeFormatter.ofPattern(defaultFormat);

		audit.setAuditDate(date);
		audit.setBill(bill);
		audit.setType(type);
		audit.setUser(BaseRestController.getUserLoggedAudit());

		auditBusiness.add(audit);

	}

}