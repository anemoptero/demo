package com.example.demo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomData {
	
	private String name;
	private String chineseName;
	private String rate;
	private String rateFloat;
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date updateTime;
}
