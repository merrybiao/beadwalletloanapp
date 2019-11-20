package com.waterelephant.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticSearchUtils {

	private Settings setting;

	private Map<String, Client> clientMap = new ConcurrentHashMap<String, Client>();

	private ElasticSearchUtils() {
		init();
		// TO-DO 添加你需要的client到helper
	}

	public static final ElasticSearchUtils getInstance() {
		return ClientHolder.INSTANCE;
	}

	private static class ClientHolder {
		private static final ElasticSearchUtils INSTANCE = new ElasticSearchUtils();
	}

	/**
	 * 初始化默认的client
	 */
	public void init() {
		setting = Settings.settingsBuilder().put("cluster.name", SystemConstant.ES_CLUSTER_NAME)
				.put("client.transport.sniff", true).build();
		try {
			addClient(setting);
		} catch (UnknownHostException e) {
		}
	}

	public Client getClient() {
		return getClient(SystemConstant.ES_CLUSTER_NAME);
	}

	public Client getClient(String clusterName) {
		return clientMap.get(SystemConstant.ES_CLUSTER_NAME);
	}

	public void addClient(Settings setting) throws UnknownHostException {
		TransportClient client = TransportClient.builder().settings(setting).build();
		String[] ips = SystemConstant.ES_IP.split(",");
		for (String ip : ips) {
			client.addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName(ip), SystemConstant.ES_PORT));
		}
		clientMap.put(setting.get("cluster.name"), client);
	}

}
