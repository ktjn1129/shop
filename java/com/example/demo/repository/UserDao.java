package com.example.demo.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.User;

/**
 * ユーザーDao
 * 
 * @author koto
 */
public interface UserDao {
	
	// IDを基にユーザー情報を取得
	public User findById(int id) throws DataAccessException;
	
	// Emailを基にユーザー情報を取得
	public User findByEmail(String email) throws DataAccessException;
	
	// ユーザー名を基にユーザー情報を取得
	public User findByUsername(String username) throws DataAccessException;
	
	// ユーザーを新規登録
	public void register(User user) throws DataAccessException;
	
	// ユーザー情報を更新
	public void update(User user) throws DataAccessException;
}