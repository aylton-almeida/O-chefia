import org.json.JSONArray;
import org.json.JSONObject;

public interface JsonFormatter {
    public JSONObject toJson();

    public default JSONArray toJSONArray(){
        return new JSONArray().put(toJson());
    }

}
