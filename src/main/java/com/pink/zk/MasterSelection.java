package com.pink.zk;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.Participant;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class MasterSelection {

	// private CuratorFramework client;

	private String serviceId;
	private String latchPath;

	private LeaderLatch leaderLatch;

	public MasterSelection(CuratorFramework client, String serviceId, String latchPath) {
		this.serviceId = serviceId;
		this.latchPath = latchPath;
		leaderLatch = new LeaderLatch(client, latchPath, serviceId);
	}

	public void start() throws Exception {

		leaderLatch.start();
	}

	public void close() throws IOException {
		leaderLatch.close();

	}

	public void blockUntilisLeader() throws Exception {
		leaderLatch.await();
	}

	public Participant currentLeader() throws Exception {
		return leaderLatch.getLeader();
	}

	public boolean isLeader()
	{
		return leaderLatch.hasLeadership();
	}
	
	
	public static void main(String[] args) throws Exception
	{
		String serviceId = args[0];
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 1000, 1000, new ExponentialBackoffRetry(1000, Integer.MAX_VALUE));
		
		MasterSelection masterChecker = new MasterSelection(client, serviceId, "/streaming_service");
		client.start();
		masterChecker.start();
		while(true)
		{
			if(masterChecker.isLeader())
			{
				System.out.println(serviceId + " is master");
			} else {
				System.out.println(serviceId + " is slave");
			}
			Thread.sleep(5* 1000L);
		}
	
	}
	

}
