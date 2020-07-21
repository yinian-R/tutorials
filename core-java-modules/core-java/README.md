#### 泛型

Java 泛型提供了编译时类型安全检测机制，该机制允许程序员在编译时检测到非法的类型。泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。

优点：增加鲁棒性，并使程序更易于阅读

避免了强制类型转换并提供了类型安全性

*什么时候选择上界通配符和下界通配符？*

在上下界通配符之间进行选择的常见规则是PECS，PECS代表生产者 extends，消费者 super。

生产者 extends，如果您想创建泛型生产者，则可以使用 extends 关键字。让我们尝试将其应用于集合：
```
public static void makeLotsOfNoise(List<? extends Animal> animals) {
    animals.forEach(Animal::makeNoise);
}
```
在这里，每一个动物都调用 makeNoise() 制造噪音，这意味着列表是生产者。如果没有 extends 则无法传递 dogs、cats 或其他动物子类列表。
通过生产者扩展原则，将拥有最大的灵活度。

消费者 super，如果我们要处理消耗元素的事务，则应该使用 super 关键字。
```
public static void addCats(List<? super Animal> animals){
    animals.add(new Cat());
}
```
这意味着我们可以传入任何动物超类列表，但不能传入子类。例如，如果我们传入 dogs、cats，则代码编译错误

最后要考虑，如果集合既是生产者又是消费者，该怎么办（示例：即添加元素也删除元素）。在这种情况下，应使用无界通配符 ?

## 参考
https://www.baeldung.com/java-generics-interview-questions