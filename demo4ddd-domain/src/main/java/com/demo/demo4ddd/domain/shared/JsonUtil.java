package com.demo.demo4ddd.domain.shared;

import static com.demo.demo4ddd.domain.shared.DomainExceptionCode.CAST_OBJECT_TO_JSON_FAIL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class JsonUtil {

    private static volatile ObjectMapper objectMapper;

    private JsonUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            synchronized (JsonUtil.class) {
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    objectMapper.registerModule(new Jdk8Module());
                    objectMapper
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    JavaTimeModule module = new JavaTimeModule();
                    module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    objectMapper.registerModule(module);
                }
            }
        }
        return objectMapper;
    }

    public static String toJSONString(Object object, String defaultReturn) {
        String res;
        try {
            res = toJSONString(object);
        } catch (Exception e) {
            return defaultReturn;
        }
        return res == null ? defaultReturn : res;
    }

    public static String toJSONString(Object object) {
        try {
            return object == null ? null :
                    getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }

    public static Map<String, String> toMap(String json, Map<String, String> defaultReturn) {
        Map<String, String> res;
        try {
            res = toMap(json);
        } catch (Exception e) {
            return defaultReturn;
        }
        return res == null ? defaultReturn : res;
    }

    public static Map<String, String> toMap(String json) {
        try {
            return StringUtils.isEmpty(json) ? null :
                    getObjectMapper().readValue(json, new TypeReference<Map<String, String>>() {
                    });
        } catch (IOException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }

    public static <T> Map<String, T> toMap(String json, TypeReference<Map<String, T>> type) {
        try {
            return StringUtils.isEmpty(json) ? null :
                    getObjectMapper().readValue(json, type);
        } catch (IOException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }


    public static <K, V> Map<K, V> toCustomMap(String json, TypeReference<Map<K, V>> type) {
        try {
            return StringUtils.isEmpty(json) ? null :
                    getObjectMapper().readValue(json, type);
        } catch (IOException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }

    public static Map<String, String> objToMap(Object object) {
        String json = toJSONString(object, "{}");
        return toMap(json);
    }

    public static <T> List<T> toList(String json, TypeReference<List<T>> type) {
        try {
            return StringUtils.isEmpty(json) ? null :
                    getObjectMapper().readValue(json, type);
        } catch (IOException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }


    public static List<String> toList(String json) {
        try {
            return StringUtils.isEmpty(json) ? null :
                    getObjectMapper().readValue(json, new TypeReference<List<String>>() {
                    });
        } catch (IOException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }

    public static <T> List<T> toList(String json, TypeReference<List<T>> type,
            List<T> defaultReturn) {
        List<T> res;
        try {
            res = StringUtils.isEmpty(json) ? null :
                    getObjectMapper().readValue(json, type);
        } catch (IOException e) {
            return defaultReturn;
        }

        return res == null ? defaultReturn : res;
    }


    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return StringUtils.isEmpty(json) ? null
                    : getObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz, T defaultValue) {
        try {
            return StringUtils.isEmpty(json) ? null
                    : getObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            return defaultValue;
        }
    }


    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return StringUtils.isEmpty(json) ? null
                    : getObjectMapper().readValue(json, typeReference);
        } catch (IOException e) {
            throw new DomainException(CAST_OBJECT_TO_JSON_FAIL, e);
        }
    }


}
