import java.util.Objects;

public class IntegerUtil {


    public static Integer defaultIsZero(Integer value) {
        return Objects.isNull(value) ? 0 : value;
    }
}
