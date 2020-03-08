package com.span.calculators.product_selector.service;

import com.span.calculators.product_selector.model.LsiRsiPotential;
import com.span.calculators.product_selector.model.Product;
import com.span.calculators.product_selector.model.ProductSelection;
import com.span.calculators.product_selector.model.RequestInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LsiRsiCalculator implements LsiRsiCalculatorInterface {
  @Override
  public double calculateLsi(double ph, double saturationPh) {
    return Math.round((ph - saturationPh) * 100.0) / 100.0;
  }

  @Override
  public double calculateRsi(double ph, double saturationPh) {
    return Math.round((((2 * saturationPh) - ph) * 100.0)) / 100.0;
  }

  @Override
  public double calculateSaturationPh1(final double tds,
                                       final double temp,
                                       final double calcium,
                                       final double alkalinity) {
    double factorA = (-0.1083 + 0.0446 * Math.log(tds));
    double factorB = 3.0801 * Math.exp(-0.0056 * temp);
    double factorC = -0.4022 + 0.4353 * Math.log(calcium);
    double factorD = -0.0002 + 0.4344 * Math.log(alkalinity);
    return (9.3 + factorA + factorB) - (factorC + factorD);
  }

  @Override
  public ProductSelection selectProductInfo(final double lsi, final double rsi) {
    ProductSelection productSelection = new ProductSelection();
    productSelection.setLsiRsiPotential(selectCaCO3FormationPotential(lsi, rsi));
    productSelection.setProducts(selectProduct(lsi, rsi));
    return productSelection;
  }

  @Override
  public ProductSelection selectProductInfo(final RequestInput requestInput) {

    double saturationPh = calculateSaturationPh1(requestInput.getTds(),
                                                 requestInput.getTemp(),
                                                 requestInput.getCalcium(),
                                                 requestInput.getAlkalinity());
    double lsi = calculateLsi(requestInput.getPh(), saturationPh);
    double rsi = calculateRsi(requestInput.getPh(), saturationPh);

    ProductSelection productSelection = selectProductInfo(lsi, rsi);

    double saturationPhRoudOff = calculateSaturationPh(requestInput.getTds(),
                                                       requestInput.getTemp(),
                                                       requestInput.getCalcium(),
                                                       requestInput.getAlkalinity());
    productSelection.setSaturationPh(saturationPhRoudOff);
    return productSelection;
  }

  private LsiRsiPotential selectCaCO3FormationPotential(final double lsi, final double rsi) {
    LsiRsiPotential lsiRsiPotential = new LsiRsiPotential();
    lsiRsiPotential.setLsi(lsi);
    lsiRsiPotential.setRsi(rsi);

    if (isLSI_rsi_extreme_precipitation(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_EXTREME_PRECIPITATION.potential);
    } else if (isLSI_rsi_very_high_precipitation(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_VERY_HIGH_PRECIPITATION.potential);
    } else if (isLSI_rsi_high_precipitation(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_HIGH_PRECIPITATION.potential);
    } else if (isLSI_rsi_moderate_precipitation(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_MODERATE_PRECIPITATION.potential);
    } else if (isLSI_rsi_slight_precipitation(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_SLIGHT_PRECIPITATION.potential);
    } else if (isLSI_rsi_no_precipitation(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_NO_PRECIPITATION.potential);
    } else if (isLSI_rsi_very_slight_dissolution(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_VERY_SLIGHT_DISSOLUTION.potential);
    } else if (isLSI_rsi_slight_dissolution(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_SLIGHT_DISSOLUTION.potential);
    } else if (isLSI_rsi_moderate_dissolution(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_MODERATE_DISSOLUTION.potential);
    } else if (isLSI_rsi_strong_dissolution(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_STRONG_DISSOLUTION.potential);
    } else if (isLSI_RSI_very_strong_dissolution(lsi, rsi)) {
      lsiRsiPotential.setCaco3FormationPotential(CaCO3FormationPotential.LSI_RSI_VERY_STRONG_DISSOLUTION.potential);
    }
    return lsiRsiPotential;
  }

  private boolean isLSI_RSI_very_strong_dissolution(final double lsi, final double rsi) {
    return lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_STRONG_DISSOLUTION.lsi &&
           rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_STRONG_DISSOLUTION.rsi;
  }

  private boolean isLSI_rsi_strong_dissolution(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_STRONG_DISSOLUTION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_STRONG_DISSOLUTION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_STRONG_DISSOLUTION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_STRONG_DISSOLUTION.rsi);
  }

  private boolean isLSI_rsi_moderate_dissolution(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_DISSOLUTION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_STRONG_DISSOLUTION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_DISSOLUTION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_STRONG_DISSOLUTION.rsi);
  }

  private boolean isLSI_rsi_slight_dissolution(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_DISSOLUTION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_DISSOLUTION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_DISSOLUTION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_DISSOLUTION.rsi);
  }

  private boolean isLSI_rsi_very_slight_dissolution(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_SLIGHT_DISSOLUTION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_DISSOLUTION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_SLIGHT_DISSOLUTION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_DISSOLUTION.rsi);
  }

  private boolean isLSI_rsi_no_precipitation(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_NO_PRECIPITATION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_SLIGHT_DISSOLUTION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_NO_PRECIPITATION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_SLIGHT_DISSOLUTION.rsi);
  }

  private boolean isLSI_rsi_slight_precipitation(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_PRECIPITATION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_NO_PRECIPITATION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_PRECIPITATION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_NO_PRECIPITATION.rsi);
  }

  private boolean isLSI_rsi_moderate_precipitation(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_PRECIPITATION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_PRECIPITATION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_PRECIPITATION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_SLIGHT_PRECIPITATION.rsi);
  }

  private boolean isLSI_rsi_high_precipitation(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_HIGH_PRECIPITATION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_PRECIPITATION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_HIGH_PRECIPITATION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_MODERATE_PRECIPITATION.rsi);
  }

  private boolean isLSI_rsi_very_high_precipitation(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_HIGH_PRECIPITATION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_HIGH_PRECIPITATION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_HIGH_PRECIPITATION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_HIGH_PRECIPITATION.rsi);
  }

  private boolean isLSI_rsi_extreme_precipitation(final double lsi, final double rsi) {
    return (lsi <= LsiRsiCaCO3FormationPotential.LSI_RSI_EXTREME_PRECIPITATION.lsi &&
            lsi > LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_HIGH_PRECIPITATION.lsi) &&
           (rsi >= LsiRsiCaCO3FormationPotential.LSI_RSI_EXTREME_PRECIPITATION.rsi &&
            rsi < LsiRsiCaCO3FormationPotential.LSI_RSI_VERY_HIGH_PRECIPITATION.rsi);
  }

  @Override
  public List<Product> selectProduct(final double lsi, final double rsi) {
    List<Product> productList = new ArrayList<Product>();
    if ((lsi >= LsiRange.A_Range.start && lsi <= LsiRange.A_Range.end) &&
        (rsi >= RsiRange.A_Range.start && rsi <= RsiRange.A_Range.end)) {
      Product product = new Product();
      product.setCode(WTCProductName.A.toString());
      product.setWtcProductName(WTCProductName.A.getValue());
      product.setProduct(ProductCode.A.getValue());
      productList.add(product);
    }
    if ((lsi >= LsiRange.B_Range.start && lsi <= LsiRange.B_Range.end) &&
        (rsi >= RsiRange.B_Range.start && rsi <= RsiRange.B_Range.end)) {
      Product product = new Product();
      product.setCode(WTCProductName.B.name());
      product.setWtcProductName(WTCProductName.B.getValue());
      product.setProduct(ProductCode.B.getValue());
      productList.add(product);
    }
    if ((lsi >= LsiRange.BC_Range.start && lsi <= LsiRange.BC_Range.end) &&
        (rsi >= RsiRange.BC_Range.start && rsi <= RsiRange.BC_Range.end)) {
      Product product = new Product();
      product.setCode(WTCProductName.BC.name());
      product.setWtcProductName(WTCProductName.BC.getValue());
      product.setProduct(ProductCode.BC.getValue());
      productList.add(product);
    }
    if ((lsi >= LsiRange.C_Range.start && lsi <= LsiRange.C_Range.end) &&
        (rsi >= RsiRange.C_Range.start && rsi <= RsiRange.C_Range.end)) {
      Product product = new Product();
      product.setCode(WTCProductName.C.name());
      product.setWtcProductName(WTCProductName.C.getValue());
      product.setProduct(ProductCode.C.getValue());
      productList.add(product);
    }
    if ((lsi >= LsiRange.D_Range.start && lsi <= LsiRange.D_Range.end) &&
        (rsi >= RsiRange.D_Range.start && rsi <= RsiRange.D_Range.end)) {
      Product product = new Product();
      product.setCode(WTCProductName.D.name());
      product.setWtcProductName(WTCProductName.D.getValue());
      product.setProduct(ProductCode.D.getValue());
      productList.add(product);
    }
    return productList;
  }

  @Override
  public double calculateSaturationPh(final double tds,
                                      final double temp,
                                      final double calcium,
                                      final double alkalinity) {
    double factorA = (-0.1083 + 0.0446 * Math.log(tds));
    double factorB = 3.0801 * Math.exp(-0.0056 * temp);
    double factorC = -0.4022 + 0.4353 * Math.log(calcium);
    double factorD = -0.0002 + 0.4344 * Math.log(alkalinity);
    return Math.round(((9.3 + factorA + factorB) - (factorC + factorD)) * 100.0) / 100.0;
  }

}

enum ProductCode {
  A("PBTC + HEDP + AZOLE + COPOLYMER"),
  B("HEDP + AZOLE + COPOLYMER"),
  BC("PBTC / HEDP + ZINC + AZOLE + COPOLYMER"),
  C("HPA + ZINC + AZOLE + COPOLYMER"),
  D("ZINC / MOLYBDATE + AZOLE");

  private final String value;

  ProductCode(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}

enum WTCProductName {
  A("WTC 3228"),
  B("WTC 3227"),
  BC("WTC 3225"),
  C("WTC 3226"),
  D("WTC 3200");

  private final String value;

  WTCProductName(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}

enum LsiRange {
  A_Range(2.0, 3.0),
  B_Range(1.0, 2.0),
  BC_Range(-0.5, 0.5),
  C_Range(-2.0, -1.0),
  D_Range(-3.0, -2.0);

  double start;
  double end;

  LsiRange(final double start, final double end) {
    this.start = start;
    this.end = end;
  }
}

enum RsiRange {
  A_Range(3.0, 4.0),
  B_Range(4.0, 5.0),
  BC_Range(5.5, 7.0),
  C_Range(8.0, 9.0),
  D_Range(9.0, 10.0);

  double start;
  double end;

  RsiRange(final double start, final double end) {
    this.start = start;
    this.end = end;
  }
}


enum CaCO3FormationPotential {
  LSI_RSI_EXTREME_PRECIPITATION("Extremely high precipitation potential"),
  LSI_RSI_VERY_HIGH_PRECIPITATION("Very high precipitation potential"),
  LSI_RSI_HIGH_PRECIPITATION("High precipitation potential"),
  LSI_RSI_MODERATE_PRECIPITATION("Moderate precipitation potential"),
  LSI_RSI_SLIGHT_PRECIPITATION("Slight precipitation potential"),
  LSI_RSI_NO_PRECIPITATION("No precipitation potential (solid/liquid equilibrium)"),
  LSI_RSI_VERY_SLIGHT_DISSOLUTION("Very slight dissolution potential"),
  LSI_RSI_SLIGHT_DISSOLUTION("Slight dissolution potential"),
  LSI_RSI_MODERATE_DISSOLUTION("Moderate dissolution potential"),
  LSI_RSI_STRONG_DISSOLUTION("Strong dissolution potential"),
  LSI_RSI_VERY_STRONG_DISSOLUTION("Very strong dissolution potential");

  final String potential;

  CaCO3FormationPotential(final String potential) {
    this.potential = potential;
  }
}

enum LsiRsiCaCO3FormationPotential {
  LSI_RSI_EXTREME_PRECIPITATION(3.0, 3.0),
  LSI_RSI_VERY_HIGH_PRECIPITATION(2.0, 4.0),
  LSI_RSI_HIGH_PRECIPITATION(1.0, 5.0),
  LSI_RSI_MODERATE_PRECIPITATION(0.5, 5.5),
  LSI_RSI_SLIGHT_PRECIPITATION(0.2, 5.8),
  LSI_RSI_NO_PRECIPITATION(0.0, 6.0),
  LSI_RSI_VERY_SLIGHT_DISSOLUTION(-0.2, 6.5),
  LSI_RSI_SLIGHT_DISSOLUTION(-0.5, 7.0),
  LSI_RSI_MODERATE_DISSOLUTION(-1.0, 8.0),
  LSI_RSI_STRONG_DISSOLUTION(-2.0, 9.0),
  LSI_RSI_VERY_STRONG_DISSOLUTION(-3.0, 10.0);

  final double lsi;
  final double rsi;

  LsiRsiCaCO3FormationPotential(final double lsi, final double rsi) {
    this.lsi = lsi;
    this.rsi = rsi;
  }

}