package com.joseph.mail;

import com.joseph.core.util.ArrayUtil;
import com.joseph.core.util.StrUtil;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Joseph.Liu
 */
public class Mail {

    private final MailAccount mailAccount;

    private String[] tos;

    private String[] ccs;

    private String[] bccs;

    private String title;

    private String content;

    private boolean isHtml;
    /**
     * attachment
     */
    private final Multipart multipart = new MimeMultipart();
    private boolean isGlobalSession = true;


    //------------Constructor------------------

    public Mail(MailAccount mailAccount){
        this.mailAccount = mailAccount;
    }

    public static Mail create(MailAccount mailAccount){
        return new Mail(mailAccount);
    }

    //------------setter-----------------

    public Mail to(String... tos){
        this.tos = tos;
        return this;
    }

    public Mail cc(String... ccs){
        this.ccs = ccs;
        return this;
    }

    public Mail bcc(String... bccs){
        this.bccs = bccs;
        return this;
    }

    public Mail title(String title){
        this.title = title;
        return this;
    }

    public Mail content(String content){
        this.content = content;
        return this;
    }

    public Mail isHtml(boolean isHtml){
        this.isHtml = isHtml;
        return this;
    }

    public Mail isGlobalSession(boolean isGloablSession){
        this.isGlobalSession = isGloablSession;
        return this;
    }

    public Mail files(File... files){
        if(ArrayUtil.isEmpty(files)){
            return this;
        }
        final DataSource[] attachments = new DataSource[files.length];
        for(int i = 0; i < files.length; i++){
            attachments[i] = new FileDataSource(files[i]);
        }
        return setAttachments(attachments);
    }

    private Mail setAttachments(DataSource... attachments){
        if(ArrayUtil.isEmpty(attachments)){
            return this;
        }
        try{
            for(DataSource attachment : attachments){
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setDataHandler(new DataHandler(attachment));
                bodyPart.setFileName(attachment.getName());
                //image
                if(StrUtil.isNotBlank(attachment.getContentType()) && attachment.getContentType().startsWith("image/")){
                    bodyPart.setContentID(attachment.getName());
                }
                this.multipart.addBodyPart(bodyPart);
            }
        }catch (MessagingException ex){
            ex.printStackTrace();
        }
        return this;
    }

    public String send(){
        try {
            return doSend();
        } catch (MessagingException e) {
            e.printStackTrace();
            if(e instanceof SendFailedException){
                Address[] invalidAddresses = ((SendFailedException) e).getInvalidAddresses();
                String error = StrUtil.format("Invalid Address: {}", ArrayUtil.toString(invalidAddresses));
                System.err.println(error);
            }
        }
        return null;
    }

    /**
     * execute the send action
     * @return message id
     * @throws MessagingException mail exception
     */
    private String doSend() throws MessagingException {
        MimeMessage mimeMessage = buildMail();
        Transport.send(mimeMessage);
        return mimeMessage.getMessageID();
    }

    private MimeMessage buildMail() throws MessagingException {

        final MimeMessage mail = new MimeMessage(MailUtil.getSession(this.mailAccount,this.isGlobalSession));

        mail.setFrom(this.mailAccount.getFrom());
        mail.setSubject(this.title);
        mail.setSentDate(new Date());
        mail.setContent(buildMailContent());

        mail.setRecipients(MimeMessage.RecipientType.TO,parse(this.tos));
        if(ArrayUtil.isNotEmpty(this.ccs)){
            mail.setRecipients(MimeMessage.RecipientType.CC,parse(this.ccs));
        }
        if(ArrayUtil.isNotEmpty(this.bccs)){
            mail.setRecipients(MimeMessage.RecipientType.BCC,parse(this.bccs));
        }
        return mail;
    }

    private Multipart buildMailContent() throws MessagingException {
        final String charsetStr = this.mailAccount.getCharset() != null ? this.mailAccount.getCharset().name() : MimeUtility.getDefaultJavaCharset();
        final MimeBodyPart body = new MimeBodyPart();
        body.setContent(this.content,StrUtil.format("text/{}; charset={}", isHtml ? "html":"plain", charsetStr));
        this.multipart.addBodyPart(body);
        return this.multipart;
    }

    private InternetAddress[] parse(String[] addressStrs) throws AddressException {
        List<InternetAddress> result = new ArrayList<>(addressStrs.length);
        for(String addressStr : addressStrs){
            InternetAddress[] addresses = InternetAddress.parse(addressStr);
            if(ArrayUtil.isNotEmpty(addresses)){
                Collections.addAll(result,addresses);
            }
        }
        return result.toArray(new InternetAddress[0]);
    }



}
