package org.magm.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.magm.backend.auth.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="audits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audit implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column(nullable = false)
  private Date auditDate;
  
  @Column(nullable = false)
  private String type;
  
  @ManyToOne()
  @JoinColumn(name ="user", nullable = false)
  private User user;
  
  @ManyToOne()
  @JoinColumn(name = "id_bill", nullable = false)
  private Bill bill;
}
