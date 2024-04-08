import com.joseph.core.lang.Console;
import com.joseph.mail.GlobalMailAccount;
import com.joseph.mail.MailAccount;
import com.joseph.mail.MailUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Joseph.Liu
 */
public class MailUtilTest {

    @Test
    public void test(){
        String addresses = "abc@gmail.com;456@sina.com;joseph@newaim.com.au";
        List<String> result = MailUtil.splitAddress(addresses);
        System.out.println(result);
    }

    @Test
    public void parseSettingFile(){
        MailAccount mailAccount = GlobalMailAccount.INSTANCE.getMailAccount();
        System.out.println(mailAccount);
    }

    @Test
    public void getConfigInResource(){
        Properties p = new Properties();
        InputStream in = this.getClass().getResourceAsStream("config/mail.setting");
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<Object> keys = p.keySet();
        for(Object key : keys){
            Console.log("key = {}, value = {}", key, p.getProperty((String) key));
        }
    }

    @Test
    public void sendHtmlMailTest(){
        MailUtil.send("invoice@newaim.com.au","Joseph Test","<h1>Mail from Joseph to test</h1>",true);
    }
}
