package com.wymm._22null_object;

public class CustomerFactory {
    public static String[] names = {"Rob", "Joe", "Julie"};
    
    public static AbstractCustomer getCustomer(String name) {
        for (int i = 0; i < names.length; i++) {
            if (name.equalsIgnoreCase(names[i])) {
                return new RealCustomer(name);
            }
        }
        return new NullCustomer();
    }
}
