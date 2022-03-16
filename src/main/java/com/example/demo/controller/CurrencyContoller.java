package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CurrencyDao;
import com.example.demo.entity.Currency;

@RestController
@RequestMapping("currency")
public class CurrencyContoller {

	@Autowired
	private CurrencyDao dao;

	@GetMapping("search")
	public ResponseEntity<Object> get() {
		return new ResponseEntity<>(this.dao.findAll(), HttpStatus.OK);
	}

	@PostMapping("add")
	public ResponseEntity<Object> post(@RequestBody Currency currency) {
		this.dao.save(currency);
		return new ResponseEntity<>("add success", HttpStatus.OK);
	}

	@PutMapping("update")
	public ResponseEntity<Object> put(@RequestBody Currency currency) {
		this.dao.save(currency);
		return new ResponseEntity<>("update success", HttpStatus.OK);
	}

	@DeleteMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody Currency currency) {
		this.dao.delete(currency);
		return new ResponseEntity<>("delect success", HttpStatus.OK);
	}

}
