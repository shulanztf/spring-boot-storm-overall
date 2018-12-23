package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 
 * @ClassName: Appclition
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月21日 下午1:48:01
 */
@SpringBootApplication
public class Appclition {

	public static void main(String[] args) {
		SpringApplication.run(Appclition.class, args);

		// ConfigurableApplicationContext context =
		// SpringApplication.run(Appclition.class, args);
		// // storm配置
		// GetSpringBean beans = new GetSpringBean();
		// beans.setApplicationContext(context);
		// TopologyApp app = GetSpringBean.getBean(TopologyApp.class);
		// app.runStorm(args);
	}

}
