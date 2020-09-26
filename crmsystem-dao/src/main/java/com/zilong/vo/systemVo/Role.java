package com.zilong.vo.systemVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * 表名 :  role<br/>
 * 表注释 : 角色表
 */ 
@JsonIgnoreProperties(value = "handler")
public class Role implements Serializable {

	/**角色id*/
	private Integer roleId;
	/**角色称呼*/
	private String roleName;

	public Role() {
		super();
	}
	public Role(Integer roleId,String roleName) {
		this.roleId=roleId;
		this.roleName=roleName;
	}
	/**设置"角色id"*/
	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}
	/**获取"角色id"*/
	public Integer getRoleId(){
		return roleId;
	}
	/**设置"角色称呼"*/
	public void setRoleName(String roleName){
		this.roleName=roleName;
	}
	/**获取"角色称呼"*/
	public String getRoleName(){
		return roleName;
	}
	@Override
	public String toString() {
		return "role[" + 
			"roleId=" + roleId + 
			", roleName=" + roleName + 
			"			]";
	}
}

