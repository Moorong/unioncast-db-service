package com.unioncast;

import com.unioncast.db.DbRestApiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DbRestApiApplication.class)
@WebAppConfiguration
public class BaseDbRestApiApplicationTest {

	@Test
	public void contextLoads() {
	}

}
