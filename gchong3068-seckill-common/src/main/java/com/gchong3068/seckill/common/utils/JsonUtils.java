package com.gchong3068.seckill.common.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

/**
 * @author: gchong3068
 * @date: 2026年04月11日21:25
 * @Version: 1.0
 * @Description: JSON 工具类
 */
public class JsonUtils {


    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        OBJECT_MAPPER.registerModules(new JavaTimeModule());
    }

    /*
     * 将对象转化为JSON字符串
     * @author gchong3068
     * @date 2026/4/11 21:28
     * @param obj
     * @return java.lang.String
     */
    @SneakyThrows
    public static String toJsonString(Object obj){
        return OBJECT_MAPPER.writeValueAsString(obj);
    }


    /**
     * 初始化 ObjectMapper，供 JacksonConfig 调用，统一序列化行为
     *
     * @param objectMapper
     */
    public static void init(ObjectMapper objectMapper) {
        OBJECT_MAPPER = objectMapper;
    }

}
