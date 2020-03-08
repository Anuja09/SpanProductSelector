package com.span.calculators.product_selector.model;

public class LsiRsiPotential {
  private double lsi;
  private double rsi;
  private String caco3FormationPotential;

  public LsiRsiPotential(final double lsi, final double rsi, final String caco3FormationPotential) {
    this.lsi = lsi;
    this.rsi = rsi;
    this.caco3FormationPotential = caco3FormationPotential;
  }

  public LsiRsiPotential() {

  }

  public double getLsi() {
    return lsi;
  }

  public void setLsi(final double lsi) {
    this.lsi = lsi;
  }

  public double getRsi() {
    return rsi;
  }

  public void setRsi(final double rsi) {
    this.rsi = rsi;
  }

  public String getCaco3FormationPotential() {
    return caco3FormationPotential;
  }

  public void setCaco3FormationPotential(final String caco3FormationPotential) {
    this.caco3FormationPotential = caco3FormationPotential;
  }

  @Override
  public String toString() {
    return "LsiRsiPotential{" +
           "lsi=" +
           lsi +
           ", rsi=" +
           rsi +
           ", caco3FormationPotential='" +
           caco3FormationPotential +
           '\'' +
           '}';
  }
}
