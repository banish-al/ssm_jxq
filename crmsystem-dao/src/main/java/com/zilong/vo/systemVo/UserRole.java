package com.zilong.vo.systemVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * 表名 :  user_role<br/>
 * 表注释 : 员工角色表
 */ 
@JsonIgnoreProperties(value = "handler")
public class UserRole implements Serializable {

	private Integer userRoleId;
	/**员工id*/
	private Users users;
	/**角色id*/
	private Role role;

	public UserRole() {
		super();
	}
	public UserRole(Integer userRoleId,Users users,Role role) {
		this.userRoleId=userRoleId;
		this.users=users;
		this.role=role;
	}
	public void setUserRoleId(Integer userRoleId){
		this.userRoleId=userRoleId;
	}
	public Integer getUserRoleId(){
		return userRoleId;
	}
	public void setUsers(Users users){
		this.users=users;
	}
	public Users getUsers(){
		return users;
	}
	public void setRole(Role role){
		this.role=role;
	}
	public Role getRole(){
		return role;
	}
	@Override
	public String toString() {
		return "user_role[" + 
			"userRoleId=" + userRoleId + 
			", users=" + users + 
			", role=" + role + 
			"			]";
	}
}

