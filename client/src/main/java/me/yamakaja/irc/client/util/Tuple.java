package me.yamakaja.irc.client.util;

/**
 * Created by Yamakaja on 03.02.17.
 */
public class Tuple<A,B> {

    private A a;
    private B b;

    public Tuple() {
    }

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
