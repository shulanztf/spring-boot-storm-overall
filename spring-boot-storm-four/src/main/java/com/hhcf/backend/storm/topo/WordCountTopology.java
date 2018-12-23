package com.hhcf.backend.storm.topo;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.springframework.stereotype.Component;

import com.hhcf.backend.storm.bolt.ReportBolt;
import com.hhcf.backend.storm.bolt.SplitSentenceBolt;
import com.hhcf.backend.storm.bolt.WordCountBolt;
import com.hhcf.backend.storm.spout.SentenceSpout;

/**
 * 
 * @ClassName: WordCountTopology
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月23日 下午2:21:01
 */
@Component("wordCountTopology")
public class WordCountTopology {
	private static Logger logger = Logger.getLogger(WordCountTopology.class);
	private static final String SENTENCE_SPOUT_ID = "sentence-spout";
	private static final String SPLIT_BOLT_ID = "split-bolt";
	private static final String COUNT_BOLT_ID = "count-bolt";
	private static final String REPORT_BOLT_ID = "report-bolt";
	private static final String TOPOLOGY_NAME = "word-count-topology";
	/** 字段名 */
	public final static String FIELD_NAME = "word";
	public final static String COUNT_KEY = "count";

	public static void main(String[] args) {
		WordCountTopology topo = new WordCountTopology();
		topo.runStorm(args);
	}

	public void runStorm(String[] args) {
		TopologyBuilder tb = new TopologyBuilder();
		tb.setSpout(SENTENCE_SPOUT_ID, new SentenceSpout(), 1); // 3、注册一个spout,设置两个Executeor(线程)，默认一个
		tb.setBolt(SPLIT_BOLT_ID, new SplitSentenceBolt(), 1)// 2个线程
				.setNumTasks(2)// 4个对象实例
				.shuffleGrouping(SENTENCE_SPOUT_ID);// 随机分发
		tb.setBolt(COUNT_BOLT_ID, new WordCountBolt(), 1)// 4个线程
				.setNumTasks(2)// 2个对象实例
				.fieldsGrouping(SPLIT_BOLT_ID, new Fields(FIELD_NAME));// 相同的字段，分到一bolt

		// 6、globalGrouping是把WordCountBolt发射的所有tuple路由到唯一的ReportBolt
		tb.setBolt(REPORT_BOLT_ID, new ReportBolt()).globalGrouping(COUNT_BOLT_ID);

		// 7、Config类是一个HashMap<String,Object>的子类，用来配置topology运行时的行为
		Config conf = new Config();
		// config.setNumWorkers(2);

		if (args != null && args.length > 0) {
			logger.info("运行远程模式");
			try {
				StormSubmitter.submitTopology(args[0], conf, tb.createTopology());
			} catch (Exception e) {
				logger.error("提交storm作业异常", e);
			}
		} else {
			logger.info("运行本地模式");
			LocalCluster lc = new LocalCluster();
			lc.submitTopology(TOPOLOGY_NAME, conf, tb.createTopology());
			Utils.sleep(1000 * 20);
			lc.killTopology(TOPOLOGY_NAME);
			lc.shutdown();
			logger.info("启动类结束.....");
		}
	}

}
