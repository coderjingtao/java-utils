import com.joseph.core.comparator.CompareUtil;
import com.joseph.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;

/**
 * @author Joseph.Liu
 */
public class CompareUtilTest {

    @Test
    public void compareTest(){
        Date date1 = DateUtil.parse("2023-11-17", "yyyy-MM-dd");

        Date date2 = null;

        int result = CompareUtil.compare(date1, date2, false);
        System.out.println(result);
    }
}
