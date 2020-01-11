package com.oranle.es.module;

import org.junit.Test;

public class TestJava {

    @Test
    public void test1() {
        char a = 'a';

        String astr = a +"";

        int aInt = a;




        System.out.println("xxxx int " + aInt);
        System.out.println("xxxx str " + astr.toUpperCase());

        char aCast = (char)(65);

        System.out.println("xxxx aCast " + aCast);
    }

}
