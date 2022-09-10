package org.magm.backend.integration.cli2.model.business;

import org.magm.backend.integration.cli2.model.ItemCli2;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.FoundException;
import org.magm.backend.model.business.NotFoundException;

public interface IItemCli2Business {

  public ItemCli2 add(ItemCli2 item) throws FoundException, BusinessException;

  public ItemCli2 load(long id) throws FoundException, BusinessException, NotFoundException;

  public ItemCli2 update(ItemCli2 item) throws NotFoundException, BusinessException;

}
