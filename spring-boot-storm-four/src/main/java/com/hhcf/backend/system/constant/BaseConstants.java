package com.hhcf.backend.system.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: BaseConstants
 * @Description:常量配置类，取配置文件
 * @author: zhaotf
 * @date: 2018年12月17日 下午5:16:35
 */
@Component
public class BaseConstants {
	@Value("${kafka.topicName}")
	private String topicName;

	@Value("${kafka.servers}")
	private String servers;

	@Value("${kafka.maxPollRecords}")
	private int maxPollRecords;

	@Value("${kafka.commitRule}")
	private String commitRule;

	@Value("${kafka.autoCommit}")
	private String autoCommit;

	@Value("${kafka.groupId}")
	private String groupId;
	/** 已获取用户信息hash KEY,game-user-info */
	@Value("${game.user.info.hash.key}")
	private String gameUserInfo;
	/** 用户次数key前缀user-count- */
	@Value("${user.count.key.prefix}")
	private String userCountPrefix;

	/**
	 * 获取topicName
	 * 
	 * @return topicName
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * 获取servers
	 * 
	 * @return servers
	 */
	public String getServers() {
		return servers;
	}

	/**
	 * 获取maxPollRecords
	 * 
	 * @return maxPollRecords
	 */
	public int getMaxPollRecords() {
		return maxPollRecords;
	}

	/**
	 * 获取commitRule
	 * 
	 * @return commitRule
	 */
	public String getCommitRule() {
		return commitRule;
	}

	/**
	 * 获取autoCommit
	 * 
	 * @return autoCommit
	 */
	public String getAutoCommit() {
		return autoCommit;
	}

	/**
	 * 获取groupId
	 * 
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	public String getGameUserInfo() {
		return gameUserInfo;
	}

	public String getUserCountPrefix() {
		return userCountPrefix;
	}

}
