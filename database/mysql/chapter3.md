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
    1. set profile = 1; 在服务器会话级别动态的修改.
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



