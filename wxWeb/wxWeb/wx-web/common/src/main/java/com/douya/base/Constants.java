package com.douya.base;


/**
 * 全局常量
 */
public enum Constants {
	/**
	 * aa
	 */
	NOT_LOGIN(202, "请重新登录"),
	NOT_REGISTER(203, "手机号码未注册"),
	EXIST_PHONE(204, "手机号码已注册"),
	PASSWORD_PHONE_ERROR(205, "手机号或密码错误"),
	VERIFICATION_CODE_ERROR(206, "验证码错误"),
	USER_STATUS_ERROR(207, "用户身份错误"),
	USER_NOT_EXIST(208, "用户不存在"),
	STOCK_NUMBER_OVERFLOW(300, "库存不足");

	public Integer code;
	public String msg;

	Constants(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
