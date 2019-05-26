package com.wymm.lambda;


import java.text.DecimalFormat;
import java.util.function.Function;

interface IMoneyFormat {
    String format(int money);
}

class MyMoney {
    private final int money;

    MyMoney(int money) {
        this.money = money;
    }

    public void printMoney1(IMoneyFormat moneyFormat) {
        System.out.println(moneyFormat.format(this.money));
    }

    /**
     * 因为所需函数接口是输入一个int，输出一个String，所以可以使用JDK自带的Function
     */
    public void printMoney2(Function<Integer, String> moneyFormat) {
        System.out.println(moneyFormat.apply(this.money));
    }
}

public class _2LambdaDemo {
    public static void main(String[] args) {
        MyMoney helloWorld = new MyMoney(999999999);

        // use IMoneyFormat
        helloWorld.printMoney1(i -> new DecimalFormat("#,###").format(i));

        // use Function
        helloWorld.printMoney2(i -> new DecimalFormat("#,###").format(i));

        // 函数接口的链式调用
        Function<Integer, String> moneyFormat = i -> new DecimalFormat("#,###").format(i);
        helloWorld.printMoney2(moneyFormat.andThen(s -> "人名币" + s));
    }
}
