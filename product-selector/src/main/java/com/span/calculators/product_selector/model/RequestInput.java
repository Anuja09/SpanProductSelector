package com.span.calculators.product_selector.model;

public class RequestInput {
  private double tds;
  private double temp;
  private double calcium;
  private double alkalinity;
  private double ph;

  public RequestInput(final double tds,
                      final double temp,
                      final double calcium,
                      final double alkalinity,
                      final double ph) {
    this.tds = tds;
    this.temp = temp;
    this.calcium = calcium;
    this.alkalinity = alkalinity;
    this.ph = ph;
  }

  public double getTds() {
    return tds;
  }

  public void setTds(final double tds) {
    this.tds = tds;
  }

  public double getTemp() {
    return temp;
  }

  public void setTemp(final double temp) {
    this.temp = temp;
  }

  public double getCalcium() {
    return calcium;
  }

  public void setCalcium(final double calcium) {
    this.calcium = calcium;
  }

  public double getAlkalinity() {
    return alkalinity;
  }

  public void setAlkalinity(final double alkalinity) {
    this.alkalinity = alkalinity;
  }

  public double getPh() {
    return ph;
  }

  public void setPh(final double ph) {
    this.ph = ph;
  }

  @Override
  public String toString() {
    return "RequestInput{" +
           "tds=" +
           tds +
           ", temp=" +
           temp +
           ", calcium=" +
           calcium +
           ", alkalinity=" +
           alkalinity +
           ", ph=" +
           ph +
           '}';
  }
}
