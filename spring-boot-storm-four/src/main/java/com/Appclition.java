package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.hhcf.backend.comm.service.HelloMessageService;

/**
 * 
 * @ClassName: Appclition
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月21日 下午1:48:01
 */
@SpringBootApplication
public class Appclition implements CommandLineRunner {
    @Autowired
    private HelloMessageService helloService;
    
	public static void main(String[] args) {
		SpringApplication.run(Appclition.class, args);

		// ConfigurableApplicationContext context =
		// SpringApplication.run(Appclition.class, args);
		// // storm配置
		// GetSpringBean beans = new GetSpringBean();
		// beans.setApplicationContext(context);
		// TopologyApp app = GetSpringBean.getBean(TopologyApp.class);
		// app.runStorm(args);

		// disabled banner, don't want to see the spring logo

	}

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(args + ",aaaaaaaaaaaaaaa");
		// for (int i = 0; i < 100; i++) {
		// System.out.println("aaa:" + System.currentTimeMillis());
		// Thread.sleep(1000 * 1);
		// }
		if (args.length > 0) {
			System.out.println(helloService.getMessage(args[0].toString()));
		} else {
			System.out.println(helloService.getMessage());
		}

	}

}
