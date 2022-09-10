package org.magm.backend.integration.cli2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.magm.backend.model.Bill;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cli2_factura")
@PrimaryKeyJoinColumn(name = "id_bill")
@Getter
@Setter
public class BillCli2 extends Bill {
	
	private static final long serialVersionUID = -7660002054606583573L;

	@Column(nullable = false, unique = true)
	private String codFacturaCli2;

}
