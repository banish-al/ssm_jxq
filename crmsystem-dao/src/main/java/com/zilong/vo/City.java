package com.zilong.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * 表名 :  city<br/>
 */ 
@JsonIgnoreProperties(value = "handler")
public class City implements Serializable {

	private Integer id;
	private String code;
	private String name;
	private String provincecode;

	public City() {
		super();
	}
	public City(Integer id,String code,String name,String provincecode) {
		this.id=id;
		this.code=code;
		this.name=name;
		this.provincecode=provincecode;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setCode(String code){
		this.code=code;
	}
	public String getCode(){
		return code;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setProvincecode(String provincecode){
		this.provincecode=provincecode;
	}
	public String getProvincecode(){
		return provincecode;
	}
	@Override
	public String toString() {
		return "city[" + 
			"id=" + id + 
			", code=" + code + 
			", name=" + name + 
			", provincecode=" + provincecode + 
			"			]";
	}
}

