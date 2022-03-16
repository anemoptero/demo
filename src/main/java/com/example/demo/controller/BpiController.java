package com.example.demo.controller;

import java.text.ParseException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dao.CurrencyDao;
import com.example.demo.entity.Coindesk;
import com.example.demo.entity.Currency;
import com.example.demo.entity.CurrencyRate;
import com.example.demo.entity.CustomData;

@RestController
@RequestMapping("bpi")
public class BpiController {

	@Autowired
	private CurrencyDao dao;
	private RestTemplate restTemplate;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "javascript")));
		this.restTemplate.getMessageConverters().add(converter);
	}

	@GetMapping("callApi")
	public ResponseEntity<Object> callApi() throws ParseException {
		return new ResponseEntity<>(this.call(), HttpStatus.OK);
	}
	
	@GetMapping("search")
	public ResponseEntity<Object> get() throws ParseException {
		List<CustomData> result = new ArrayList<>();

		ResponseEntity<Coindesk> resp = this.call();
		
		if (resp.hasBody()) {
			Coindesk coindesk = resp.getBody();
			Map<String, CurrencyRate> bpi = coindesk.getBpi();

			List<Currency> list = this.dao.findAll();
			for (Currency currency : list) {
				if (bpi.containsKey(currency.getName())) {
					CustomData data = new CustomData();
					data.setName(currency.getName());
					data.setChineseName(currency.getChineseName());
					
					CurrencyRate currencyRate = bpi.get(currency.getName());
					data.setRate(currencyRate.getRate());
					data.setRateFloat(currencyRate.getRateFloat());
					data.setUpdateTime(this.convert(coindesk.getTime().getUpdatedISO()));
					
					result.add(data);
				}
			}
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	public ResponseEntity<Coindesk> call() {
		return this.restTemplate.getForEntity("https://api.coindesk.com/v1/bpi/currentprice.json", Coindesk.class);
	}
	
	private Date convert(String time) throws ParseException {
		OffsetDateTime odt = OffsetDateTime.parse( "2010-01-01T12:00:00+01:00" );
		Instant instant = odt.toInstant();
		return Date.from(instant);
	}
}
