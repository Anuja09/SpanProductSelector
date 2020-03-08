package com.span.calculators.product_selector.service;

import com.span.calculators.product_selector.model.Product;
import com.span.calculators.product_selector.model.ProductSelection;
import com.span.calculators.product_selector.model.RequestInput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LsiRsiCalculatorTest {

  @InjectMocks
  LsiRsiCalculator lsiRsiCalculator;

  @Test
  public void shouldCalculate() {
    double tds = 1688;
    double temp = 84;
    double calcium = 300;
    double alkalinity = 120;
    double ph = 7.8;
    double saturationPH = 7.29;
    double saturationPh1 = lsiRsiCalculator.calculateSaturationPh1(tds, temp, calcium, alkalinity);
    double lsi = lsiRsiCalculator.calculateLsi(ph, saturationPh1);
    double rsi = lsiRsiCalculator.calculateRsi(ph, saturationPh1);
    Assert.assertEquals(7.29, lsiRsiCalculator.calculateSaturationPh(tds, temp, calcium, alkalinity), 0);
    Assert.assertEquals(0.51, lsi, 0);
    Assert.assertEquals(6.77, rsi, 0);
  }

  @Test
  public void shouldSelectProduct() {
    List<Product> products = lsiRsiCalculator.selectProduct(2.0, 4.0);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(2.1, 3.9);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(1.0, 5.0);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(0.5, 5.5);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(-0.5, 7.0);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(-0.4, 6.0);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(-0.1, 8.0);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(-0.2, 9.0);
    System.out.println("Product Selected = " + products);
    products = lsiRsiCalculator.selectProduct(-0.3, 10.0);
    System.out.println("Product Selected = " + products);
  }

  @Test
  public void shouldSelectProductInfo() {
    ProductSelection productSelected = lsiRsiCalculator.selectProductInfo(3.0, 3.0);
    System.out.println("Product Selected = " + productSelected);
    Assert.assertEquals(CaCO3FormationPotential.LSI_RSI_EXTREME_PRECIPITATION.potential,
                        productSelected.getLsiRsiPotential().getCaco3FormationPotential());

    productSelected = lsiRsiCalculator.selectProductInfo(2.0, 4.0);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(1.0, 5.0);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(0.5, 5.5);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(0.2, 5.8);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(0.0, 6.0);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(-0.2, 6.5);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(-0.5, 7.0);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(-1.0, 8.0);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(-2.0, 9.0);
    System.out.println("Product Selected = " + productSelected);

    productSelected = lsiRsiCalculator.selectProductInfo(-3.0, 10.0);
    System.out.println("Product Selected = " + productSelected);
  }

  @Test
  public void shouldCalculate1() {
    double tds = 1688;
    double temp = 84;
    double calcium = 300;
    double alkalinity = 120;
    double ph = 1.5;
    double saturationPH = lsiRsiCalculator.calculateSaturationPh(tds, temp, calcium, alkalinity);
    Assert.assertEquals(7.29, saturationPH, 0);
    Assert.assertEquals(-5.79, lsiRsiCalculator.calculateLsi(ph, saturationPH), 0);
    Assert.assertEquals(13.08, lsiRsiCalculator.calculateRsi(ph, saturationPH), 0);
  }


  @Test
  public void shouldReturnProductInfo() {
    double tds = 1200;
    double temp = 80;
    double calcium = 300;
    double alkalinity = 120;
    double ph = 7.8;
    RequestInput requestInput = new RequestInput(tds, temp, calcium, alkalinity, ph);

    ProductSelection productSelection = lsiRsiCalculator.selectProductInfo(requestInput);
    System.out.println("Product Selected = " + productSelection);
  }

}
