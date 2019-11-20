package com.waterelephant.base;

/**
 * 发送消息队列服务
 * 
 * @author 崔雄健
 * @date 2017年3月17日
 * @description 发送消息队列服务
 */
// @Service("mqProducer")
// public class MqProducer {
// private static final Logger logger = LoggerFactory.getLogger(MqProducer.class);
// // private String namesrvAddr = "139.224.17.43:9876";
// // private String producerGroup = "loanapp_1";
// //
// // private String instanceName = "loanapp_1";
// // private String topic = "top_1";
// // private String tags = "sendStatus";
// @Value("#{settings['namesrvAddr']}")
// private String namesrvAddr;
// @Value("#{settings['producerGroup']}")
// private String producerGroup;
// @Value("#{settings['instanceName']}")
// private String instanceName;
// @Value("#{settings['topic']}")
// private String topic;
// @Value("#{settings['tags']}")
// private String tags;
//
// private DefaultMQProducer producer = null;
//
// @PostConstruct
// public void start() throws Exception {
// logger.info("初始化生产者开始……");
// producer = new DefaultMQProducer();
// producer.setNamesrvAddr(namesrvAddr);
// producer.setProducerGroup(producerGroup);
// producer.setInstanceName(instanceName);
// producer.setMaxMessageSize(2 * 1024 * 1024);// 发送报文最大2M
// producer.start();
// logger.info("初始化生产者完成");
// }
//
// @PreDestroy
// public void destroy() {
// logger.info("关闭rocketmq连接。");
// try {
// producer.shutdown();
// } catch (Exception e) {
// logger.error("关闭rocketmq异常。", e);
// }
// }
//
// /**
// * 向消息中间件投递普通消息
// *
// * @param key
// * @param tag
// * @param msg
// */
// public void sendMsg(String key, String msg) {
// try {
// Message message = new Message(topic, tags, key, msg.getBytes("utf-8"));
// SendResult result = producer.send(message);
// logger.info("发送MQ状态==> key：" + key + ",状态：" + result.getSendStatus());
// } catch (Exception e) {
// logger.error("投递消息异常。", e);
// }
// }
//
// }
