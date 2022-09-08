package org.magm.backend.integration.cli2.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCli2 implements Serializable{
	
	private static final long serialVersionUID = -8164412031824412059L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private double quantity;
	
	private double price;
	
	@ManyToOne
	@JoinColumn(name = "id_product", nullable = false)
	private ProductCli2 product;

}