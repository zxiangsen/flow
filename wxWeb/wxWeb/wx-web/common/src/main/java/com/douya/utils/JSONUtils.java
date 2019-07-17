package com.douya.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * JSON
 */
public class JSONUtils extends ObjectMapper {

    private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    private static JSONUtils mapper;

    public JSONUtils() {
        this(JsonInclude.Include.NON_NULL);
    }

    public JSONUtils(JsonInclude.Include include) {
        // 设置输出时包含属性的风格
        if (include != null) {
            this.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){
            @Override
            public void serialize(Object value, JsonGenerator jgen,
                                  SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                jgen.writeString("");
            }
        });
        // 进行HTML解码。
        this.registerModule(new SimpleModule().addSerializer(String.class, new JsonSerializer<String>(){
            @Override
            public void serialize(String value, JsonGenerator jgen,
                                  SerializerProvider provider) throws IOException,
                    JsonProcessingException {
                jgen.writeString(StringEscapeUtils.unescapeHtml4(value));
            }
        }));
        // 设置时区
        this.setTimeZone(TimeZone.getDefault());//getTimeZone("GMT+8:00")
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static JSONUtils getObjectMapper() {
        if (mapper == null){
            mapper = new JSONUtils().enableSimple();
        }
        return mapper;
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static JSONUtils nonDefaultMapper() {
        if (mapper == null){
            mapper = new JSONUtils(JsonInclude.Include.NON_DEFAULT);
        }
        return mapper;
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {
        try {
            return this.writeValueAsString(object);
        } catch (IOException e) {
            if(logger.isWarnEnabled()){
                logger.warn("write to json string error:" + object, e);
            }
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     *
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     * @see #fromJson(String, Class)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return this.readValue(jsonString, clazz);
        } catch (IOException e) {
            if(logger.isWarnEnabled()){
                logger.warn("parse json string error:" + jsonString, e);
            }
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
     * @see #createCollectionType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) this.readValue(jsonString, javaType);
        } catch (IOException e) {
            if(logger.isWarnEnabled()){
                logger.warn("parse json string error:" + jsonString, e);
            }
            return null;
        }
    }

    /**
     * 構造泛型的Collection Type如:
     * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     */
    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return this.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    @SuppressWarnings("unchecked")
    public <T> T update(String jsonString, T object) {
        try {
            return (T) this.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            if(logger.isWarnEnabled()){
                logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
            }
        } catch (IOException e) {
            if(logger.isWarnEnabled()){
                logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
            }
        }
        return null;
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum,
     * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
     * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
     */
    public JSONUtils enableEnumUseToString() {
        this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return this;
    }

    /**
     * 允许单引号
     * 允许不带引号的字段名称
     */
    public JSONUtils enableSimple() {
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return this;
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return this;
    }

    /**
     * 对象转换为JSON字符串
     * @param object
     * @return
     */
    public static String toString(Object object){
        return JSONUtils.getObjectMapper().toJson(object);
    }

    /**
     * JSON字符串转换为对象
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T parse(String jsonString, Class<T> clazz){
        return JSONUtils.getObjectMapper().fromJson(jsonString, clazz);
    }

    /**
     * 将对象转换为json字符串
     * @param object
     * @return
     */
    public static String parseObject2JsonString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 解析json数组字符串为集合
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 判断字符串是否能转为Json
     * @param jsonString
     * @return
     */
    public static boolean isJson(String jsonString){
        if (StringUtils.isEmpty(jsonString)) {
            return false;
        }
        try {
            JSONUtils.getObjectMapper().readValue(jsonString, HashMap.class);
        } catch (Exception e1) {
            try {
                JSONUtils.getObjectMapper().readValue(jsonString, ArrayList.class);
            } catch (Exception e2) {
                return false;
            }
        }
        return true;
    }



    @Override
    protected ObjectReader _newReader(DeserializationConfig config) {
        ObjectReader obj = super._newReader(config);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

    @Override
    protected ObjectReader _newReader(DeserializationConfig config, JavaType valueType, Object valueToUpdate, FormatSchema schema, InjectableValues injectableValues) {
        ObjectReader obj = super._newReader(config, valueType, valueToUpdate, schema, injectableValues);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

    @Override
    protected ObjectWriter _newWriter(SerializationConfig config) {
        ObjectWriter obj = super._newWriter(config);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

    @Override
    protected ObjectWriter _newWriter(SerializationConfig config, FormatSchema schema) {
        ObjectWriter obj = super._newWriter(config, schema);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

    @Override
    protected ObjectWriter _newWriter(SerializationConfig config, JavaType rootType, PrettyPrinter pp) {
        ObjectWriter obj = super._newWriter(config, rootType, pp);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

    @Override
    protected Object _convert(Object fromValue, JavaType toValueType) throws IllegalArgumentException {
        Object obj = super._convert(fromValue, toValueType);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

    @Override
    protected Object _readValue(DeserializationConfig cfg, JsonParser p, JavaType valueType) throws IOException {
        Object obj = super._readValue(cfg, p, valueType);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

    @Override
    protected Object _readMapAndClose(JsonParser p0, JavaType valueType) throws IOException {
        Object obj = super._readMapAndClose(p0, valueType);
        _typeFactory.clearCache();  //清除缓存（防止返回属性丢失）
        return obj;
    }

}
