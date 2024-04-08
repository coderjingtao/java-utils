package com.joseph.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Joseph.Liu
 */
public class UserPassAuthenticator extends Authenticator {

    private final String user;
    private final String pass;

    public UserPassAuthenticator(String user, String pass){
        this.user = user;
        this.pass = pass;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.user,this.pass);
    }
}
