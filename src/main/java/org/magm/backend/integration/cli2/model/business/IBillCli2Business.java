package org.magm.backend.integration.cli2.model.business;

import java.util.List;

import org.magm.backend.integration.cli2.model.BillCli2;

public interface IBillCli2Business {
  public BillCli2 generateBill();

  public BillCli2 modifyBill(BillCli2 bill);

  public BillCli2 deleteBill(long id);

  public BillCli2 getBill(long id);

  public List<BillCli2> getBillListNotAnnulled();
}
