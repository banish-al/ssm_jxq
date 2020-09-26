package com.zilong.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * 表名 :  area<br/>
 */ 
@JsonIgnoreProperties(value = "handler")
public class Area implements Serializable {

	private Integer id;
	private String code;
	private String name;
	private String citycode;

	public Area() {
		super();
	}
	public Area(Integer id,String code,String name,String citycode) {
		this.id=id;
		this.code=code;
		this.name=name;
		this.citycode=citycode;
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
	public void setCitycode(String citycode){
		this.citycode=citycode;
	}
	public String getCitycode(){
		return citycode;
	}
	@Override
	public String toString() {
		return "area[" + 
			"id=" + id + 
			", code=" + code + 
			", name=" + name + 
			", citycode=" + citycode + 
			"			]";
	}
}

