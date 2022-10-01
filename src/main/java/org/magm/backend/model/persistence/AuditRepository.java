package org.magm.backend.model.persistence;

import java.util.List;

import org.magm.backend.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long>{
  
  @Query(value = "SELECT * FROM audits WHERE audits.user = ? ",nativeQuery = true)
  public List<Audit> userAudits(String username);
}
