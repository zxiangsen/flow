package com.douya.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Created by lb on 2018/4/4.
 */
public class MyObjectMapper extends ObjectMapper {
	/**
	 * 利用该实体类作为 MappingJackson2HttpMessageConverter 的objectMapper变量，对Long类型转换成String类型
	 */
	public MyObjectMapper() {
		super();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		registerModule(simpleModule);
	}
}