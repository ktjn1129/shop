package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.OrderDetailBean;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.OrderItemDao;

/**
 * 注文商品Dao JDBCクラス
 * 
 * @author koto
 */
@Repository("OrderItemDaoJdbc")
public class OrderItemDaoJdbc implements OrderItemDao{
	
	/**
	 * JDBC Templateの紐付け
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 注文商品情報の登録処理
	 * 
	 * @param orderItem 注文情報
	 */
	@Override
	public void register(OrderItem orderItem) throws DataAccessException {
		//　SQL文を定義
		String sql = "INSERT INTO order_items (quantity, order_id, item_id, price)"
					+ " VALUES(?, ?, ?, ?)";
		// クエリを実行
		jdbcTemplate.update(sql
							,orderItem.getQuantity()
							,orderItem.getOrderId()
							,orderItem.getItemId()
							,orderItem.getPrice());
	}
	
	/**
	 * 注文IDを基に注文商品情報を取得
	 * 
	 * @param orderId 注文ID
	 * @return orderItem 取得した注文商品情報
	 */
	@Override
	public List<OrderDetailBean> findByOrderId(int orderId)  throws DataAccessException {
		//　SQL文を定義
		String sql = "SELECT * FROM order_items AS oi"
					+ " LEFT JOIN items AS i"
					+ " ON oi.item_id = i.id"
					+ " WHERE order_id = ?";
		// クエリを実行し商品情報を取得
		List<Map<String, Object>> getList = jdbcTemplate.queryForList(sql, orderId);
		// 返却用の注文商品リストを生成
		List<OrderDetailBean> orderItemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Orderインスタンスを生成
			OrderDetailBean orderItem = new OrderDetailBean();
			// 取得した注文商品情報をセット
			orderItem.setId((Integer) map.get("id"));
			orderItem.setQuantity((Integer) map.get("quantity"));
			orderItem.setOrderId((Integer) map.get("order_id"));
			orderItem.setItemId((Integer) map.get("item_id"));
			orderItem.setPrice((Integer) map.get("price"));
			orderItem.setName((String) map.get("name"));
			// 商品リストに追加
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}
}
