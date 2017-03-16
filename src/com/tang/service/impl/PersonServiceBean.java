package com.tang.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tang.bean.Person;
import com.tang.service.PersonService;

public class PersonServiceBean implements PersonService {

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Person person) {
		
		
		
		
		
		
		jdbcTemplate.update(
				"insert into person(name,age,sex)values(?,?,?)",
				new Object[] { person.getName(), person.getAge(),
						person.getSex() }, new int[] { java.sql.Types.VARCHAR,
						java.sql.Types.INTEGER, java.sql.Types.VARCHAR });

	}

	@Override
	public void update(Person person) {
		jdbcTemplate.update(
				"update person set name=?,age=?,sex=? where id=?",
				new Object[] { person.getName(), person.getAge(),
						person.getSex(), person.getId() }, new int[] {
						java.sql.Types.VARCHAR, java.sql.Types.INTEGER,
						java.sql.Types.VARCHAR, java.sql.Types.INTEGER });

	}

	@Override
	public Person getPerson(Integer id) {
		Person person = (Person) jdbcTemplate.queryForObject(
				"select * from person where id=?", new Object[] { id },
				new int[] { java.sql.Types.INTEGER }, new PersonRowMapper());
		return person;
	}

	@Override
	public List<Person> getPerson() {
		List<Person> list = jdbcTemplate.query("select * from person", new PersonRowMapper());
		   return list;
	}

	@Override
	public void delete(Integer id) {
		 jdbcTemplate.update("delete from person where id = ?", new Object[] { id },
				       new int[] { java.sql.Types.INTEGER });

	}

}
