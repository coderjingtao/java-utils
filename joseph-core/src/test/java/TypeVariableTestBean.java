import java.io.Serializable;
import java.util.List;

/**
 * @author Joseph.Liu
 */
public class TypeVariableTestBean<K extends Number & Serializable, V> {
    private K key;
    private V value;

    private V[] values;
    private List<K> keys;

    private String str;
}
