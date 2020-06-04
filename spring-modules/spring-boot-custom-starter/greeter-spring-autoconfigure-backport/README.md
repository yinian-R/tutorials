## 核心述求
- Spring版本3.x，不打算升级和引入Spring Boot
- 期望在少改代码的前提下实现一些功能的增强
## 面临的问题
- 3.x的Spring没有条件判断注解
- 无法自动定位需要加载的自动配置
## 核心解决思路
- 通过BeanFactoryPostProcessor进行判断
## 配置加载
- 编写Java Config类
- 引入配置类
  - 通过component-scan
  - 通过XML文件import


## Spring的两个扩展点
### BeanFactory
- 针对Bean实例
- 在Bean创建后提供定制逻辑回调
### BeanFactoryProcessor
- 针对Bean定义
- 在创建Bean容器前获取配置元数据