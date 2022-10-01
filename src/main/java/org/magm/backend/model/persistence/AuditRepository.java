package org.magm.backend.model.persistence;

import java.util.List;

import org.magm.backend.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long>{
  
  @Query(value = "SELECT * FROM audits as a INNER JOIN users as u WHERE u.id_user = ?",nativeQuery = true)
  public List<Audit> userAudits(Long long1);
}
