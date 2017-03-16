package com.tang.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.tang.bean.User;
public class UserRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet set, int index) throws SQLException {
		User person = new User(set.getInt("id"), set.getString("username"), set.getString("password"));
		return person;
	}

}
