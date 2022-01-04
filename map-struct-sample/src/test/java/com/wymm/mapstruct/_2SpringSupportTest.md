## 在 Spring 中更好的使用 MapStruct

```java
/**
 * 1 把 Mapper 注入 Spring Bean 中
 */
@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper {
}

/**
 * 2 有时，我们需要在映射逻辑中使用其他 Spring 组件。在这种情况下，我们必须使用抽象类而不是接口
 */
@Mapper(componentModel = "spring")
public abstract class SimpleDestinationMapperUsingInjectedService{
    /**
     * 我们必须记住不要将注入的 bean 设为私有！这是因为 MapStruct 必须访问生成的实现类中的对象
     */
    @Autowired
    protected SimpleSourceDestinationMapper simpleService;

    @Mapping(target = "name", expression = "java(simpleService.enrichName(source.getName()))")
    public abstract SimpleDestination sourceToDestination(SimpleSource source);
}
```