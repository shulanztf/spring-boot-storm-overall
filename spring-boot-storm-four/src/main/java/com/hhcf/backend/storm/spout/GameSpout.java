package com.hhcf.backend.storm.spout;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hhcf.backend.system.config.SpringBeanContext;
import com.hhcf.backend.system.constant.BaseConstants;
import com.hhcf.backend.system.constant.Constants;

/**
 * 
 * @ClassName: GameSpout
 * @Description:摇摇小游戏spout
 * @author: zhaotf
 * @date: 2018年12月17日 下午4:54:51
 */
public class GameSpout extends BaseRichSpout {
	private static final long serialVersionUID = -1944420250142434799L;
	private static final Logger logger = LoggerFactory.getLogger(GameSpout.class);
	private SpoutOutputCollector collector;
	private KafkaConsumer<String, String> consumer;
	private ConsumerRecords<String, String> msgList;
	private BaseConstants app;

	/**
	 * 初始化
	 */
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.app = SpringBeanContext.getBean(BaseConstants.class);
		kafkaInit();
		this.collector = collector;
	}

	/**
	 * 拉取kafka消息，消息格式：token,次数
	 */
	@Override
	public void nextTuple() {
		while (this.consumer != null) {
			try {
				this.msgList = consumer.poll(100);// 从kafka拉取数据
				if (msgList.count() > 0) {
					logger.info("Spout拉取到的数据:" + msgList.count() + "," + JSON.toJSONString(msgList.iterator()));
					String msg = StringUtils.EMPTY;
					for (ConsumerRecord<String, String> record : msgList) {
						msg = record.value();
						if (StringUtils.isBlank(msg)) {
							continue;
						}
						logger.info("Spout发射的数据:" + msg);
						this.collector.emit(new Values(msg));
					}
					consumer.commitAsync();// 手工控制,同步或异步的确认

				} else {
					Thread.sleep(1000 * 2);
					logger.info("未拉取到数据...");
				}
			} catch (Exception e) {
				logger.error("消息队列处理异常!", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("延迟异常:" + e1.getLocalizedMessage(), e1);
				}
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.FIELD));
	}

	/**
	 * 初始化kafka配置
	 */
	public void kafkaInit() {
		Properties props = new Properties();
		props.put("bootstrap.servers", app.getServers());
		props.put("max.poll.records", app.getMaxPollRecords());
		props.put("enable.auto.commit", app.getAutoCommit());
		props.put("group.id", app.getGroupId());
		props.put("auto.offset.reset", app.getCommitRule());
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		consumer = new KafkaConsumer<>(props);
		String topic = app.getTopicName();
		this.consumer.subscribe(Arrays.asList(topic));
		logger.info("消息队列[" + topic + "] 开始初始化...");
	}
}
