package com.douya.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by lb on 2018/4/4.
 */
public class LongJsonSerializer extends JsonSerializer<Long> {
	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		String text = (value == null ? null : String.valueOf(value));
		if (text != null) {
			gen.writeString(text);
		}
	}
}