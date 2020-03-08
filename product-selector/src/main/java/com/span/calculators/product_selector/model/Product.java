package com.span.calculators.product_selector.model;

public class Product {
  String code;
  String product;
  String wtcProductName;

  public Product(final String code, final String product, final String wtcProductName) {
    this.code = code;
    this.product = product;
    this.wtcProductName = wtcProductName;
  }

  public Product() {

  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(final String product) {
    this.product = product;
  }

  public String getWtcProductName() {
    return wtcProductName;
  }

  public void setWtcProductName(final String wtcProductName) {
    this.wtcProductName = wtcProductName;
  }

  @Override
  public String toString() {
    return "Product{" +
           "code='" +
           code +
           '\'' +
           ", product='" +
           product +
           '\'' +
           ", wtcProductName='" +
           wtcProductName +
           '\'' +
           '}';
  }
}
