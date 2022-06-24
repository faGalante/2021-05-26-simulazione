package it.polito.tdp.yelp.model;

public class Adiacenza {
	
	private Business business1 ;
	private Double media1 ;
	private Business business2 ;
	private Double media2 ;
	
	public Adiacenza(Business business1, Double media1, Business business2, Double media2) {
		super();
		this.business1 = business1;
		this.media1 = media1;
		this.business2 = business2;
		this.media2 = media2;
	}

	public Business getBusiness1() {
		return business1;
	}

	public void setBusiness1(Business business1) {
		this.business1 = business1;
	}

	public Double getMedia1() {
		return media1;
	}

	public void setMedia1(Double media1) {
		this.media1 = media1;
	}

	public Business getBusiness2() {
		return business2;
	}

	public void setBusiness2(Business business2) {
		this.business2 = business2;
	}

	public Double getMedia2() {
		return media2;
	}

	public void setMedia2(Double media2) {
		this.media2 = media2;
	}

	@Override
	public String toString() {
		return "Adiacenza [business1=" + business1 + ", media1=" + media1 + ", business2=" + business2 + ", media2="
				+ media2 + "]";
	}
	
	

}
