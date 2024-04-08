package com.joseph.mail;

import com.joseph.core.collection.CollUtil;
import com.joseph.core.collection.ListUtil;
import com.joseph.core.lang.Assert;
import com.joseph.core.util.CharUtil;
import com.joseph.core.util.StrUtil;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Email Utility
 * @author Joseph.Liu
 */
public class MailUtil {

    public static String sendText(String to, String subject, String content, File... files){
        return send(to,subject,content,false,files);
    }

    public static String sendHtml(String to, String subject, String content, File... files){
        return send(to,subject,content,true,files);
    }

    public static String send(String to, String subject, String content, boolean isHtml, File... files){
        return send(splitAddress(to),subject,content,isHtml,files);
    }

    public static String send(Collection<String> tos, String subject, String content, boolean isHtml, File... files){
        return send(tos, null, null, subject, content, isHtml, files);
    }

    public static String send(Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content, boolean isHtml, File... files){
        return send(GlobalMailAccount.INSTANCE.getMailAccount(), true, tos, ccs, bccs, subject, content, isHtml, files);
    }

    private static String send(MailAccount mailAccount, boolean useGlobalSession,
                               Collection<String> tos, Collection<String> ccs, Collection<String> bccs, String subject, String content,
                               boolean isHtml, File... files){
        Mail mail = Mail.create(mailAccount).isGlobalSession(useGlobalSession);
        if(CollUtil.isNotEmpty(ccs)){
            mail.cc(ccs.toArray(new String[0]));
        }
        if(CollUtil.isNotEmpty(bccs)){
            mail.bcc(bccs.toArray(new String[0]));
        }
        return mail.to(tos.toArray(new String[0]))
                .title(subject)
                .content(content)
                .isHtml(isHtml)
                .files(files)
                .send();
    }

    public static List<String> splitAddress(String addresses){
        Assert.notNull(addresses);
        List<String> result;
        if(StrUtil.contains(addresses, CharUtil.COMMA)){
            String[] addressArr = addresses.split(String.valueOf(CharUtil.COMMA));
            result = ListUtil.toList(addressArr);
        }else if(StrUtil.contains(addresses, CharUtil.SEMICOLON)){
            String[] addressArr = addresses.split(String.valueOf(CharUtil.SEMICOLON));
            result = ListUtil.toList(addressArr);
        }else{
            result = CollUtil.newArrayList(addresses);
        }
        return result;
    }

    public static Session getSession(MailAccount mailAccount, boolean isGlobalSession){
        Authenticator authenticator = null;
        if(mailAccount.isAuth()){
            authenticator = new UserPassAuthenticator(mailAccount.getUser(),mailAccount.getPass());
        }
        return isGlobalSession ?
                Session.getDefaultInstance(mailAccount.getSmtpProps(),authenticator) :
                Session.getInstance(mailAccount.getSmtpProps(),authenticator);
    }
}
