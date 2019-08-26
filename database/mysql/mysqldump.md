# 1. mysqldump

<!-- TOC -->

- [1. mysqldump](#1-mysqldump)
  - [1.1. 性能和可扩展性的注意事项](#11-性能和可扩展性的注意事项)
  - [1.2. 调用语法](#12-调用语法)
  - [1.3. 选项语法](#13-选项语法)
    - [1.3.1. mysqldump 选项](#131-mysqldump-选项)

<!-- /TOC -->

mysqldump 要求对需要 dump 的表有查询权限，对视图有 show view 权限，对应的触发器有 TRIGGER 权限并且如果没有使用 `--single-transaction` 选项 则需要使用 `LOCK TABLES`。确保拥有操作描述里要的其它权限。

为了加载 dump 文件，你必须拥有它包含的执行语句的权限。 比如当一个对象被创建的时候需要 `CREATE` 权限。

mysqldump 输出可以包含 `ALTER DATABASE` 语句，它可以改变数据库的集合。这可以用来在导入程序的时候保留原来的字符集。
为了加载 dump 文件包含的这些语句，需要数据库的 `ALTER` 权限。

- 注意：

  一个 dump 模式是使用 PowerShell 在 Windows 用重定向来创建一个文件，这个文件是 `UTF-16` 编码:

  - `shell> mysqldump [options] > dump.sql`

  然而，`UTF-16` 不能用于来作为一个连接字符集（[不允许的客户端字符集](https://dev.mysql.com/doc/refman/8.0/en/charset-connection.html#charset-connection-impermissible-client-charset])）。所以 dump 文件不能正确加载，为了解决这个问题，使用 `--result-file` 选项，这会创建一个 ASCII 格式的文件:

  - `shell> mysqldump [options] --result-file=dump.sql`

## 1.1. 性能和可扩展性的注意事项

mysqldump 的优势包括在重新存储之前可以方便和灵活的查看甚至是编辑输出。你可以克隆数据库对一些开发或者 DBA 的工作，或者一个已经存在的数据库对产生的细微的变化测试。它不是一个用于快速和灵活备份大量数据的解决方案。对于大量的数据，甚至备份时间是合理的，但是恢复数据也可能非常慢，因为需要重放 SQL 语句调用磁盘 I/O 插入、创建索引等等。

对于大数据的备份和恢复，更推荐的是的*物理备份*。复制数据文件用原来的格式可以快速的恢复:

- 如果你的表主要是 innodb 表，或者你主要使用的是 innodb 和 MyISAM 表，考虑使用 MySQL Enterprise Backup （mysql 企业级备份产品），`mysqlbackup` 命令。它以最小的中断对 innodb 提供最好的备份性能；它也可从 MyISAM 或者其它的存储引擎备份其他表；它也提供了大量方便的选项来适配不同的备份场景。详情查看[MySQL Enterprise Backup](https://dev.mysql.com/doc/refman/8.0/en/mysql-enterprise-backup.html).


    mysqldump 可以一行一行的检索 dump 表的内容，也可以从一个表检索出所有内容在 dump 之前把他存在内存里面。缓冲在内存里面有一个问题如果你正在导出的表比较大。使用 `--quick` 选项（或者`--opt`，它允许 `--quick`）。 `--opt`（`--quick`） 选项是默认允许的，所以如果要使用缓冲在内存的话可以使用 `--skip-quick`选项。

    如果使用的是最近的 mysqldump 版本产生的 dump 文件去一个非常老版本的 MySQL 服务器重新加载，使用 `--skip-opt` 选项代替 `--opt` 或者 `--extended-insert` 选项。

    其它关于 mysqldump 额外的信息看,[using-mysqldump](https://dev.mysql.com/doc/refman/8.0/en/using-mysqldump.html)

## 1.2. 调用语法

有大概三种使用 mysqldump 的方式

- 按顺序导出一个或者多个表的集合
  - `shell> mysqldump [options] db_name [tbl_name ...]`
- 导出一个或者多个数据库集合
  - `shell> mysqldump [options] --databases db_name ...`
- 导出整个 MySQL 服务器
  - `mysqldump [options] --all-databases`
  - 导出整个数据库，不需要表名在数据库名后面，或者使用`--databases`/`--all-databases` 选项。
    为了列出你的 mysqldump 版本支持的选项，可以使用 `mysqldump --help`。

## 1.3. 选项语法

mysqldump 支持下面的选项，这些可以在命令行 或者`[mysqldump]`、`[client]`组的 option 文件指定。关于更多的 MySQL 程序使用 option 文件的信息，查看[option-files](https://dev.mysql.com/doc/refman/8.0/en/option-files.html)。

### 1.3.1. mysqldump 选项

| Option Name               | Description                                                            | Introduced | Deprecated | Removed |
| :------------------------ | :--------------------------------------------------------------------- | :--------- | :--------- | :------ |
| --add-drop-database       | 在每一个 CREATE DATABASE 语句之前添加 DROP DATABASE 语句               |
| --add-drop-table          | 在每一个 CREATE TABLE 语句之前添加 DROP TABLE 语句                     |
| --add-drop-trigger        | 在每一个 CREATE TRIGGER 语句之前添加 DROP TRIGGER 语句                 |
| --add-locks               | 用 LOCK TABLES 和 UNLOCK TABLES 包围 dump 中的每一个表                 |
| --all-databases           | 导出所有表的所有库                                                     |
| --allow-keywords          | 允许用他的关键字创建列                                                 |
| --apply-slave-statements  | 在 CHANGE MASTER 语句之前包含 STOP SLAVE，在输出末尾添加 START SLAVE   |
| --bind-address            | 限制特定的网络接口来连接 MySQL 服务器                                  |
| --character-sets-dir      | 字符集安装的目录                                                       |
| --column-statistics       | 写一个 ANALYZE TABLE 语句来产生统计直方图                              | 8.0.2      |
| --comments                | 添加注释到 dump 文件                                                   |
| --compact                 | 产生更紧凑的输出                                                       |
| --compatible              | 产生更加兼容其它数据库系统或更老的 MySQL 服务器                        |
| --complete-insert         | 使用包含列明的完整 INSERT 语句                                         |
| --compress                | 压缩所有在客户端和服务器之间发送的信息                                 |            | 8.0.18     |
| --compression-algorithms  | 在服务器之间允许的压缩算法                                             | 8.0.18     |
| --create-options          | 在 CREATE TABLE 　语句中包含 MySQL 的特殊表                            |
| --databases               | 将所有的参数名称解释为数据库的名称                                     |
| --debug                   | 写入 debug 日志                                                        |
| --debug-check             | 程序退出的时候打印 debuging 信息                                       |
| --debug-info              | 程序退出时打印 debug 信息: 内存、CPU                                   |
| --default-auth            | 使用的认证插件                                                         |
| --default-character-set   | 指定默认字符集                                                         |
| --default-extra-file      | 常规的 option 文件外，读取另外的 option 文件的名称                     |
| --default-file            | 读取确切的 option 文件                                                 |
| --default-group-suffix    | Option 组的后缀值                                                      |
| --delete-master-logs      | 在一个 master 服务器上复制的时候，在 dump 操作执行之后删除二进制日志   |
| --disable-keys            | 对每张表，用 disable keys 和 enable keys 围绕 INSERT 语句              |
| --dump-date               | 包含导出日期例如 "Dump completed on" 注释，如果 --comments 被提供      |
| --dump-slave              | 包含 CHANGE MASTER 语句被列在二进制日志的候选的 slave 的 master 列表中 |
| --enable-cleartext-plugin | 启用明文验证插件                                                       |
| --events                  | 从导出的数据库导出事件                                                 |
