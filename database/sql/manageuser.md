# <center>Oracle 创建、删除用户 </center>


##数据库权限

*一、创建*

sys : 系统管理员，最高权限 <br>
system : 本地管理员，次高权限 <br>
scott：普通用户，默认密码为 tiger，默认未解锁<br>
<hr style="color:yellow">
*二、登陆*

`sqlplus sys/root as sysdba` : 以 DBA 的方式登陆 sys 账户其他用户类似

*三、管理用户*

`create user test`;//在管理员帐户下，创建用户 test <br> 
`alter user scott identified by tiger`;//修改密码


- 删除用户，及级联关系也删除掉
drop user LYK cascade;
- 删除表空间，及对应的表空间文件也删除掉
drop tablespace LYK including contents and datafiles cascade constraint;

*四、授予权限*

1. 默认的普通用户 *scott*  默认未解锁，不能进行使用，新建的用户也没有任何权限，必须授予权限。<br>
    - `grant create session to scott` :  授予用户 *scott session*权限，即登陆权限<br>
    - `grant unlimited tablespace to scott` ： 授予用户使用表空间的权限<br>
    - `grant create table to scott` ：授予用户创建表的权限<br>
    - `grant drop table to scott` ：授予用户删除表的权限<br>
    - `grant insert table to scott` ： 授予用户插入表的权限<br>
    - `grant update table to scott` ：授予用户更细表的权限<br>
    - `grant all to public`: 授予所有权限给所有用户<br>
    <hr>
2. ORACLE 对权限管理比较严格，普通用户之间也是默认不能访问的需要相互授权

    - `grant select on tablename to scott` : 授予scott 查看指定表的权限
    - `grant drop on tablename to scott` : 授予用删除指定表的权限
    - `grant  insert on tablename to scott`：授予户插入指定表的权限
    - `grant  insert(id) on tablename to scott`：予用户对指定表指定字段的插入
    - `grant update(id) on tablename to scott`： 予用户对指定表指定字段的修改
    - `grant alert all table to scott`：　授予用户`alert` 任意表的权限


*五、权限撤销*
		基本语法通`grant` ，关键字改为 `revoke`.

*六、查看权限*
- `select * from user_sys_privs` : 查看当前用户的权限
- `select * from user_tab_privs`: 查看所用用户对表的权限

*七、操作表的用户的表*

`select *  from scott.tablename`

*八、权限传递*

1. 即用户 A 的权限授予 B ，B 可以将授权操作的权限在授予 C :
    - `grant alert table on tablename to A with admin option` : 关键字 `with admin option`
    - `grant alert table on tablename to A with grant option` : 关键字 `with grant option`  和  `admin` 类似


*九、角色*
1. 角色即权限的集合，可以把一个角色授予用户
    - `create role myrole`: 创建 角色<br>
    - `grant create seeion to myrole`:将创建`session` 的权限授予角色 `myrole`<br>
    - `grant myrole to scott` :  授予 `scott` 用户  `myrole` 的角色<br>
    - `drop role myrole` :  删除角色 <br>
