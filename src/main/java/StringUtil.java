import org.apache.commons.lang3.StringUtils;

public class StringUtil {


    public static String defaultIsNone(String value) {
        return StringUtils.defaultIfBlank(value, "无");
    }
}
