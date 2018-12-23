package com.hhcf.backend.storm.bolt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

/**
 * 
 * @ClassName: ReportBolt
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月23日 下午2:31:13
 */
public class ReportBolt extends BaseRichBolt {
	private static final long serialVersionUID = 5263194017299466026L;
	/** 字段名 */
	public final static String FIELD_NAME = "word";
	public final static String COUNT_KEY = "count";
	/** 保存单词和对应的计数 */
	private Map<String, Long> counts = null;
	private OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.counts = new HashMap<String, Long>();
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String word = input.getStringByField(FIELD_NAME);
		Long count = input.getLongByField(COUNT_KEY);
		this.counts.put(word, count);
		// // 实时输出
		// System.out.println("结果:" + this.counts);
		// this.collector.ack(input);// TODO
	}

	/**
	 * 末端bolt，不需要发射数据流，这里无需定义
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	/**
	 * cleanup是IBolt接口中定义 Storm在终止一个bolt之前会调用这个方法
	 * 本例我们利用cleanup()方法在topology关闭时输出最终的计数结果
	 * 通常情况下，cleanup()方法用来释放bolt占用的资源，如打开的文件句柄或数据库连接
	 * 但是当Storm拓扑在一个集群上运行，IBolt.cleanup()方法不能保证执行（这里是开发模式，生产环境不要这样做）。
	 */
	@Override
	public void cleanup() {
		System.out.println("---------- 最后结果 -----------");
		List<String> keys = new ArrayList<String>();
		keys.addAll(this.counts.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			System.out.println("详细:" + key + ":" + this.counts.get(key));
		}
		System.out.println("-------------清空资源---------------");
	}

}
