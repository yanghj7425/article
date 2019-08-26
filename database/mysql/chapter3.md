# 第三章 服务器性能剖析

## 剖析 SQL 查询

- 捕获 MySQL 的查询日志到文件中
  1. 在 MySQL5.1 级更新的版本中,慢日志已经被加强,可以通过设置 long_query_time 为 0 来捕获所有的查询,而且可以让查询响应时间已经可以做到微妙级别.
  2. 通用日志查询,通用日志在查询请求到服务器时进行记录,所以不包含响应时间和执行计划等重要信息.
  3. Percona Server 的慢查询日志比官方的更详细.

### 分析日志查询

- 离差指数: 指的是查询对应的响应时间的变化较大,而这类查询通常值得优化.

### 剖析单条查询

- 使用 show profile

  - 查看 profiling 状态 `show variables like '%profiling%'`, profiling 在 5.7 以后的版本推荐使 用 `performance_schema`,亦可通过 `show variables like '%performance_schema%'` 查看.

  1. set profiling = 1; 在服务器会话级别动态的修改.
  2. show profiles
  3. show profile for query 1
  4. 查询 information_schema.PROFILING 表

     ```sql

     SELECT
         state,
         SUM(duration) total_r,
         ROUND(SUM(duration) * 100 / (SELECT
                         SUM(duration)
                     FROM
                         information_schema.PROFILING
                     WHERE
                         QUERY_ID = '25'),
                 2) ptc_r,
         COUNT(*) calls,
         SUM(duration) / COUNT(*) 'r/calls'
     FROM
         information_schema.PROFILING
     WHERE
         QUERY_ID = '25'
     GROUP BY state
     ORDER BY total_r DESC;
     ```

* 使用 show status

  - show status 命令返回一个计数器,有全局的也有会话级别的.没提交一次计数器加 1. 使用 `show global status` 可以查看服务器级别的从服务器启动开始执行的查询次数.

* 使用 processlist

  - `show processlist` 命令可以查询出当前数据库链接的状态.
  - `SELECT * FROM information_schema.PROCESSLIST` 可以查询出和 `show processlist` 同样的结果.

* 使用查询日志
  > 如果要通过慢查询日志,则需要开启并在全局级别这只 `long_query_time` 为 0, 并确认所有链接都已经使用了这种设置.
  - 可以使用 tcpdump 和 pt-query-digest 工具模拟代替.
