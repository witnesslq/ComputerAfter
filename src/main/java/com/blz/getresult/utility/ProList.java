package com.blz.getresult.utility;

import java.util.List;

public class ProList {
	private String pro;
	private List<String> city;


	public ProList(String pro, List<String> city) {
		this.pro = pro;
		this.city = city;
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public List<String> getCity() {
		return city;
	}

	public void setCity(List<String> city) {
		this.city = city;
	}
}
