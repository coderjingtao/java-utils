package com.joseph.mail;

import com.joseph.core.util.StrUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 邮件账户
 * @author Joseph.Liu
 */
public class MailAccount implements Serializable {

    public static final String[] MAIL_SETTING_PATHS = new String[]{"config/mail.setting", "config/mailAccount.setting", "mail.setting"};
    /**
     * SMTP server host
     */
    private String host;
    /**
     * SMTP server port
     */
    private Integer port;
    /**
     * need username and password for authentication
     */
    private final Boolean auth = Boolean.TRUE;
    /**
     * username
     */
    private String user;
    /**
     * password
     */
    private String pass;
    /**
     * send from
     */
    private String from;
    /**
     * use SSL communication
     */
    private Boolean sslEnable;
    /**
     * SSL protocols,
     */
    private String sslProtocols;

    private final Charset charset = StandardCharsets.UTF_8;

    public MailAccount(String settingPath){
        //todo add info
        Properties properties = new Properties();
        try{
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(settingPath);
            properties.load(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
        init(properties);
    }
    private void init(Properties properties){
        this.host = properties.getProperty("host");
        this.port = Integer.valueOf(properties.getProperty("port"));
        this.from = properties.getProperty("from");
        this.user = properties.getProperty("user");
        this.pass = properties.getProperty("pass");
        this.sslEnable = Boolean.valueOf(properties.getProperty("sslEnable","true"));
    }

    public Boolean isAuth(){
        return this.auth;
    }

    public String getUser(){
        return this.user;
    }

    public String getPass(){
        return this.pass;
    }

    public String getFrom(){
        return this.from;
    }

    public Charset getCharset(){
        return this.charset;
    }

    public Properties getSmtpProps(){
        final Properties p = new Properties();
        //smtp basic info
        p.put("mail.transport.protocol","smtp");
        p.put("mail.smtp.host",this.host);
        p.put("mail.smtp.port",String.valueOf(this.port));
        p.put("mail.smtp.auth",String.valueOf(this.auth));
        //SSL info
        if(this.sslEnable != null && this.sslEnable){
            p.put("mail.smtp.ssl.enable","true");
            p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            p.put("mail.smtp.socketFactory.fallback","false");
            p.put("smtp.socketFactory.port",String.valueOf(465));

            if(StrUtil.isNotBlank(this.sslProtocols)){
                p.put("mail.smtp.ssl.protocols",this.sslProtocols);
            }
        }
        return p;
    }

    @Override
    public String toString() {
        return "MailAccount [host=" + host + ", port=" + port + ", auth=" + auth + ", user=" + user + ", pass=" + (StrUtil.isEmpty(this.pass) ? "" : "******") + ", from=" + from ;
    }
}
