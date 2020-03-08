package com.span.calculators.product_selector.service;

import com.span.calculators.product_selector.model.Product;
import com.span.calculators.product_selector.model.ProductSelection;
import com.span.calculators.product_selector.model.RequestInput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LsiRsiCalculatorInterface {
  double calculateLsi(double ph, double saturationPh);

  double calculateRsi(double ph, double saturationPh);

  double calculateSaturationPh(final double tds,
                               final double temp,
                               final double calcium,
                               final double alkalinity);

  double calculateSaturationPh1(final double tds,
                                       final double temp,
                                       final double calcium,
                                       final double alkalinity);

  List<Product> selectProduct(double lsi, double rsi);

  ProductSelection selectProductInfo(final double lsi, final double rsi);

  ProductSelection selectProductInfo(final RequestInput requestInput);
}
