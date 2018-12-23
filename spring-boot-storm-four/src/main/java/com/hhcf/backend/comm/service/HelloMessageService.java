package com.hhcf.backend.comm.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName: HelloMessageService
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月23日 上午11:52:35
 * @see {@linkplain https://blog.csdn.net/orichisonic/article/details/79962065}
 */
@Service
public class HelloMessageService {
//	private static Logger logger = LoggerFactory.getLogger(HelloMessageService.class);
	private static Logger logger = Logger.getLogger(HelloMessageService.class); 
//	private static Logger logger = LogManager.getLogger(HelloMessageService.class); 
//	static Log4jLoggerFactory logger = Log4jLoggerFactory;
	
	@Value("${name:unknown}")
	private String name;

	public String getMessage() {
		logger.info("aaaaaaaaaaaaaaaaaaaa");
		return getMessage(name);
	}

	public String getMessage(String name) {
		logger.info("ddddddddddddddddddds");
		return "Hello " + name;
	}
}
