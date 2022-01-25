# Flyway

脚本初始版本从V1.0.1开始，例如：V1.0.1__init.sql

> 特别重要：操作表结构和操作表数据的 SQL 应该分别创建不同的文件。（支持事务回滚）

## 修改数据 SQL 使用 MySQL 事务
> 特别提醒：事务用来管理 insert,update,delete 语句。操作表结构使用事务无效的，因此，如果需使用事务则 flyway 中把操作表结构的脚本和操作表数据的脚本分别创建文件
```sql
BEGIN;
-- do something
COMMIT;
```

## flyway 执行失败后，如何重新执行
```
简单地重新执行的问题是可能已经应用了一些更改，这将导致迁移失败。 有两种解决方案：
1. 使用支持DDL事务的数据库，如PostgreSQL，SQLServer、MySQL，在 flyway 执行 migrate 操作之前执行 repair 操作。修复 flyway.table 中的记录
2.在重新应用之前，手动清理已修改的结构和元数据表
```

## 脚本执行顺序
顺序仅和文件版本命名，与外层文件夹无关