package com.hhcf.backend.storm.bolt;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.hhcf.backend.system.constant.Constants;

/**
 * @ClassName: AccumulatorBolt
 * @Description:摇摇小游戏，次数累加器
 * @author: zhaotf
 * @date: 2018年12月17日 下午4:57:42
 * @see {@linkplain https://blog.csdn.net/weixin_40294332/article/details/79849546
 *      }
 */
public class AccumulatorBolt extends BaseRichBolt {
	private static final long serialVersionUID = -4338379546708600419L;
	private static Logger logger = LoggerFactory.getLogger(AccumulatorBolt.class);
	// private RedisTemplate<String, Object> redisTemplate;
	private StringRedisTemplate stringRedisTemplate;
	protected ConfigurableApplicationContext applicationContext;

	/**
	 * 初始化
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		try {
			if (applicationContext == null) {
				applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
			}
			logger.info("bolt端，spring上下文加载:{}", applicationContext);
			// redisTemplate = (RedisTemplate<String, Object>)
			// applicationContext.getBean("redisTemplate");
			stringRedisTemplate = (StringRedisTemplate) applicationContext.getBean("stringRedisTemplate");
			

		} catch (Exception e) {
			logger.error("初始化配置信息失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 次数合计，并，存入redis，消息格式：token,次数
	 */
	@Override
	public void execute(Tuple input) {
		String msg = input.getStringByField(Constants.FIELD);
		try {
			String[] arr = StringUtils.split(msg, ",");
			String token = arr[0];
			Integer count = Integer.valueOf(arr[1]);// 单次摇动次数

			logger.info("写入数据的bolt：" + msg);
			this.stringRedisTemplate.opsForValue().increment(token, count.longValue());// 累加到redis中
		} catch (Exception e) {
			logger.error("次数合计据处理异常:" + msg, e);
		}
	}

	/**
	 * 末端节点，不需要向后发射/声明
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

}
