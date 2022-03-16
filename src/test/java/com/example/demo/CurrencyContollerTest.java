package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.CurrencyContoller;
import com.example.demo.dao.CurrencyDao;
import com.example.demo.entity.Currency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CurrencyContollerTest {

	@InjectMocks
	private CurrencyContoller currencyContoller;

	@Mock
	CurrencyDao currencyDao;

	@Test
	public void testGet() {

		// 1. 測設 取得幣別 mapping 資料
		{
			this.show(currencyContoller.get());
		}
	}

	@Test
	public void testPost() {
		// 2. 測試 新增一筆幣別 mapping 並查看DB資料
		{
			Currency currency = new Currency("USD", "美元");
			when(currencyDao.save(any(Currency.class))).thenReturn(currency);
			
			this.show(currencyContoller.post(currency));
		}
	}

	@Test
	public void testPut() {
		// 3. 測試 修改一筆幣別 mapping 並查看DB資料
		{
			Currency currency = new Currency("USD", "美金");
			when(currencyDao.save(any(Currency.class))).thenReturn(currency);
			
			this.show(currencyContoller.put(currency));
		}
	}

	@Test
	public void testDelete() {
		// 4. 測試 刪除一筆幣別 mapping 並查看DB資料
		{
			Currency currency = new Currency("USD", null);
			this.show(currencyContoller.delete(currency));
		}
	}

	private void show(ResponseEntity<Object> resp) {
		log.info("HttpStatus:{}", resp.getStatusCodeValue());
		log.info("responseBody:{}", resp.getBody());
	}
}
