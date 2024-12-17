package git.pancitox77.classes;

import com.google.gson.JsonObject;

public interface Jsonizable<T> {
    JsonObject toJson();
    T fromJson(JsonObject object);
}
