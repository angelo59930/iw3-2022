package org.magm.backend.integration.cli2.model;

import java.util.Date;

import org.magm.backend.model.Item;
import org.magm.backend.model.Product;

public interface BillCli2SlimView {
  long getNumber();

  Date getIssueDate();

  Date getExpitDate();

  boolean getAnnulled();

  Item getItems();

  //Puede ser que en vez de Items sea Item
  interface Items {
    double getQuantity();

    double getPrice();

    // TODO: fijarse si agregar los productos
    Product getProduct();

    interface Product {
      String getProduct();
    }
  }
  
}
