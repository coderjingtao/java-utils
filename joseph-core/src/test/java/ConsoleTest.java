import com.joseph.core.lang.Console;
import com.joseph.core.util.NumberUtil;
import org.junit.Test;

/**
 * @author Joseph.Liu
 */
public class ConsoleTest {

    @Test
    public void logTest() {
        Console.log("my name is {}","joseph");
        Console.print("my name is {}","joseph");
    }

    @Test
    public void progressTest(){
        int total = 100;
        int step = 1;
        for(int i = step; i<= total; i++){
            double ratio = NumberUtil.div(String.valueOf(i),String.valueOf(total)).doubleValue();
            Console.printProgress('#',total,ratio);
        }
    }
}
