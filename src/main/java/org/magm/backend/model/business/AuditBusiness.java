package org.magm.backend.model.business;

import java.util.List;
import java.util.Optional;

import org.magm.backend.model.Audit;
import org.magm.backend.model.persistence.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditBusiness implements IAuditBusiness {

  @Autowired
  private AuditRepository auditDAO;

  @Override
  public Audit load(long id) throws NotFoundException, BusinessException {
    Optional<Audit> r;
    try {
      r = auditDAO.findById(id);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw BusinessException.builder().ex(e).build();
    }
    if (r.isEmpty()) {
      throw NotFoundException.builder().message("No se encuentra la Auditoria id=" + id).build();
    }

    return r.get();
  }

  @Override
  public Audit add(Audit audit) throws FoundException, BusinessException {

    try {
      load(audit.getId());
      throw FoundException.builder().message("Se encuentró la Auditoria id=" + audit.getId()).build();
    } catch (NotFoundException e) {
    }

    try {
      return auditDAO.save(audit);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw BusinessException.builder().ex(e).build();
    }

  }

  @Override
  public List<Audit> list(String username) throws BusinessException {
    try {
      return auditDAO.userAudits(username);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw BusinessException.builder().ex(e).build();
    }
  }

}
