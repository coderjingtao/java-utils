import com.joseph.core.util.ArrayUtil;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Joseph.Liu
 */
public class ArrayUtilTest {
    @Test
    public void testArrayAddAll(){
        Integer[] a = new Integer[]{1,2,3};
        Integer[] b = new Integer[]{4,5,6,7};
        Integer[] all = ArrayUtil.addAll(b, a);
        System.out.println(Arrays.toString(all));
    }

    @Test
    public void testFloat(){
        System.out.println(0.75f == .75f);
    }
}
