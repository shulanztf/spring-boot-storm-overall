package com.hhcf.backend.system.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName: GetSpringBean
 * @Description:动态获取bean的代码如下:
 * @author: zhaotf
 * @date: 2018年12月8日 上午11:11:28
 * @see {@linkplain https://www.cnblogs.com/xuwujing/archive/2018/05/10/9021561.html}
 */
public class GetSpringBean implements ApplicationContextAware {
	private static ApplicationContext context;

	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static <T> T getBean(Class<T> c) {
		return context.getBean(c);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (applicationContext != null) {
			context = applicationContext;
		}
	}

}
