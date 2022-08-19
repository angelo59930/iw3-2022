package org.magm.backend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable{

	private static final long serialVersionUID = 1583413618748026543L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100, unique = true)
	private String product;
	
	@Column(columnDefinition = "tinyint default 0")
	private boolean stock = true;

	private double price;

}
