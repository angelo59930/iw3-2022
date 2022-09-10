package org.magm.backend.integration.cli2.model.business;

import java.util.Optional;
import java.util.Set;

import org.magm.backend.integration.cli2.model.BillCli2;
import org.magm.backend.integration.cli2.model.ItemCli2;
import org.magm.backend.integration.cli2.model.persistence.IItemRepository;
import org.magm.backend.model.business.BusinessException;
import org.magm.backend.model.business.FoundException;
import org.magm.backend.model.business.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemCli2Business implements IItemCli2Business {

  @Autowired
  private IItemRepository itemDAO;

  @Override
  public ItemCli2 load(long id) throws NotFoundException, BusinessException {
    Optional<ItemCli2> r;
    try {
      r = itemDAO.findById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw BusinessException.builder().ex(e).build();
    }
    if (r.isEmpty()) {
      throw NotFoundException.builder().message("No se encuentra el Item id=" + id).build();
    }
    return r.get();
  }

  @Override
  public ItemCli2 add(ItemCli2 item) throws FoundException, BusinessException {
    try {
      load(item.getId());
      throw FoundException.builder().message("Se encuentró el Item id=" + item.getId()).build();
    } catch (NotFoundException e) {
    }
    try {
      return itemDAO.save(item);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw BusinessException.builder().ex(e).build();
    }
  }

  @Override
  public ItemCli2 update(ItemCli2 item) throws NotFoundException, BusinessException {
    load(item.getId());
    try {
      return itemDAO.save(item);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw BusinessException.builder().ex(e).build();
    }
  }

  @Override
  public void addList(Set<ItemCli2> itemsSet) throws FoundException, BusinessException {
    // TODO Auto-generated method stub
    try {
      for (ItemCli2 item : itemsSet) {
        load(item.getId());
        itemDAO.save(item);
      }
    }catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}
