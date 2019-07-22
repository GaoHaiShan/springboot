 业务需求：sbm2项目主要实现了在线知识管理、查询业务，可以使知识体系有整体的理解与总结，简易的技术交流平台。类似于csdn。

 技术实现：通过springboot+mybatis+thelef实施基础开发，springcloud eureka 服务注册中心，feign实现服务调用,负载均衡根据默认配置，redis 实现二级缓存，以及分页查询效果。rabbitmq解决数据一致问题。
