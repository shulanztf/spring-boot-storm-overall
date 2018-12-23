package com.hhcf.backend.storm.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * 
 * @ClassName: SplitSentenceBolt
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月23日 下午2:23:44
 */
public class SplitSentenceBolt extends BaseRichBolt {
	private static final long serialVersionUID = 2707357609501677986L;
	/** 字段名 */
	public final static String FIELD_NAME = "word";
	private OutputCollector collector;

	/**
	 * prepare()方法类似于ISpout 的open()方法。 这个方法在blot初始化时调用，可以用来准备bolt用到的资源,比如数据库连接。
	 * 本例子和SentenceSpout类一样,SplitSentenceBolt类不需要太多额外的初始化,
	 * 所以prepare()方法只保存OutputCollector对象的引用。
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	/**
	 * SplitSentenceBolt核心功能是在类IBolt定义execute()方法，这个方法是IBolt接口中定义。
	 * 每次Bolt从流接收一个订阅的tuple，都会调用这个方法。 本例中,收到的元组中查找“sentence”的值,
	 * 并将该值拆分成单个的词,然后按单词发出新的tuple。
	 */
	@Override
	public void execute(Tuple input) {
		String sentence = input.getStringByField(FIELD_NAME);
		String[] words = sentence.split(" ");
		for (String word : words) {
			this.collector.emit(new Values(word));
		}
	}

	/**
	 * plitSentenceBolt类定义一个元组流,每个包含一个字段(“word”)。
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(FIELD_NAME));
	}

}
