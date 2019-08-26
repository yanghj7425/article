# 1. mytatis 总结摘录

<!-- TOC -->

- [1. mytatis 总结摘录](#1-mytatis-总结摘录)
  - [1.0.1. #{} 与 \${} 的区别？](#101--与--的区别)
  - [1.0.2. 实体中的属性名称和表中的字段名称不一样时？](#102-实体中的属性名称和表中的字段名称不一样时)
  - [1.0.3. 如何获取自动生成的主键值？](#103-如何获取自动生成的主键值)
  - [1.0.4. 在 Mapper 中如何传递多个参数？](#104-在-mapper-中如何传递多个参数)

<!-- /TOC -->

> 看了半天知乎，摘录的相关内容，原文 [阅读全文](https://zhuanlan.zhihu.com/p/34469960)。

### 1.0.1. #{} 与 \${} 的区别？

- #{} 解析传递进来的参数数据。
- \${} 对传入的参数原样拼接到 SQL 中。

### 1.0.2. 实体中的属性名称和表中的字段名称不一样时？

- 通过在查询的 SQL 中定义字段别名，使之与实体匹配。
- 通过 resultMap 来映射字段名和属性名称。

### 1.0.3. 如何获取自动生成的主键值？

1. Mysql：
   - 通过 LAST_INSERT_ID() 获取刚插入记录的自增主键值，在 insert 语句执行后，执行 select LAST_INSERT_ID() 就可以获取自增主键。
   ```sql
       <insert id="insertUser" parameterType="cn.itcast.mybatis.po.User">
           <selectKey keyProperty="id" order="AFTER" resultType="int">
               select LAST_INSERT_ID()
           </selectKey>
   	    INSERT INTO USER(username,birthday,sex,address) VALUES(#{username},#{birthday},#{sex},#{address})
       </insert>
   ```
2. Oracle

- 可以使用 select 序列.nextval() from dual。

### 1.0.4. 在 Mapper 中如何传递多个参数？

1. 使用占位符

   - 在映射文件中使用 #{0}、#{1} 代表传递进来的第几个参数

   ```sql
       //对应的xml,#{0}代表接收的是dao层中的第一个参数，#{1}代表dao层中第二参数，更多参数一致往后加即可。
       <select id="selectUser"resultMap="BaseResultMap">
           select *  fromuser_user_t   whereuser_name = #{0} anduser_area=#{1}
       </select>
   ```

   - @param 注解方式

   ```java
       public interface usermapper {
        user selectuser(@param(“username”) string username,
        @param(“hashedpassword”) string hashedpassword);
       }

   ```

   ```sql
       <select id=”selectuser” resulttype=”user”>
           select id, username, hashedpassword
           from some_table
           where username = #{username}
           and hashedpassword = #{hashedpassword}
       </select>
   ```

2. 使用 Map 作为参数来封装。
