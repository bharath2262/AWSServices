package framework;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;


public class JSONUtils {

    public static Object JSONFilter(String JSON, String JSONPathExpression) {
        return JsonPath.read(JSON, JSONPathExpression);
    }

    public static JSONObject generateJSONObject(String JSONString) {
        return new JSONObject(JSONString);
    }

   }