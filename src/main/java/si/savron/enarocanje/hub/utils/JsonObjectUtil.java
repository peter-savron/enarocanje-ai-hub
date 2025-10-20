package si.savron.enarocanje.hub.utils;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class JsonObjectUtil {
    public static String getString(JsonObject object, String key) {
        if (object.get(key).getValueType() == JsonValue.ValueType.STRING) {
            return object.getString(key);
        }
        return null;
    }
    public static Integer getInteger(JsonObject object, String key) {
        if (object.get(key).getValueType() == JsonValue.ValueType.STRING) {
            return object.getInt(key);
        }
        return null;
    }
}
