import com.joseph.core.date.DateTime;
import com.joseph.core.date.DateUtil;
import com.joseph.core.lang.Console;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Joseph.Liu
 */
public class DateUtilTest {

    @Test
    public void testNextMonday(){
        DateTime invoiceDate = DateUtil.parse("2023-10-06", "yyyy-MM-dd");
        DateTime nextMonday = DateUtil.beginOfNextWeek(invoiceDate);
        Console.log("invoiceDate = {}, nextMonday = {}",invoiceDate,nextMonday);
    }

    @Test
    public void testNextFriday(){
        DateTime invoiceDate = DateUtil.parse("2023-10-03", "yyyy-MM-dd");
        DateTime nextFriday = DateUtil.endOfNextBusinessWeek(invoiceDate);
        Console.log("invoiceDate = {}, nextFriday = {}",invoiceDate,nextFriday);
    }

    @Test
    public void testEndOfNextMonth(){
        DateTime invoiceDate = DateUtil.parse("2023-10-03", "yyyy-MM-dd");
        DateTime endOfNextMonth = DateUtil.endOfNextMonth(invoiceDate);
        Console.log("invoiceDate = {}, endOfNextMonth = {}",invoiceDate,endOfNextMonth);
    }

    @Test
    public void testNextTuesdayAndNextWednesday(){
        DateTime invoiceDate = DateUtil.parse("2023-10-06", "yyyy-MM-dd");
        DateTime nextTuesday = DateUtil.nextTuesday(invoiceDate);
        DateTime nextWednesday = DateUtil.nextWednesday(invoiceDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Console.log("nextTuesday = {}, nextWednesday = {}",nextTuesday.toString(dateFormat),nextWednesday.toString(dateFormat));
    }

}
