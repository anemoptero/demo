package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.controller.BpiController;
import com.example.demo.dao.CurrencyDao;
import com.example.demo.entity.Coindesk;
import com.example.demo.entity.Currency;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BpiControllerTest {

	@InjectMocks
	BpiController bpiController;

	@Mock
	CurrencyDao currencyDao;
	@Mock
	RestTemplate restTemplate;

	@Test
	public void testCallApi() throws ParseException, JsonMappingException, JsonProcessingException {
		this.before();
		this.show(this.bpiController.callApi());
	}

	@Test
	public void testGet() throws ParseException, JsonMappingException, JsonProcessingException {
		this.before();
		List<Currency> list = new ArrayList<>();
		list.add(new Currency("USD", "美元"));
		list.add(new Currency("GBP", "英鎊"));
		list.add(new Currency("EUR", "歐元"));
		when(currencyDao.findAll()).thenReturn(list);
		this.show(this.bpiController.get());
	}
	
	private void before() throws JsonMappingException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		Coindesk coindesk = om.readValue("{\"time\":{\"updated\":\"Mar 16, 2022 13:23:00 UTC\",\"updatedISO\":\"2022-03-16T13:23:00+00:00\",\"updateduk\":\"Mar 16, 2022 at 13:23 GMT\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"40,415.2817\",\"description\":\"United States Dollar\",\"rate_float\":40415.2817},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"30,870.4450\",\"description\":\"British Pound Sterling\",\"rate_float\":30870.445},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"36,777.7042\",\"description\":\"Euro\",\"rate_float\":36777.7042}}}", Coindesk.class);
		ResponseEntity<Coindesk> resp = new ResponseEntity<>(coindesk, HttpStatus.OK);
		when(restTemplate.getForEntity("https://api.coindesk.com/v1/bpi/currentprice.json", Coindesk.class)).thenReturn(resp);
	}

	private void show(ResponseEntity<Object> resp) {
		log.info("HttpStatus:{}", resp.getStatusCodeValue());
		log.info("responseBody:{}", resp.getBody());
	}
}
