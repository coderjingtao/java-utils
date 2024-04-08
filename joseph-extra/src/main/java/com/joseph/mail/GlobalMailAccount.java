package com.joseph.mail;

/**
 * @author Joseph.Liu
 */
public enum GlobalMailAccount {
    /**
     *
     */
    INSTANCE;

    private final MailAccount mailAccount;

    GlobalMailAccount(){
        mailAccount = createDefaultAccount();
    }

    public MailAccount getMailAccount(){
        return this.mailAccount;
    }

    private MailAccount createDefaultAccount(){
        for(String mailSettingPath : MailAccount.MAIL_SETTING_PATHS){
            try{
                return new MailAccount(mailSettingPath);
            } catch (RuntimeException ignore){
                //ignore
            }
        }
        return null;
    }
}
