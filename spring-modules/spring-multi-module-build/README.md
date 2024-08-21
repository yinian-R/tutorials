
# 多个模块打包后，启动时共用依赖

使用步骤
1. 构建 spring-multi-module-build，获取完整依赖文件 global-library-wymm
2. 解压各个模块，启动

升级步骤
1. 构建 spring-multi-module-build，查看 global-library-wymm 变动文件
2. 取模块文件和变动的 global-library-wymm，发送到升级服务器