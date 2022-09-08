package org.magm.backend.integration.cli2.model.business;

import java.util.List;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.NotFoundException;

public interface IBillCli2Business {
  public BillCli2 generateBill(BillCli2 bill) throws FoundException, BusinessException;

  public BillCli2 modifyBill(BillCli2 bill) throws NotFoundException, BusinessException;

  public BillCli2 deleteBill(long id) throws NotFoundException, BusinessException;

  public BillCli2 getBill(long id) throws NotFoundException, BusinessException;

  public List<BillCli2> getBillListNotAnnulled();
}
