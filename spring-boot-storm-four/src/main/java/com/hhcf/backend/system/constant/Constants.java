package com.hhcf.backend.system.constant;

import org.springframework.context.annotation.Configuration;

/**
 * 
 * @ClassName: Constants
 * @Description:定数类
 * @author: zhaotf
 * @date: 2018年12月8日 上午11:32:35
 */
@Configuration
public class Constants {
	/** kafka数据摘取spout */
	public static final String KAFKA_SPOUT = "KAFKA_SPOUT";
	/** storm spout-1类id */
	public static final String INSERT_BOLT = "INSERT_BOLT";
	/** 累加器bolt */
	public static final String ACCUMULATOR_BOLT = "ACCUMULATOR_BOLT";
	/** 用户信息处理bolt */
	public static final String USER_INFO_BOLT = "USER_INFO_BOLT";
	/** storm 字段key */
	public final static String FIELD = "insert";
}
