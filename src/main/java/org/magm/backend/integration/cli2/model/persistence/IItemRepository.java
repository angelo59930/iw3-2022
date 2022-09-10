package org.magm.backend.integration.cli2.model.persistence;

import java.util.Optional;

import org.magm.backend.integration.cli2.model.ItemCli2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemRepository extends JpaRepository<ItemCli2, Long> {
  public Optional<ItemCli2> findByCodItemCli2(String codItemCli2);

}
