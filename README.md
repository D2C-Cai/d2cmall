# D2C商城项目结构
商城JAVA服务端源码，在线产品PC/WAP/APP/小程序，各渠道搜索“D2C全球好设计”即可查看。

## D2CMALL项目大模块介绍
| d2cmall商城项目 | 模块功能介绍 |
| ---- | ---- |
| d2cmall-common |  公共组件，中间件框架组件式拆分，<br>Maven选择性引入，快速构建项目 |
| d2cmall-frame |  公共基准模型，Dubbo项目体系，根据项目类型，<br>定义了一系列基准模型，便于抽取同类项目间的公共属性 |
| d2cmall-provider |  功能组件，SOA解耦拆分的业务微服务，<br>是项目整体核心业务逻辑部分 |
| d2cmall-services |  服务组件，系统附加的一些服务，<br>如定时任务等 |
| d2cmall-main |  项目对外的接口服务，<br>如PC、WAP、APP、小程序、开放API等 |
| d2cmall-back |  项目对内的接口服务，<br>如运营后台、商家后台、门店后台、统计报表等 |

### 公共组件部分
| d2cmall-common | 具体功能介绍 | 
| ---- | ---- |
| d2cmall-common-base |  公共常用的工具类，异常类等 |
| d2cmall-common-api |  针对API项目的基类，抽象类等 |
| d2cmall-common-core |  Spring的工具类，二次封装类 |
| d2cmall-common-mybatis |  MyBatis的工具类，二次封装类 |
| d2cmall-common-cache |  Redis的工具类，二次封装类 |
| d2cmall-common-mongo |  MongoDB的工具类，二次封装类 |
| d2cmall-common-search |  Elasticsearch的工具类，二次封装类 |
| d2cmall-common-mq |  ActiveMQ的工具类，二次封装类 |
| d2cmall-common-math |  Math算法的工具类，二次封装类 |
| d2cmall-common-old |  老版本遗留公共常用的工具类 |

### 项目基准模型
| d2cmall-frame | 具体功能介绍 |
| ---- | ---- |
| d2cmall-frame-api	|  API项目的基准模型，dubbo-api |
| d2cmall-frame-provider |	Provider的基准模型，dubbo-provider |
| d2cmall-frame-web |  项目对外的接口服务的基准模型 |
| d2cmall-frame-back |  项目对内的接口服务的基准模型 |

### 业务SOA组件服务
| d2cmall-provider |  具体功能介绍 |
| ---- | ---- |
| d2cmall-logger |  日志消息服务模块 |
| d2cmall-content |  CMS内容管理模块 |
| d2cmall-product |  商品服务模块 |
| d2cmall-member |  会员服务模块 |
| d2cmall-order |  订单服务模块 |
| d2cmall-similar |  商品相似推荐模块 |
| d2cmall-behavior |  用户行为分析模块 |
| d2cmall-report |  报表统计服务模块 |
| d2cmall-openapi |  开放API服务模块 |

### 系统附加组件服务
| d2cmall-services | 具体功能介绍 |
| ---- | ---- |
| d2cmall-services-quartz |  系统定时任务模块 |

### 对外的接口服务
| d2cmall-main | 具体功能介绍 |
| ---- | ---- | 
| d2cmall-main-web |  PC、WAP的门户接口，基于模板引擎 |
| d2cmall-main-rest |  APP、小程序的门户接口，JSON数据 |
| d2cmall-main-report |  平台会员销售分析报表的JSON接口 |
| d2cmall-main-openapi |  平台对外（商户）开放API的接口 |

### 对内的接口服务
| d2cmall-back |  具体功能介绍 |
| ---- | ---- |
| d2cmall-back-rest |  运营后台，商户后台，门店后台统一的JSON接口服务，角色权限控制 |
