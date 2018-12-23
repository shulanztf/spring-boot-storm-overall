package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.hhcf.backend.storm.topo.GameTopoStarter;
import com.hhcf.backend.system.config.SpringBeanContext;

/**
 * 
 * @ClassName: Appclition
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月21日 下午1:48:01
 */
@SpringBootApplication
public class Appclition implements CommandLineRunner {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Appclition.class, args);
		// storm配置
//		GetSpringBean beans = new GetSpringBean();
//		beans.setApplicationContext(context);
//		WordCountTopology app = GetSpringBean.getBean(WordCountTopology.class);
//		app.runStorm(args);

		SpringBeanContext beans = new SpringBeanContext();
		beans.setApplicationContext(context);
		GameTopoStarter topo = SpringBeanContext.getBean(GameTopoStarter.class);
		topo.runStorm(args);
	}

	public void run(String... args) throws Exception {
		System.out.println(args + ",aaaaaaaaaaaaaaa");

	}

}
