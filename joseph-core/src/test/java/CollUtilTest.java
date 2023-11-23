import com.joseph.core.collection.ListUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

/**
 * @author Joseph.Liu
 */
public class CollUtilTest {

    @Test
    public void testPartition(){
        List<Number> list = new ArrayList<>();
        for(int i = 0; i <= 1001; i++){
            list.add(i);
        }

        List<List<Number>> splitLists = ListUtil.partition(list, 100);
        for(List<Number> sublist : splitLists){
            if(sublist instanceof RandomAccess){
                System.out.println("yes");
            }
            System.out.println(sublist.size());
        }
    }

    @Test
    public void testAvgPartition(){
        List<Number> list = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            list.add(i);
        }

        List<List<Number>> splitLists = ListUtil.avgPartition(list,11);
        for(List<Number> sublist : splitLists){
            System.out.println(sublist);
        }
    }
}
