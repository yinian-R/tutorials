## geotools 官方仓库 
https://github.com/geotools/geotools
GeoTools 的不同版本对 Java 版本有明确的依赖关系，以下是主要版本的对应关系及关键信息：

1.  GeoTools 29.x 及以上版本 
    最低 Java 版本要求 ：Java 11
   从 GeoTools 29.x 开始，官方明确要求 Java 11 作为最低版本。如果仍使用 Java 8，需降级至 GeoTools 28.x 或更早版本
   。
2.  GeoTools 21.x–28.x 版本 
    兼容性 ：支持 Java 8 和 Java 11
   GeoTools 21.x 是首个同时兼容 Java 8 和 Java 11 的版本。28.x 及以下版本可在 Java 8 环境中运行，但 29.x 后不再支持 Java 8
   。
    注意 ：GeoTools 20.x 仅支持 Java 8，若在 Java 9/10 环境中运行需添加 JVM 参数（如 --add-modules=java.xml.bind）
   。
3.  更早版本 
    GeoTools 15.x–20.x ：需 Java 8
   。
    GeoTools 12.x ：需 Java 7（支持 Oracle JDK 和 OpenJDK 7）
   。
    GeoTools 9.x ：需 Java 5+，但部分 API 因 Java 5 的迭代器限制进行了调整
   。
4.  GeoServer 与 GeoTools 的版本关联 
   GeoServer 的版本通常依赖特定 GeoTools 版本，例如：

 GeoServer 2.22.x ：对应 GeoTools 28.x，支持 Java 8 和 11
。
 GeoServer 2.23.x+  ：需 GeoTools 29.x+，仅支持 Java 11+
。
5.  推荐选择 
    Java 8 用户 ：建议使用 GeoTools 28.x（最新兼容版本）


## 引入依赖
```xml
<dependency>
    <groupId>io.oss84.geotools</groupId>
    <artifactId>gt-geojson</artifactId>
    <version>24.2</version>
</dependency>
```


