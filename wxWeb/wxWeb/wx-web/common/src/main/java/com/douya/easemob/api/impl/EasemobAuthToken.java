package com.douya.easemob.api.impl;


import com.douya.easemob.api.AuthTokenAPI;
import com.douya.easemob.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
