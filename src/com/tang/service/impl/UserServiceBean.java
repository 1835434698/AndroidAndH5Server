package com.tang.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.tang.bean.User;
import com.tang.service.UserService;

public class UserServiceBean implements UserService {

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(User user) {
		jdbcTemplate.update(
				"insert into user(username,password)values(?,?)",
				new Object[] { user.getUsername(),
						user.getPassword() }, new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });

	}

	@Override
	public void update(User user) {
		jdbcTemplate.update(
				"update user set password=?where username=?",
				new Object[] { user.getPassword(), user.getUsername()},
				new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });

	}


	@Override
	public boolean query(User user) {
		
		try {
			User result = (User)jdbcTemplate.queryForObject("select * from user where username=?", new Object[]{user.getUsername()}, new int[] {java.sql.Types.VARCHAR }, new UserRowMapper());
			
			if (result.getPassword().equals(user.getPassword())) {
				
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void delete(User uer) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public User getUser(Integer id) {
//		User person = (User) jdbcTemplate.queryForObject(
//				"select * from person where id=?", new Object[] { id },
//				new int[] { java.sql.Types.INTEGER }, new PersonRowMapper());
//		return person;
//	}
//
//	@Override
//	public List<User> getUser() {
//		List<User> list = jdbcTemplate.query("select * from person", new PersonRowMapper());
//		   return list;
//	}

//	@Override
//	public void delete(Integer id) {
//		 jdbcTemplate.update("delete from user where id = ?", new Object[] { id },
//				       new int[] { java.sql.Types.INTEGER });
//
//	}

}
