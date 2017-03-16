package com.tang.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tang.bean.Person;

public class PersonRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet set, int index) throws SQLException {
		Person person = new Person(set.getInt("id"), set.getString("name"),
				set.getInt("age"), set.getString("sex"));
		return person;
	}

}
