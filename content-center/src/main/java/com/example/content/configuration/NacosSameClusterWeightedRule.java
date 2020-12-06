package com.example.content.configuration;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.alibaba.nacos.common.utils.MapUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * @author xianpeng.xia
 * on 2020/12/6 下午9:58
 */
@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            // 拿到配置文件中的clusterName
            String clusterName = nacosDiscoveryProperties.getClusterName();
            BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            //  想要请求的微服务的名称
            String name = baseLoadBalancer.getName();
            // 服务发现的相关api
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            // 1 找到指定服务的所有实力 A
            List<Instance> instances = namingService.selectInstances(name, true);
            // 2 过滤相同集群下的所有实例 B
            List<Instance> sameClusterInstances = instances.stream()
                .filter(instance -> Objects.equals(instance.getClusterName(), clusterName)
                ).collect(Collectors.toList());
            // 3 如果B是空就用A
            List<Instance> instancesToBeChosen = CollectionUtils.isEmpty(sameClusterInstances) ? instances : sameClusterInstances;
            // 4 基于权重的负载均衡算法 返回一个实例
            Instance instance = ExtendBalance._getHostByRandomWeight(instancesToBeChosen);
            log.info("instance = {}", instance);
            return new NacosServer(instance);
        } catch (NacosException e) {
            log.error("NacosException ", e);
        }
        return null;
    }


}

class ExtendBalance extends Balancer {

    public static Instance _getHostByRandomWeight(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}
