package com.demo.demo4ddd.domain.shared;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;


@Data
public class JsonMap<T extends JsonFields> implements ValueObject<JsonMap> {

    private static final long serialVersionUID = 5775611792572444377L;

    @NonNull
    private final Map<String, String> data;

    public JsonMap(Map<String, String> data) {
        this.data = new HashMap<>(data);
    }

    public JsonMap(String json) {
        this.data = JsonUtil.toMap(json);
    }

    @JsonIgnore
    public String getJsonString() {
        return JsonUtil.toJSONString(data);
    }

    public void put(T field, String value) {
        data.put(field.getKeyName(), value);
    }

    public String get(String key) {
        return data.get(key);
    }


    public String get(T field) {
        return data.get(field.getKeyName());
    }

    @Override
    public boolean sameValueAs(JsonMap other) {
        return other != null &&
                data.equals(other.data);
    }
}
