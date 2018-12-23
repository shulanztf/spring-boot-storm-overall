package com.hhcf.backend.storm.spout;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

/**
 * 
 * @ClassName: SentenceSpout
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月23日 下午2:23:15
 */
public class SentenceSpout extends BaseRichSpout {
	private static final long serialVersionUID = 3040352907878756005L;
	private static Logger logger = Logger.getLogger(SentenceSpout.class);
	// BaseRichSpout是ISpout接口和IComponent接口的简单实现，接口对用不到的方法提供了默认的实现
	private SpoutOutputCollector collector;
	/** 字段名 */
	public final static String FIELD_NAME = "word";
	/** 偏移量 */
	private int index = 0;
	/** 数据源 */
	private String[] sentences = { "my name is soul", "im a boy", "i have a dog", "my dog has fleas",
			"my girl friend is beautiful" };

	/**
	 * open()方法中是ISpout接口中定义，在Spout组件初始化时被调用。
	 * open()接受三个参数:一个包含Storm配置的Map,一个TopologyContext对象，提供了topology中组件的信息,
	 * SpoutOutputCollector对象提供发射tuple的方法。
	 * 在这个例子中,我们不需要执行初始化,只是简单的存储在一个SpoutOutputCollector实例变量。
	 */
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	/**
	 * nextTuple()方法是任何Spout实现的核心。 Storm调用这个方法，向输出的collector发出tuple。
	 * 在这里,我们只是发出当前索引的句子，并增加该索引准备发射下一个句子。
	 */
	@Override
	public void nextTuple() {
		System.out.println("日志打印SpoutNextTuple执行统计：SentenceSpout.nextTuple");
		// this.collector.emit(new Values(sentences[index]), sentences[index]);
		if (index < sentences.length) {
			this.collector.emit(new Values(sentences[index]));
			// System.out
			// .println("日志打印：SentenceSpout.nextTuple," + index + "," +
			// sentences.length + "," + sentences[index]);
			index++;
		}
		Utils.sleep(1);
	}

	/**
	 * declareOutputFields是在IComponent接口中定义的，所有Storm的组件（spout和bolt）都必须实现这个接口
	 * 用于告诉Storm流组件将会发出那些数据流，每个流的tuple将包含的字段
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELD_NAME));
	}

}
