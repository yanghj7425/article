# 1. liunx 操作笔记

<!-- TOC -->

- [1. liunx 操作笔记](#1-liunx-操作笔记)
  - [1.1. 基本命令](#11-基本命令)
  - [1.2. 扩展 LVM](#12-扩展-lvm)
  - [1.3. debain](#13-debain)
  - [1.4. centos](#14-centos)
    - [1.4.1. svn 相关](#141-svn-相关)
    - [1.4.2. 数据库](#142-数据库)
      - [1.4.2.1. mysql](#1421-mysql)
        - [1.4.2.1.1. 安装步骤](#14211-安装步骤)
        - [1.4.2.1.2. 启动设置](#14212-启动设置)
    - [1.4.3. 网络设置](#143-网络设置)

<!-- /TOC -->

## 1.1. 基本命令

- 内核版本相关

  - `cat /proc/version`
  - `uname -a`、`uname -r`

- 系统版本下相关

  - `lsb_release -a`
  - `cat /etc/issue`

- 后台运行

  - `&` 表示后台运行
  - `nohup /usr/local/redis/redis-5.0.2/bin/redis-server >/dev/null 2>&1 &`
  - 参数说明

    |           类型            | 文件描述符 |        默认情况        | 对应文件句柄位置 |
    | :-----------------------: | :--------: | :--------------------: | :--------------: |
    | 标准输入(standard input)  |     0      |     从键盘获得输入     | /proc/self/fd/0  |
    | 标准输出(standard output) |     1      | 输出到屏幕（即控制台） | /proc/self/fd/1  |
    |  错误输出(error output)   |     2      | 输出到屏幕（即控制台） | /proc/self/fd/2  |

## 1.2. 扩展 LVM

- 查看扩展磁盘

  - 命令 `fdisk -l`，这里是 `/dev/sdb`

- 操作命令

```sh
 >   pvcreate /dev/sdb
 >   vgcreate storage /dev/sdb
 >   lvcreate -n vo -l 2 storage
 >   mkfs.ext4 /dev/storage/vo # 格式化
 >   mkdir /tmp/mysql
 >   mount /dev/storage/vo /tmp/mysql/   # 挂载使用
```

以上命令，创建了逻辑卷。逻辑卷创建好后其实是链接到一个路径，如上:`/dev/storage/vo`。挂载后就可使用。

## 1.3. debain

- 替换源

  - 路径 `/etc/apt/sources.list`

- 安装中文输入法

  - apt install ibus-pinyin

- 安装 aptitude

  - apt-get install aptitude

- 虚拟机则安装增强工具

  - apt-get install virturalbox-guest-addition-iso

- 配置 shadowsocks
  - https://github.com/fanach/download/releases/download/latest/shadowsocks-local-linux32-1.1.5.gz

## 1.4. centos

### 1.4.1. svn 相关

- 切换仓库路径

  - `svn switch --relocate <old_locate> <new_locate>`

- 修改 SVN 用户名密码
  - 永久更换：
    > 删除目录 `~/.subversion/auth/` 下的所有文件。下一次操作 svn 时会提示你重新输入用户名和密码。
    - `rm -rf ~/.subversion/auth/`

### 1.4.2. 数据库

#### 1.4.2.1. mysql

- [官网下载地址](#https://cdn.mysql.com//Downloads/MySQL-8.0/mysql-8.0.17-linux-glibc2.12-x86_64.tar.xz)

- [官方安装手册地址](#https://dev.mysql.com/doc/refman/8.0/en/binary-installation.html)

##### 1.4.2.1.1. 安装步骤

```sh
 shell> groupadd mysql
 shell> useradd -r -g mysql -s /bin/false mysql
 shell> cd /usr/local
 shell> tar xvf /path/to/mysql-VERSION-OS.tar.xz
 shell> ln -s full-path-to-mysql-VERSION-OS mysql
 shell> cd mysql
 shell> mkdir mysql-files
 shell> chown mysql:mysql mysql-files
 shell> chmod 750 mysql-files
 shell> bin/mysqld --initialize --user=mysql
 shell> bin/mysql_ssl_rsa_setup
 shell> bin/mysqld_safe --user=mysql &
 # Next command is optional
 shell> cp support-files/mysql.server /etc/init.d/mysql.server
```

> 至此, mysql 已经安装好了。

##### 1.4.2.1.2. 启动设置

- 修改配置

  - 找到 my.cnf 文件(`find / -name my.cnf`),查看文件内所配置的路径存在，如果不存在需要创建。并授权给 mysql 这个用户。

- 添加服务依赖
- `cp support-files/mysql.server /lib/systemd/system/`
- 刷新服务依赖
  - `systemctl daemon-reload`
- 添加开启启动
  - `systemctl enable mysql.server`
- 禁止开机启动

  - `systemctl disable mysql.server`

- 添加环境变量
  ```
    export PATH=$PATH:/usr/local/mysql/bin
    export PATH=$MYSQL_HOME/bin:$PATH
  ```
- mysql 服务 启动初始化设置
- 启动
  - systemctl restart mysql
- 连接
  - 这里要指定 -h 参数不然会报错（不想去深究哪儿的问题了）
    - `mysql -h 127.0.0.1 -uroot -p`
- 修改初始密码
  - `alter user user() identified by "123456";`
- 新建用户
  - `create user 'demo'@'%' identified by '123456';`
- 授权
  - `grant all privileges on demo.* to demo@"%" ;`
- 刷新权限

  - `flush privileges;`

- MySQL 配置文件启动

  - `./mysqld --defaults-file=/usr/local/mysql/my.cnf --user=mysql`

- 防火墙设置

  - 打开端口
    - `firewall-cmd --permanent --zone=public --add-port=3306/tcp;`
  - 重启防火墙
    - `systemctl restart firewalld`

### 1.4.3. 网络设置

- 生成配置文件
  - 使用 nmtui (Text User Interface for controlling NetworkManager), 是一个 centos7 自带的图形化网络管理工具。
- 防火墙设置
  - [参考博客地址](#https://www.cnblogs.com/daxiongblog/p/6003170.html)
