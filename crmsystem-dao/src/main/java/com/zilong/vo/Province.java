package com.zilong.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * 表名 :  province<br/>
 */ 
@JsonIgnoreProperties(value = "handler")
public class Province implements Serializable {

	private Integer id;
	private String code;
	private String name;

	public Province() {
		super();
	}
	public Province(Integer id,String code,String name) {
		this.id=id;
		this.code=code;
		this.name=name;
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
	@Override
	public String toString() {
		return "province[" + 
			"id=" + id + 
			", code=" + code + 
			", name=" + name + 
			"			]";
	}
}

