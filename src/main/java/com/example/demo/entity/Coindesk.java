package com.example.demo.entity;

import java.util.Map;

import lombok.Data;

@Data
public class Coindesk {
	private Time time;
	private String disclaimer;
	private String chartName;
	private Map<String, CurrencyRate> bpi;
}
