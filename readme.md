## Redis


### 1.基本操作

默认是16个库，下标从0开始  


select 0 选择几号库  

set   

get  

dbsize key数量  


keys 支持ant风格  

keys *  

keys k?  

flushdb 清空当前库  

flushall 清空所有库  

exists k1 判断key存在 1是有0是没有   

move k1 3 将k1移动到3号库  

ttl k1  k1还能存在多久到期 -1永不过期 -2已经过期  

EXPIRE k1 20 设置k120秒后过期，到期后被移除  

type k1 获取类型  

DEL k1 删除



### 2.数据类型

1 string 一个value最大512m  
2 hash  
3 set string类型无需集合，是哈希表实现的，不能重复  
4 list 底层是链表，按照插入顺序排序，可以添加元素到首尾  
5 zset string类型有序集合，不能重复，每个元素带着一个double类型的分数  


* 字符串


append k2 qwert 字符串追加内容   

STRLEN k2 字符串长度  


加减：只有纯数字才能加减  
INCR k3 ：k3+1

DECR k3： k3-1

多步加减  
INCRBY k3 12  : k3+12

DECRBY k3 2  ： k3-2

GETRANGE key 0 -1 : 0到-1代表获取字符串全部   


GETRANGE key 0 3 ： 截取0到3  

覆盖：从0开始设置为xxx 
SETRANGE key 0 xxx

设置键值 k5:v555 ，存活10秒  
setex k5 10 v555

存在才设置值，防止覆盖  
setnx  k1 11100

一次取出多个  
mget k1 k2

设置多个  
mset k1 ooo k5 555



* List

向list 设置值，正进反出  
LPUSH list1 1 2 3 4 5

取出list全部值  
LRANGE list1 0 -1

根据范围取list的值  
LRANGE list1 0 2


向list 设置值，正进正出  
RPUSH list2 1 2 3 4 5 6 7 8 9


左出栈  
lpop list2

右出栈  
rpop list1


索引取值  
LINDEX list1 0

长度  
LLEN list1

删除list中的两个3  
LREM list 2 3 

截取0-5  
LTRIM list2 0 5 


* Set

添加值  
sadd s1 1 2 3 4 

查看 set  
SMEMBERS s1

元素个数  
scard s1

删除元素  
srem s1 2

随机抽取2个  
SRANDMEMBER s1 2

随机出栈  
spop s1 

s1内的e移动到s2  
SMOVE s1 s2 e


差集：在第一个里面但不在后面任何一个里面  
sdiff s1 s2

交集  
SINTER s1 s2


并集  
SUNION s1 s2


* Hash(类似keyvalue)

KV结构不变 value是键值对

添加  
hset user id 11

获取  
hget user id

获取多个  
hmget user id name age

HGETALL user
 
删除一个  
HDEL user name 
 
是否存在属性  
HEXISTS user id

列出键  
HKEYS user


列出值  
HVALS user
 
增长：年零增长10岁  
HINCRBY user age 10
 
设置属性如果不存在  
HSETNX user birth 190603

 
 
* Zset

添加   key:zs1   value: 10 v1,  20 v2 ,......  

zadd zs1 10 v1 20 v2 30 v3 100 v5
 
 取出所有值  
ZRANGE zs1 0 -1 
 
 取出值和score  
 ZRANGE zs1 0 -1 WITHSCORES
 
 
 按score范围10-20取值  
 ZRANGEBYSCORE zs1 10 20
 
 
 [10, 20)  
  ZRANGEBYSCORE zs1 10 (20
 
 
 (10,20)  
  ZRANGEBYSCORE zs1 (10 (20
 
 
 从2开始截取2个  
 ZRANGE zs1 0 -1  limit 2 2
 
 
 按照score移除  
 zrem zs 10
 
 
 统计个数  
 zcard zs1
 
 根据值得到下标  
 ZRANK zs1 v3
 
 根据值得到score  
 ZSCORE zs1 v2
 
 反转查  90-10  
 ZREVRANGEBYSCORE zs1 90 10
 
 
### 3.持久化 rdb aof
 
 
 rdb: redis database  
 aof: append only file  
 
 允许同时存在，同时存在时，首先使用aof恢复
 
 * rdb:  
      
 配置：  
 
     1.save second changes  
     多少秒内 key有多少次改动  
     
     2.stop-writes-on-bgsave-error  
     后台保存时出错，前台停止写 默认yes   
     设置成no代表不在乎数据一致性 
     
     3.禁用rdb: 注释掉save配置
     
     4.rdbcompression 
     存储在磁盘中的快照，是否进行压缩，是的话，将采用lzf算法压缩 
     
     
 命令
 
    1.flushall shutdown
    会生成新的dump.rdb，空文件，无意义
 
    2.save / bgsave
    
    可以忽略配置，立刻备份，save只管保存，全部阻塞，数据无法写入，bgsave在后台处理备份
 
  
 
  
 
 
 
 * aof:  
 
 使用日志记录写操作 ，只追加不改写
 
 配置文件设置 appendonly yes 就打开了aof持久化
 
 aof文件出错，执行指令 redis-check-aof --fix appendonly.aof 进行修复  
 
 
常见配置：  
 
 
1.追写策略 appendfsync ：
    
always 同步持久化 发生数据改变就记录到磁盘，性能差但数据完整性好  
everysec 出厂默认推荐，异步操作，每秒记录，如果一秒内宕机会丢数据  
no
    
    
2.重写策略：
 
    auto-aof-rewrite-percentage 100
    auto-aof-rewrite-min-size 64mb
     
        
aof采用文件追加，文件会越来越大，重写机制为了避免这种情况，当文件大小超过阈值，会被压缩，
只保留可以恢复数据的最小指令集，也可以通过bgrewriteaof指令  

redis会记录上次重写时的aof大小，默认配置是aof文件是上次重写后大小的一倍且文件大于64m时触发
 
 
    no-appendfsync-on-rewrite 
    
重写时是否运用appendfsync，默认为no，保证数据的安全性
 
 
总结：
    1 rdb能在指定时间间隔进行数据存储，aof记录每次对服务器的写操作
    2.只做缓存，可以不开启持久化
    3.rdb更好备份，快速重启，不会有aof存在的潜在问题
    
 
### 4.事务

一次执行多个命令，本质是一组命令的集合，所有命令都会序列化，按顺序串行执行，不被其他命令插入

可以实现在一个队列中，一次性顺序性排他性的执行一系列命令

开启事务：multi
提交事务：exec
放弃事务：DISCARD

redis只部分支持事务，过程中就出现错误的会导致全部失败，过程中不出现错误成功加入队列，但实际执行出错的，不会影响其他执行结果，

watch监控（类似乐观锁）

* 补充，数据库的锁，解决高并发和一致性的冲突


    悲观锁：表锁，认为每次拿数据都认为别人会修改，用于备份
    乐观锁：既保证高并发，又不锁整张表，不对表上锁，表字段加入版本号，根据版本号确定先后


wacth 指令：监控某个key，如果事务执行之前被其他命令改动，那么事务将被打断

unwatch命令： 取消对所有key的监控



### 5.消息发布订阅机制

* 进程间的一种消息订阅发布模式
* redis 先订阅，后发布，才能收到消息  


    SUBSCRIBE c1 c2 c3 订阅c1 c2 c3频道上的消息
    
    PSUBSCRIBE new* 通过通配符一次订阅多个
    
    PUBLISH c2 hello 发布消息


### 6.master/slaver机制：主从复制，读写分离，容灾恢复   

* 1.主从复制

配置：

1.只配置从机，不配置主机，从机设置
2.主机可写，从机不可写
3.主机故障，从机原地待命，主机恢复，从机照旧进行数据复制，或从机执行slaveof no one，停止与其他数据库同步，反从为主
4.从机故障，恢复后，必须重新建立连接，除非在配置文件配置为从机
5.上一个slave可以是下一个slave的master，以减轻中央master的压力，中途一旦变更转向，会清空之前的数据，保证数据的完整性

原理：

slave启动成功后，会发送一个sync指令，master接收到命令后存盘并收集修改命令，发送数据文件到slave，完成一次同步。
全量复制：slave第一次接收到数据文件后，存盘并加载到内存
增量复制：master继续将收集到的命令发送给slave
重新连接master，会执行全量复制





命令：

        info replication 查看主机或从机
        
        SLAVEOF 127.0.0.1 6379  从机绑定主机，完全复制主机


* 2.哨兵模式：自动反从为主


定义：能够从后台监控主机是否故障，如果故障，用投票方式自动将某个从机转换为主机

配置：
1 新建配置文件  touch sentinel.conf  
2. 编写配置文件，追加内容 （sentinel monitor 自定义名称 地址 端口 1代表）   sentinel monitor host6379 127.0.0.1 6379  1
