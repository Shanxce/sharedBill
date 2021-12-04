### API通用格式

#### 返回格式

| 参数名称 |  类型  |       描述       |
| :------: | :----: | :--------------: |
| code | int | 返回代码 |
| msg | string | 返回信息 |
| data | Data | 返回数据 |

#### API公共错误码

| 返回码   |       描述       |
| :-----: | :-------------: |
| 200 | 正常 |
| 300 | 用户未登录 |
|  301  | 权限不足 |
| 310 | 验证码错误 |
| 311 | 邮箱已存在 |
| 312 | 手机号已存在 |
| 313 | 用户名或密码错误 |
| 320 | 资源不存在 |
| 400 | 非法请求参数 |
| 500 | 服务器内部错误 |

#### 部分结构定义

##### User

| 参数名称 |  类型  |    描述    |
| :------: | :----: | :--------: |
|   uid   |  int   | 用户id |
|   name    | string |  用户名  |

##### Record

| 参数名称 |  类型  |    描述    |
| :------: | :----: | :--------: |
|    recordId    |  int   |  账单编号  |
  |    billId    |  int   |  账本编号  |
  |    count    |  double   |  金额  |
  |    describe    |  string   |  账单描述  |
  |    assetsId |  int |  资产编号 |
  |    assetsName    |  string   |  资产名称  |
  |    createtime    |  datetime   |  创建时间  |

##### Bill

| 参数名称 |  类型  |    描述    |
| :------: | :----: | :--------: |
| id | int | 账本id |
| name | string | 账本名称 |
| share | bool | 是否共享 |

##### Asset

| 参数名称 |  类型  |    描述    |
| :------: | :----: | :--------: |
| id | int | 资产id |
| name | string | 名称 |
| amount | double | 金额 |
| createtime| datetime| 创建时间 |

### API列表

#### 用户模块

##### 登录

- URL

  /api/user/login

- 请求方法

  POST

- 请求参数

  | 参数名称 |  类型  |       描述       |
  | :------: | :----: | :--------------: |
  | username | string | 用户手机号或邮箱 |
  |  passwd  | string |       密码       |

- 返回参数

  无

##### 注册

+ URL

  /api/user/register

+ 请求方法

  POST
  
+ 请求参数

  | 参数名称 |  类型  |  描述  |
  | :------: | :----: | :----: |
  | username | string | 用户名 |
  |  passwd  | string |  密码  |
  |   mail   | string |  邮箱  |
  |  phone   | string | 手机号 |
  | captcha  | string | 验证码 |

+ 返回参数

  无

##### 发送邮件

+ URL

  /api/user/sendMail
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |  描述  |
  | :------: | :----: | :----: |
  | mail | string | 邮箱地址 |
  
+ 返回参数

  无

##### 退出登录

+ URL

  /api/user/logout

+ 请求方法

  GET

+ 请求参数

  无
  
+ 返回参数

  无

##### 用户信息

+ URL

  /api/user/getMyInfo
  
+ 请求方法

  GET
  
+ 请求参数

  无
  
+ 返回参数

  | 参数名称 |  类型  |         描述         |
  | :------: | :----: | :------------------: |
  | id | int | 用户id |
  | name   | string | 用户名 |
  | phone | string | 手机号 |
  | mail | string | 邮箱 |

##### 用户信息修改

+ URL

  /api/user/modify
  
+ 请求方法

  POST
  
+ 请求参数

  | 参数名称 |  类型  |         描述         |
  | :------: | :----: | :------------------: |
  | newname | string | 新用户名 |
  | passwd  | string | 原密码 |
  | newpasswd | string | 新密码 |
  
+ 返回参数

  无

##### 获取账本信息

+ URL
  
  /api/user/getBills
  
+ 请求方法

  GET
  
+ 请求参数

  无
  
+ 返回参数

  | 参数名称 |  类型  |         描述         |
  | :------: | :----: | :------------------: |
  | bills | Bill[] | 账本信息 |

#### 账本模块

##### 创建账本

+ URL

  /api/bill/create
  
+ 请求方法

  POST
  
+ 请求参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | name | string | 账本名称 |
  | share | bool | 是否共享 |
  
+ 返回参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | id | int | 账本id |

##### 获取参数者

+ URL

  /api/bill/getUsers
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | id | int | 共享账本id |
  
+ 返回参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | users | User[] | 参与账本的用户 |

##### 获取邀请码

+ URL

  /api/bill/getInviteCode
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | billId | int | 共享账本id |
  
+ 返回参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | inviteCode | string | 邀请码 |


##### 加入账本

+ URL

  /api/bill/join
  
+ 请求方法

  POST
  
+ 请求参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | code | string | 邀请码 |
  
+ 返回参数

  无



##### 添加新账单

+ URL

  /api/bill/createRecord
  
+ 请求方法

  POST
  
+ 请求参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | billId | int | 账本编号 |
  | assetsId | int | 资产编号 |
  | count | double | 金额 |
  | describe | string | 账单描述 |
  | photoAddr | string | 图片地址 |
  
+ 返回参数

  无


##### 获取账本内所有账单

+ URL

  /api/bill/getRecords
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |    描述    |
  | :------: | :----: | :--------: |
  | id | int | 账本id |
  
+ 返回参数

  | 参数名称 |   类型   |    描述    |
  | :------: | :------: | :--------: |
  | Records  | Record[] |  账单记录  |

##### 获取单个账单信息

+ URL

  /api/bill/getRecordsInfo
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    id    |  int   |  账单Id  |

+ 返回参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  | Record  | Record[] |  账单记录  |

##### 账单清算

+ URL

  /api/bill/calculate
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    id    |  int   |  账本Id  |

+ 返回参数

  无

#### 资产模块

##### 创建资产

+ URL

  /api/assets/create

+ 请求方法

  POST

+ 请求参数

  | 参数名称 |   类型   |    描述    |
  | :------: | :------: | :--------: |
  | name  |string |  资产名称  |
  | amount | double | 资产总量 |

+ 返回参数

  | 参数名称 |   类型   |    描述    |
  | :------: | :------: | :--------: |
  | id  | int |  资产id  |

##### 修改资产

+ URL

  /api/assets/modify
  
+ 请求方法

  POST
  
+ 请求参数

  | 参数名称 |   类型   |    描述    |
  | :------: | :------: | :--------: |
  | id  | int |  资产id  |
  | name | string | 资产名称 |
  | account | double | 资产金额 |
  
+ 返回参数

  无
##### 获取所有资产

+ URL

  /api/assets/getAssets
  
+ 请求方法

  GET
  
+ 请求参数

  无

+ 返回参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    Assets    |  Asset[]   |  资产  |

##### 获取账单信息

+ URL

  /api/assets/getAssetsInfo
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    id    |  int   |  资产Id  |

+ 返回参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    Asset    |  Asset   |  资产  |

##### 获取资产账单信息

+ URL

  /api/assets/getRecords
  
+ 请求方法

  GET
  
+ 请求参数

  无

+ 返回参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    Records    |  Record[]   |  所有账单信息  |
  
