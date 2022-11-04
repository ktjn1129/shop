package com.example.demo.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.User;

/**
 * ユーザーDao
 * 
 * @author koto
 */
public interface UserDao {
	
	public User findById(int id) throws DataAccessException;
	
	public User findByEmail(String email) throws DataAccessException;
	
	public User findByUsername(String username) throws DataAccessException;
	
	public Integer regist(User user) throws DataAccessException;
	
	public User update(User user) throws DataAccessException;
}