package com.qf.tempspringrubbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TempSpringRubbitmqApplicationTests {

	@Autowired
	private RabbitTemplate template;

	@Test
	public void contextLoads() {
		template.convertAndSend("simple","hello,simple-queque");
	}

}
