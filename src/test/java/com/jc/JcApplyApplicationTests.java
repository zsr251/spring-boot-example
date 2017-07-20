package com.jc;

import com.jc.constant.DepartmentEnum;
import com.jc.model.Employee;
import com.jc.service.ActivityService;
import com.jc.service.ApplyService;
import com.jc.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JcApplyApplicationTests {
	@Autowired
	private ApplyService applyService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void contextLoads() {
	}
//	@Test
//	@Transactional
	public void testAddEmp(){
		Employee employee = employeeService.addEmployee(DepartmentEnum.技术部,"朱圣然","jasonzhu");
		Assert.assertNotNull(employee);
	}
//	@Test
	public void testRedis(){
		stringRedisTemplate.opsForValue().set("a","1");
		Assert.assertTrue( stringRedisTemplate.hasKey("a"));
		Assert.assertEquals(stringRedisTemplate.opsForValue().get("a"),"1");
	}

}
