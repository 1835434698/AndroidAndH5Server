package com.tang.service;

import java.util.List;

import com.tang.bean.Person;

public interface PersonService {

	public abstract void save(Person person);
	
	public abstract void update(Person person);
	
	public abstract Person getPerson(Integer id);
	
	public abstract List<Person> getPerson();
	
	public abstract void delete(Integer id);
	
	
}
