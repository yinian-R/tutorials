package com.wymm.lambda;

/**
 * 类型定义
 */
public class _5TypeDemo {
    public static void main(String[] args) {
        // 变量类型定义
        IMath lambda1 = (x, y) -> x + y;
        
        // 数组里定义
        IMath[] lambda2 = {(x, y) -> x + y};
        
        // 强转
        Object lambda3 = (IMath) (x, y) -> x + y;
        
        // 通过返回类型定义
        createIMath();
        
        // 通过方法参数定义
        test((x, y) -> x + y);
    }
    
    public static void test(IMath iMath) {
        
    }
    
    public static IMath createIMath() {
        return (x, y) -> x + y;
    }
}

@FunctionalInterface
interface IMath {
    int add(int x, int y);
}