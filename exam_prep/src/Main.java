import java.util.ArrayList;

class A {
    static int f1 = 1;
    int s1;

    public A(int a) {
        this.f1 = a * s1;
        s1 = s1 + 1;
    }
    static int getS() {
        return getS1(s1);
    }

    int getS1(int x) {
        return (x * getS());
    }
}


public class Main {
    public static void main(String[] args) {
        A a = new A(4);
        A.getS1(1);
        System.out.println("Hello world!");
    }
}