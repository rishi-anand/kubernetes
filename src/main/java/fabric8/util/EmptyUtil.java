package fabric8.util;

import java.util.Collection;
import java.util.Map;


public class EmptyUtil {
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return m == null || m.size() == 0;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    public static boolean isNotEmpty(Map<?, ?> m) {
        return !isEmpty(m);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    public static boolean isAnyNull(Object... objects) {
        for (Object o : objects) {
            if (isNull(o)) {
                return true;
            }
        }
        return false;
    }
}
