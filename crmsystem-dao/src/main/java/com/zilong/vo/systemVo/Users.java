package com.zilong.vo.systemVo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;

/**
 * 表名 :  users<br/>
 * 表注释 : 员工表
 */ 
@JsonIgnoreProperties(value = "handler")
public class Users implements Serializable {
	/**员工id*/
	private Integer userId;
	/**员工编号*/
	private String userNumber;
	/**员工姓名*/
	private String userName;
	/**员工性别*/
	private String userSex;
	/**员工年龄*/
	private Integer userAge;
	/**电话号码*/
	private String userPhone;
	/**登录密码*/
	private String userPassword;
	/**联系地址*/
	private String userAddr;
	/**员工状态*/
	private String userState;

	private List<Role> roles;

	public Users() {
		super();
	}

	public Users(Integer userId, String userNumber, String userName, String userSex, Integer userAge, String userPhone, String userPassword, String userAddr, String userState, List<Role> roles) {
		this.userId = userId;
		this.userNumber = userNumber;
		this.userName = userName;
		this.userSex = userSex;
		this.userAge = userAge;
		this.userPhone = userPhone;
		this.userPassword = userPassword;
		this.userAddr = userAddr;
		this.userState = userState;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Users{" +
				"userId=" + userId +
				", userNumber='" + userNumber + '\'' +
				", userName='" + userName + '\'' +
				", userSex='" + userSex + '\'' +
				", userAge=" + userAge +
				", userPhone='" + userPhone + '\'' +
				", userPassword='" + userPassword + '\'' +
				", userAddr='" + userAddr + '\'' +
				", userState='" + userState + '\'' +
				", roles=" + roles +
				'}';
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}

