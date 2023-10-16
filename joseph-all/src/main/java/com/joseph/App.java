package com.joseph;

import com.joseph.core.lang.Console;

/**
 * @author Joseph.Liu
 */
public class App {
    private App(){}
    public static final String AUTHOR = "Joseph.Liu";
    public static void print(){
        Console.log("Welcome to use the java tools. Author is {}",AUTHOR);
    }
}
