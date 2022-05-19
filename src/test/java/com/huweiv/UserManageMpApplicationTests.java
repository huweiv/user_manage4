package com.huweiv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huweiv.dao.UserDao;
import com.huweiv.domain.Role;
import com.huweiv.dao.RoleDao;
import com.huweiv.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserManageMpApplicationTests {


	@Autowired
	UserDao userDao;
	@Test
	void contextLoads() {

		LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
		lqw.eq(User::getUsername, "huweiv");
		User userInfo = userDao.selectOne(lqw);
		System.out.println(userInfo);
	}

}
