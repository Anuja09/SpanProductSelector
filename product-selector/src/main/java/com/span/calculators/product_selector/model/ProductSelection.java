package com.span.calculators.product_selector.model;

import java.util.Collections;
import java.util.List;

public class ProductSelection {
  private double saturationPh;
  private LsiRsiPotential lsiRsiPotential;
  private List<Product> products = Collections.emptyList();

  public LsiRsiPotential getLsiRsiPotential() {
    return lsiRsiPotential;
  }

  public void setLsiRsiPotential(final LsiRsiPotential lsiRsiPotential) {
    this.lsiRsiPotential = lsiRsiPotential;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(final List<Product> products) {
    this.products = products;
  }

  public double getSaturationPh() {
    return saturationPh;
  }

  public void setSaturationPh(final double saturationPh) {
    this.saturationPh = saturationPh;
  }

  @Override
  public String toString() {
    return "ProductSelection{" +
           "saturationPh='" +
           saturationPh +
           '\'' +
           ", lsiRsiPotential=" +
           lsiRsiPotential +
           ", products=" +
           products +
           '}';
  }
}
