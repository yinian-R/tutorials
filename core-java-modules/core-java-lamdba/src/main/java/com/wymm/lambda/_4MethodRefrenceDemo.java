package com.wymm.lambda;

public class _4MethodRefrenceDemo {
    public static void main(String[] args) {
        // 方法引用
        Consumer<String> consumer1 = System.out::println;
        consumer1.accept("接受的数据");
        
        // 静态方法的方法引用
        Consumer<Dog> consumer2 = Dog::bark;
        Dog dog = new Dog();
        consumer2.accept(dog);
        
        // 非静态方法，使用对象实例的方法引用
        Function<Integer, Integer> function1 = dog::eat;
        System.out.println("还剩下" + function1.apply(2) + "斤");
        // 优化，因为输入和输出类型相同，所以可以使用一元函数接口
        UnaryOperator<Integer> function2 = dog::eat;
        System.out.println("还剩下" + function2.apply(2) + "斤");
        // 优化，因为是输入和输出都是Integer，所以可以使用 IntUnaryOperator
        IntUnaryOperator function3 = dog::eat;
        System.out.println("还剩下" + function3.applyAsInt(2) + "斤");
        
        // 使用类名来方法引用
        BiFunction<Dog, Integer, Integer> biFunction = Dog::eat;
        System.out.println("还剩下" + biFunction.apply(dog, 2) + "斤");
        
        // 构造函数的方法引用
        Supplier<Dog> supplier = Dog::new;
        System.out.println("创建了新对象：" + supplier.get());
        
        // 带参数的构造函数的方法引用
        Function<String, Dog> function4 = Dog::new;
        System.out.println("创建了新对象：" + function4.apply("旺财"));
    }
    
    static class Dog {
        private String name = "中华田园犬";
        // 狗粮剩余数量（斤）
        private int food = 10;
        
        public Dog() {
            
        }
        
        public Dog(String name) {
            this.name = name;
        }
        
        public static void bark(Dog dog) {
            System.out.println(dog + "叫了");
        }
        
        /**
         * 和public int eat(Dog this,int num) 一样，调用的方式依旧是 doy.eat(2)
         * JDK 会把当前成员传递到非静态方法的第一个参数this
         */
        public int eat(int num) {
            System.out.println("狗吃了" + num + "斤");
            return food -= num;
        }
        
        @Override
        public String toString() {
            return name;
        }
        
    }
}


