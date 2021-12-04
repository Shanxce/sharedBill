### API通用格式

##### 添加资产

+ URL
  /api/bill/createRecord
  
+ 请求方法
  POST
  
+ 请求参数
  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    billId    |  int   |  账本编号  |
  |    assetsId    |  int   |  资产编号  |
  |    count    |  double   |  金额  |
  |    describe    |  string   |  账单描述  |
  |    photoAddr    |  string   |  图片地址  |
  |    joiner    |  int[]   |  参与者编号（必须包括自己）  |

+ 返回参数
  无

##### 获取账单参与者

+ URL

  /api/bill/getJoiners
  
+ 请求方法

  GET
  
+ 请求参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    recordId    |  int   |  账单编号  |

+ 返回参数

  | 参数名称 |  类型  |   描述   |
  | :------: | :----: | :------: |
  |    Users    |  User[]   |  参与者信息  |
  
##### 修改账单

+ URL

  /api/bill/recordCleanModify
  
+ 请求方法

  POST
  
+ 请求参数

  | 参数名称   |  类型  |   描述     |
  | :------:  | :----: | :------:   |
  | recordId | int | 账单编号        |
  | assetsId   |  int   |  资产编号 |
  

+ 返回参数

  无


##### 修改账单

+ URL

  /api/bill/recordNocleanModify
  
+ 请求方法

  POST
  
+ 请求参数

  | 参数名称   |  类型  |   描述     |
  | :------:  | :----: | :------:   |
  | recordId | int | 账单编号        |
  | assetsId   |  int   |  资产编号 |
  | describe   |  string   |  描述 |
  | count   |  double   |  金额 |
  

+ 返回参数

  无
  
