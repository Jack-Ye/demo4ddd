package com.demo.demo4ddd.domain.shared;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Data
public class JsonArray<T extends JsonFields> implements ValueObject<JsonArray> {

    private static final long serialVersionUID = 5775611792572444377L;

    @NonNull
    private final List<JsonMap<T>> data;

    public JsonArray(List<JsonMap<T>> data) {
        this.data = data;
    }

    public JsonArray(String json) {
        List<Map<String, String>> maps = JsonUtil.toList(json, new TypeReference<List<Map<String, String>>>() {
        });
        if (maps == null) {
            data = new ArrayList<>();
        } else {
            data = maps.stream()
                    .map((Function<Map<String, String>, JsonMap<T>>) JsonMap::new)
                    .collect(Collectors.toList());
        }
    }

    @JsonIgnore
    public String getJsonString() {
        List<Map<String, String>> content = data.stream().map(JsonMap::getData).collect(Collectors.toList());
        return JsonUtil.toJSONString(content);
    }

    public void add(JsonMap<T> jsonMap) {
        data.add(jsonMap);
    }


    @Override
    public boolean sameValueAs(JsonArray other) {
        return other != null &&
                data.equals(other.data);
    }
}
