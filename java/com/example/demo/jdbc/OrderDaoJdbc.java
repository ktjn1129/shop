package com.example.demo.jdbc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderDao;

/**
 * 注文Dao JDBCクラス
 * 
 * @author koto
 */
@Repository("OrderDaoJdbc")
public class OrderDaoJdbc implements OrderDao {
	
	/**
	 * JDBC Templateの紐付け
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 注文IDを基に注文情報を取得
	 * 
	 * @param id 注文ID
	 * @return order 取得した注文情報
	 */
	@Override
	public Order findById(int id)  throws DataAccessException {
		
		String sql = "SELECT * FROM orders" + " WHERE id = ?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
		
		Order order = new Order();
		order.setId((Integer) map.get("id"));
		order.setPostalCode((String) map.get("postal_code"));
		order.setAddress((String) map.get("address"));
		order.setName((String) map.get("name"));
		order.setPhoneNumber((String) map.get("phone_number"));
		order.setPayMethod((Integer) map.get("pay_method"));
		order.setUserId((Integer) map.get("user_id"));
		order.setInsertDate((LocalDateTime) map.get("insert_date"));
		
		return order;
	}
	
	/**
	 * 注文日時を基に注文情報を取得
	 * 
	 * @param date 注文日時
	 * @return order 取得した注文情報
	 */
	@Override
	public Order findByInsertDate(LocalDateTime date) throws DataAccessException {
		
		String sql = "SELECT * FROM orders" + " WHERE insert_date = ?";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, date);
		
		Order order = new Order();
		order.setId((Integer) map.get("id"));
		order.setPostalCode((String) map.get("postal_code"));
		order.setAddress((String) map.get("address"));
		order.setName((String) map.get("name"));
		order.setPhoneNumber((String) map.get("phone_number"));
		order.setPayMethod((Integer) map.get("pay_method"));
		order.setUserId((Integer) map.get("user_id"));
		order.setInsertDate((LocalDateTime) map.get("insert_date"));
		
		return order;
	}

	/**
	 * ユーザーIDを基に注文情報を取得
	 * 
	 * @param userId ユーザーID
	 * @return orderList 取得した注文情報
	 */
	@Override
	public List<Order> findByUserId(int userId) throws DataAccessException {
		
		String sql = "SELECT * FROM orders"
					+ " WHERE user_id = ?"
					+ " ORDER BY insert_date DESC";
		
		List<Map<String,Object>> getList = jdbcTemplate.queryForList(sql, userId);
		List<Order> orderList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			Order order = new Order();
			order.setId((Integer) map.get("id"));
			order.setPostalCode((String) map.get("postal_code"));
			order.setAddress((String) map.get("address"));
			order.setName((String) map.get("name"));
			order.setPhoneNumber((String) map.get("phone_number"));
			order.setPayMethod((Integer) map.get("pay_method"));
			order.setUserId((Integer) map.get("user_id"));
			order.setInsertDate((LocalDateTime) map.get("insert_date"));
			orderList.add(order);
		}
		return orderList;
	}

	/**
	 * 注文情報の登録処理
	 * 
	 * @param order 注文情報
	 */
	@Override
	public Integer regist(Order order) throws DataAccessException {

		String sql = "INSERT INTO orders (postal_code, address, name,"
					+ " phone_number, pay_method, user_id, insert_date)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql
							,order.getPostalCode()
							,order.getAddress()
							,order.getName()
							,order.getPhoneNumber()
							,order.getPayMethod()
							,order.getUserId()
							,order.getInsertDate());
		
		Integer orderId = jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class);
		
		return orderId;
	}
}
