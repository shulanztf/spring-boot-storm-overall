package com.hhcf.backend.storm.topo;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hhcf.backend.storm.bolt.AccumulatorBolt;
import com.hhcf.backend.storm.spout.GameSpout;
import com.hhcf.backend.system.constant.Constants;

/**
 * 
 * @ClassName: GameTopoStarter
 * @Description:摇摇小游戏启动topology
 * @author: zhaotf
 * @date: 2018年12月23日 下午3:54:07
 */
@Component
public class GameTopoStarter {
	private static Logger logger = Logger.getLogger(GameTopoStarter.class);
	

	/**
	 * 两种方法都编写了，通过主方法的args参数来进行控制。 Topology相关的配置说明在代码中的注释写的很详细了
	 * 
	 * @param args
	 */
	public void runStorm(String[] args) {
		TopologyBuilder tb = new TopologyBuilder();
		tb.setSpout(Constants.KAFKA_SPOUT, new GameSpout(), 1); // spout，1个Executeor(线程)，默认一个
		tb.setBolt(Constants.ACCUMULATOR_BOLT, new AccumulatorBolt(), 1) // 1个Executeor(线程/并行度)
				.setNumTasks(1) // 2个AccumulatorBolt实例，累加次数
				.shuffleGrouping(Constants.KAFKA_SPOUT);// 随机分组
//		tb.setBolt(Constants.USER_INFO_BOLT, new UserInfoBolt(), 1)// 1个Executeor(线程/并行度)
//				.setNumTasks(2)// 2个UserInfoBolt实例，用户信息处理
//				.shuffleGrouping(Constants.KAFKA_SPOUT);// 随机分组

		// 参数配置
		Config conf = new Config();
		// conf.setNumWorkers(1);// 设置一个work，默认也是一个

		try {
			if (args != null && args.length > 0) {
				logger.info("运行远程模式");
				StormSubmitter.submitTopology(args[0], conf, tb.createTopology());
			} else {
				logger.info("运行本地模式");
				LocalCluster ls = new LocalCluster();
				ls.submitTopology("SpringBootTopologyApp", conf, tb.createTopology());
			}
			logger.info("storm启动成功...");
		} catch (Exception e) {
			logger.error("storm作业提交异常:" + JSON.toJSONString(args), e);
			System.exit(1);// 退出
		}
	}

}
