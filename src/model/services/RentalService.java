package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerday;
	private Double pricePerHour;
	
	private taxService taxservice;

	public RentalService(Double pricePerday, Double pricePerHour, taxService taxservice) {
		this.pricePerday = pricePerday;
		this.pricePerHour = pricePerHour;
		this.taxservice = taxservice;
	}

	public Double getPricePerday() {
		return pricePerday;
	}

	public void setPricePerday(Double pricePerday) {
		this.pricePerday = pricePerday;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public taxService getTaxservice() {
		return taxservice;
	}

	public void setTaxservice(taxService taxservice) {
		this.taxservice = taxservice;
	}
	
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1)/ 1000 / 60 / 60;
		
		double basicPayment;
		if(hours < 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerday;
		}
		
		double tax = taxservice.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}

}
