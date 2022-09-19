package org.magm.backend.integration.cli2.model;

public interface BillCli2SlimView {

  Item getItems();

  interface Item {
    double getQuantity();

    double getPrice();

    Product getProduct();

    interface Product {
      String getProduct();
    }

  }
}
