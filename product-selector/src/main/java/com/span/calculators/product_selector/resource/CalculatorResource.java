package com.span.calculators.product_selector.resource;

import com.span.calculators.product_selector.model.ProductSelection;
import com.span.calculators.product_selector.model.RequestInput;
import com.span.calculators.product_selector.service.LsiRsiCalculatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CalculatorResource {
  @Autowired
  private LsiRsiCalculatorInterface lsiRsiCalculatorInterface;

  @RequestMapping(value = "/product-selector", method = RequestMethod.GET)
  public String productselectionform() {
    return "productselection";
  }

  @RequestMapping(value = "/product-selector", method = RequestMethod.POST)
  public String productselector(HttpServletRequest request, Model model) {
    double tds = Double.parseDouble(request.getParameter("tds"));
    double temperature = Double.parseDouble(request.getParameter("temperature"));
    double calcium = Double.parseDouble(request.getParameter("calcium"));
    double alkalinity = Double.parseDouble(request.getParameter("alkalinity"));
    double ph = Double.parseDouble(request.getParameter("ph"));

    RequestInput requestInput = new RequestInput(tds, temperature, calcium, alkalinity, ph);
    ProductSelection productSelection = lsiRsiCalculatorInterface.selectProductInfo(requestInput);
    model.addAttribute("saturation_ph", productSelection.getSaturationPh());
    model.addAttribute("lsi", productSelection.getLsiRsiPotential().getLsi());
    model.addAttribute("rsi", productSelection.getLsiRsiPotential().getRsi());
    if (productSelection.getLsiRsiPotential().getCaco3FormationPotential() != null) {
      model.addAttribute("formation_potential", productSelection.getLsiRsiPotential().getCaco3FormationPotential());
    } else {
      model.addAttribute("formation_potential", "No Match Found");
    }

    if (productSelection.getProducts() != null && productSelection.getProducts().size() > 0) {
      model.addAttribute("product_name", productSelection.getProducts().get(0).getWtcProductName());
    } else {
      model.addAttribute("product_name", "No Match Found");
    }

    model.addAttribute("input_tds", tds);
    model.addAttribute("input_temperature", temperature);
    model.addAttribute("input_calcium", calcium);
    model.addAttribute("input_alkalinity", alkalinity);
    model.addAttribute("input_ph", ph);

    return "productselection";
  }
}
