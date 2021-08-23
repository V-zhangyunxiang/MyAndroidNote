package com.owl.android_simple;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/7/15 15:29
 */
class LocationDeserializer implements JsonDeserializer<Double> {

  @Override
  public Double deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    try {
      if (jsonElement.getAsJsonPrimitive().isString()) {
        return Double.valueOf(jsonElement.getAsString());
      } else {
        return jsonElement.getAsDouble();
      }
    } catch (Exception e) {
      return 0.0;
    }
  }
}
