package com.spark;
/*
public class Test {

	public static void main(String args[]){
		System.out.println(  Runtime.getRuntime().freeMemory()+"Heap size"+Runtime.getRuntime().totalMemory());
	}
	
}
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;

public final class Test {
   // private static final Logger LOGGER = Logger.getInstance(CassUtil.class);
    private final Session session;
    private final Cluster cluster;

    public Test(List<String> servers, String username, String password, String localDc, String keyspace) {
        cluster = Cluster.builder().addContactPoints(servers.toArray(new String[servers.size()]))
                                   .withRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE)
                                   .withPoolingOptions(new PoolingOptions())
                                   .withReconnectionPolicy(new ConstantReconnectionPolicy(100L))
                                   .withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc(localDc)
                                                                                             .withUsedHostsPerRemoteDc(3)
                                                                                             .build())
                                   .withCredentials(username, password)
                                   .build();
        session = cluster.connect(keyspace);
        LOGGER.logInfo("CONNECTED SUCCESSFULLY TO CASSANDRA CLUSTER: " + cluster.getClusterName());
    }

    public void shutdown() {
        LOGGER.logInfo("Shutting down the whole cassandra cluster");
        session.close();
        cluster.close();
    }

    public Session getSession() {
        return session;
    }

    public Cluster getCluster() {
        return cluster;
    }
}*/