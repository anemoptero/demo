package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class CurrencyRate {
	
	private String code;
	private String symbol;
	private String rate;
	private String description;
	@JsonAlias(value = {"rate_float"})
	private String rateFloat;
}
