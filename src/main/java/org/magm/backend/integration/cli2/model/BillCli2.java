package org.magm.backend.integration.cli2.model;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillCli2 implements Serializable{
	
	private static final long serialVersionUID = -7660002054606583573L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private long number;
	
	@Column(nullable = false)
	private Date issueDate;
	
	@Column(nullable = false)
	private Date expirationDate;
	
	@Column(columnDefinition = "tinyint default 0")
	private boolean annulled;
	
	@OneToMany()
	@JoinColumn(name = "id_bill", nullable = true)
	private Set<ItemCli2> items;

}
