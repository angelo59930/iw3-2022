package org.magm.backend.integration.cli2.model;

import javax.persistence.*;

import org.magm.backend.model.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cli2_items")
@PrimaryKeyJoinColumn(name = "id_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemCli2 extends Item {

  private static final long serialVersionUID = -8164412031824412059L;

  @Column(nullable = false, unique = true)
  private String codItemCli2;

}
