import java.util.List;
import java.util.Map;

/**
 * To Test ParameterizedType
 * @author Joseph.Liu
 */
public class ParameterizedTypeTestBean<T> {

    List<String> stringList;

    List<T> tList;

    List<? extends Number> wildList;

    List alist;

    Map<String,Long> stringMap;

    Map<T,T> tMap;

    Map<? super Integer,?> wildMap;

    Map aMap;

    Map.Entry<Long,Short> longEntry;

    Map.Entry<T,T> tEntry;

    T data;
}
