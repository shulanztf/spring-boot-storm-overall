package com.hhcf.backend.system.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @ClassName: BaseConfig
 * @Description:
 * @author: zhaotf
 * @date: 2018年12月17日 下午3:41:06
 */
@Configuration
public class BaseConfig {
	private static Logger logger = Logger.getLogger(BaseConfig.class);
	@Value("${kafka.servers}")
	private String servers;
	@Value("${spring.redis.database}")
	private int dbIndex;
	@Value("${spring.redis.host}")
	private String hostName;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.timeout}")
	private int timeOut;

	// @Bean
	// public Map<String, Object> getProducerConfig() {
	// Map<String, Object> conf = new HashMap<String, Object>();
	// conf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.servers);
	// conf.put(ProducerConfig.RETRIES_CONFIG, 0);
	// conf.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
	// conf.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	// conf.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	// conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	// StringSerializer.class);
	// conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	// StringSerializer.class);
	// logger.info("ProducerConfig 初始化完成.....");
	// return conf;
	// }

	// @Bean
	// public DefaultKafkaProducerFactory<String, String>
	// getKafkaProducerFactory() {
	// DefaultKafkaProducerFactory<String, String> factory = new
	// DefaultKafkaProducerFactory<String, String>(
	// getProducerConfig());
	// logger.info("DefaultKafkaProducerFactory<String, String> 初始化完成.....");
	// return factory;
	// }

	// @Bean
	// public KafkaTemplate<String, String> getKafkaTemplate() {
	// KafkaTemplate<String, String> template = new KafkaTemplate<String,
	// String>(getKafkaProducerFactory());
	// logger.info("DefaultKafkaProducerFactory<String, String> 初始化完成.....");
	// return template;
	// }

	@Bean
	public RedisConnectionFactory connectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setDatabase(dbIndex);
		factory.setHostName(hostName);
		factory.setPort(port);
		factory.setPassword(password);
		factory.setTimeout(timeOut);
		return factory;
	}

//	@Bean("stringRedisTemplate")
//	public StringRedisTemplate stringRedisTemplate() {
//		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//		stringRedisTemplate.setConnectionFactory(connectionFactory());
//		return stringRedisTemplate;
//	}
//
//	@Bean("redisTemplate")
//	public RedisTemplate<String, Object> redisTemplate() {
//		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//		template.setConnectionFactory(connectionFactory());
//		setSerializer(template);// 设置序列化工具
//		template.afterPropertiesSet();
//		return template;
//	}
//
//	private void setSerializer(RedisTemplate<String, Object> template) {
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		template.setValueSerializer(jackson2JsonRedisSerializer);
//		template.setHashKeySerializer(jackson2JsonRedisSerializer);// 乱码解决
//		template.setHashValueSerializer(jackson2JsonRedisSerializer);
//	}

	// /**
	// * 自定义异步线程池
	// *
	// * @return
	// */
	// @Bean
	// public AsyncTaskExecutor taskExecutor() {
	// ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	// executor.setThreadNamePrefix("Anno-Executor");// 线程名前缀
	// executor.setCorePoolSize(10);// 核心线程数
	// executor.setMaxPoolSize(20);// 最大线程数
	// executor.setKeepAliveSeconds(10);// 线程最大空闲时间
	// executor.setQueueCapacity(200);// 队列大小
	// executor.setRejectedExecutionHandler(new
	// ThreadPoolExecutor.DiscardPolicy()); // 设置拒绝策略
	// return executor;
	// }
}
