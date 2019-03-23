## Redis


* 5大数据类型

1 string 一个value最大512m
2 hash
3 set string类型无需集合，是哈希表实现的，不能重复
4 list 底层是链表，按照插入顺序排序，可以添加元素到首尾
5 zset string类型有序集合，不能重复，每个元素带着一个double类型的分数



* 基本操作

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
 
 
 
 
 
 
 
 
 
 
 
 
 
 