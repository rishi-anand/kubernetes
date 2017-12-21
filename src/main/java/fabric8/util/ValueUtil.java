package fabric8.util;

import java.util.Map;
import java.util.function.Supplier;

public class ValueUtil {


    //Retrieves the value in object chaining avoiding nested null checks
    public static <V> V get(Supplier<V> objectValue)  {
        try {
            return objectValue.get();
        } catch (NullPointerException npe) {
            return null;
        }
    }

    public static String get(String key, Map<String, String>... maps) {
        if(key != null && maps != null) {
            for(Map<String, String> map : maps) {
                String value = map.get(key);
                if(value != null) {
                    return value;
                }
            }
        }
        return null;
    }
}
