package com.zilong.vo.systemVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * 表名 :  jurisdiction<br/>
 * 表注释 : 权限表
 */ 
@JsonIgnoreProperties(value = "handler")
public class Jurisdiction implements Serializable {

	/**菜单id*/
	private Integer menuId;
	/**角色id*/
	private Integer roleId;

	public Jurisdiction() {
		super();
	}
	public Jurisdiction(Integer menuId,Integer roleId) {
		this.menuId=menuId;
		this.roleId=roleId;
	}
	/**设置"菜单id"*/
	public void setMenuId(Integer menuId){
		this.menuId=menuId;
	}
	/**获取"菜单id"*/
	public Integer getMenuId(){
		return menuId;
	}
	/**设置"角色id"*/
	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}
	/**获取"角色id"*/
	public Integer getRoleId(){
		return roleId;
	}
	@Override
	public String toString() {
		return "jurisdiction[" + 
			"menuId=" + menuId + 
			", roleId=" + roleId + 
			"			]";
	}
}

