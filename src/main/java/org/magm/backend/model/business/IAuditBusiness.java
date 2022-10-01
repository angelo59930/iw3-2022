package org.magm.backend.model.business;

import java.util.List;

import org.magm.backend.model.Audit;
import org.springframework.stereotype.Service;

@Service
public interface IAuditBusiness {

  public Audit load(long id) throws NotFoundException, BusinessException;
  
  public Audit add(Audit audit) throws FoundException, BusinessException;
  
  public List<Audit> list(String username) throws BusinessException;

}
