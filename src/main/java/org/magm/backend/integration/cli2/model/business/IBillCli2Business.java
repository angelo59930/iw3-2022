package org.magm.backend.integration.cli2.model.business;

import java.util.List;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.FoundException;
import org.magm.backend.model.business.NotFoundException;

public interface IBillCli2Business {

  public BillCli2 load(long id) throws NotFoundException, BusinessException; // Pedir una Bill ✓

  public List<BillCli2> list() throws BusinessException; // Listado de todas las Bills ✓

  public BillCli2 add(BillCli2 bill) throws FoundException, BusinessException; // Dar de alta una Bill ✓

  public BillCli2 update(BillCli2 bill) throws NotFoundException, BusinessException; // Actualizar una Bill ✓

  public List<BillCli2> listNoAnulled() throws BusinessException; // Listado de todas las Bills no anuladas

  public BillCli2 anulledBill(long id) throws NotFoundException, BusinessException; // Anular una Bill

}
