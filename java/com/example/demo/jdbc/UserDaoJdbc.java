package com.example.demo.jdbc;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
import com.example.demo.repository.UserDao;

/**
 * ユーザーDao JDBCクラス
 * 
 * @author koto
 */
@Repository("UserDaoJdbc")
public class UserDaoJdbc implements UserDao {
	
	/**
	 * JDBC Templateの紐付け
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * ユーザーIDを基にユーザー情報を取得
	 * 
	 * @param id ユーザーID
	 * @return user 取得したユーザー情報
	 */
	@Override
	public User findById(int id) throws DataAccessException {
		
		String sql = "SELECT * FROM users" + " WHERE id = ?";
		
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
		
		User user = new User();
		user.setId((Integer) map.get("id"));
		user.setEmail((String) map.get("email"));
		user.setPassword((String) map.get("password"));
		user.setName((String) map.get("name"));
		user.setPostalCode((String) map.get("postal_code"));
		user.setAddress((String) map.get("address"));
		user.setPhoneNumber((String) map.get("phone_number"));
		user.setAuthority((Integer) map.get("authority"));
		user.setDeleteFlag((Integer) map.get("delete_flag"));
		user.setInsertDate((LocalDateTime) map.get("insert_date"));
		user.setUsername((String)map.get("username"));
		
		return user;
	}

	/**
	 * Emailを基にユーザー情報を取得
	 * 
	 * @param email Email
	 * @return user 取得したユーザー情報
	 */
	@Override
	public User findByEmail(String email) throws DataAccessException {
		
		String sql = "SELECT * FROM users" + " WHERE email = ?";
		
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap(sql, email);
			User user = new User();
			user.setId((Integer) map.get("id"));
			user.setEmail((String) map.get("email"));
			user.setPassword((String) map.get("password"));
			user.setName((String) map.get("name"));
			user.setPostalCode((String) map.get("postal_code"));
			user.setAddress((String) map.get("address"));
			user.setPhoneNumber((String) map.get("phone_number"));
			user.setAuthority((Integer) map.get("authority"));
			user.setDeleteFlag((Integer) map.get("delete_flag"));
			user.setInsertDate((LocalDateTime) map.get("insert_date"));
			user.setUsername((String)map.get("username"));
			
			return user;
			
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}
	
	/**
	 * ユーザー名を基にユーザー情報を取得
	 * 
	 * @param username ユーザー名
	 * @return user 取得したユーザー情報
	 */
	@Override
	public User findByUsername(String username) throws DataAccessException {
		
		String sql = "SELECT * FROM users" + " WHERE username = ?";
		
		try {
			Map<String, Object> map = jdbcTemplate.queryForMap(sql, username);
			User user = new User();
			user.setId((Integer) map.get("id"));
			user.setEmail((String) map.get("email"));
			user.setPassword((String) map.get("password"));
			user.setName((String) map.get("name"));
			user.setPostalCode((String) map.get("postal_code"));
			user.setAddress((String) map.get("address"));
			user.setPhoneNumber((String) map.get("phone_number"));
			user.setAuthority((Integer) map.get("authority"));
			user.setDeleteFlag((Integer) map.get("delete_flag"));
			user.setInsertDate((LocalDateTime) map.get("insert_date"));
			user.setUsername((String)map.get("username"));
			
			return user;
			
		} catch (EmptyResultDataAccessException e) {
			
			return null;
		}
	}

	/**
	 * ユーザーの登録処理
	 * 
	 * @param user ユーザー情報
	 */
	@Override
	public Integer regist(User user) throws DataAccessException {
		
		String sql = "INSERT INTO users (email, password, name, postal_code, address,"
					+ " phone_number, authority, delete_flag, insert_date, username)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql
							,user.getEmail()
							,user.getPassword()
							,user.getName()
							,user.getPostalCode()
							,user.getAddress()
							,user.getPhoneNumber()
							,user.getAuthority()
							,user.getDeleteFlag()
							,user.getInsertDate()
							,user.getUsername());
		
		Integer userId = jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class);
		
		return userId;
	}

	/**
	 * ユーザー情報の更新処理
	 * 
	 * @param user ユーザー情報
	 * @param id ユーザーID
	 */
	@Override
	public User update(User user) throws DataAccessException {
		
		String sql = "UPDATE users SET"
					+ " email = ?, password = ?, name = ?, postal_code = ?, address = ?,"
					+ " phone_number = ?, authority = ?, delete_flag = ?, insert_date = ?, username = ?"
					+ " WHERE id = ?";
		
		jdbcTemplate.update(sql
							,user.getEmail()
							,user.getPassword()
							,user.getName()
							,user.getPostalCode()
							,user.getAddress()
							,user.getPhoneNumber()
							,user.getAuthority()
							,user.getDeleteFlag()
							,user.getInsertDate()
							,user.getUsername()
							,user.getId());
		
		return user;
	}
}
