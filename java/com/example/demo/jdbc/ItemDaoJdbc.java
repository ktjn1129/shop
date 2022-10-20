package com.example.demo.jdbc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemDao;

/**
 * 商品Dao JDBCクラス
 * 
 * @author koto
 */
@Repository("ItemDaoJdbc")
public class ItemDaoJdbc implements ItemDao {
	
	/**
	 * JDBC Templateの紐付け
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * 商品IDを基に商品情報を取得
	 * 
	 * @param id 商品ID
	 * @return item 取得した商品情報
	 */
	@Override
	public Item findById(int id) throws DataAccessException {
		//　SQL文を定義
		String sql = "SELECT * FROM items" + " WHERE id = ?";
		// クエリを実行し商品情報を取得
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
		// Itemインスタンスを生成
		Item item = new Item();
		// 取得した商品情報をセット
		item.setId((Integer) map.get("id"));
		item.setName((String) map.get("name"));
		item.setPrice((Integer) map.get("price"));
		item.setDescription((String) map.get("description"));
		item.setStock((Integer) map.get("stock"));
		item.setImage((String) map.get("image"));
		item.setCategoryId((Integer) map.get("category_id"));
		item.setDeleteFlag((Integer) map.get("delete_flag"));
		item.setInsertDate((LocalDateTime) map.get("insert_date"));
		
		return item;
	}

	/**
	 * 商品名を基に商品情報を取得
	 * 
	 * @param name 商品名
	 * @return item 取得した商品情報
	 */
	@Override
	public Item findByName(String name) throws DataAccessException {
		//　SQL文を定義
		String sql = "SELECT * FROM items" + " WHERE name = ?";
		// クエリを実行し商品情報を取得
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, name);
		// Itemインスタンスを生成
		Item item = new Item();
		// 取得した商品情報をセット
		item.setId((Integer) map.get("id"));
		item.setName((String) map.get("name"));
		item.setPrice((Integer) map.get("price"));
		item.setDescription((String) map.get("description"));
		item.setStock((Integer) map.get("stock"));
		item.setImage((String) map.get("image"));
		item.setCategoryId((Integer) map.get("category_id"));
		item.setDeleteFlag((Integer) map.get("delete_flag"));
		item.setInsertDate((LocalDateTime) map.get("insert_date"));
		
		return item;
	}
	
	/**
	 * 商品を全件検索
	 * 
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findAll() throws DataAccessException {
		// SQL文を定義
		String sql = "SELECT * FROM items";
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = jdbcTemplate.queryForList(sql);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 商品を全件検索
	 * 
	 * @param pagination ページング情報
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findAll(HashMap<String, String> pagination) throws DataAccessException {
		// ページング情報を取得
		int limit = Integer.parseInt(pagination.get("limit"));
		int page = Integer.parseInt(pagination.get("page")) -1;
		// SQL文を定義
		String sql = "SELECT * FROM items" + " LIMIT :limit OFFSET :offset";
		// プレースホルダに割り当てるパラメータを用意
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", limit);
		params.addValue("offset", limit * page);
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = namedParameterJdbcTemplate.queryForList(sql, params);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}

	/**
	 * 日付の新しい順に商品情報を取得（新着順）
	 * 
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByOrderByInsertDateDesc() throws DataAccessException {
		// SQL文を定義
		String sql = "SELECT * FROM items" + " ORDER BY insert_date DESC";
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = jdbcTemplate.queryForList(sql);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 日付の新しい順に商品情報を取得（新着順）
	 * 
	 * @param pagination ページング情報
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByOrderByInsertDateDesc(HashMap<String, String> pagination) throws DataAccessException {
		// ページング情報を取得
		int limit = Integer.parseInt(pagination.get("limit"));
		int page = Integer.parseInt(pagination.get("page")) -1;
		// SQL文を定義
		String sql = "SELECT * FROM items"
					+ " ORDER BY insert_date DESC"
					+ " LIMIT :limit OFFSET :offset";
		// プレースホルダに割り当てるパラメータを用意
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", limit);
		params.addValue("offset", limit * page);
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = namedParameterJdbcTemplate.queryForList(sql, params);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	

	/**
	 * 注文件数の多い順に商品情報取得（人気順）
	 * 
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByOrderByOrderItemListSizeDesc() throws DataAccessException {
		// SQL文を定義
		String sql = "SELECT *, count(*) AS order_num FROM items AS i"
					+ " LEFT JOIN order_items AS oi ON i.id = oi.item_id"
					+ " GROUP BY oi.item_id ORDER BY order_num DESC";
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = jdbcTemplate.queryForList(sql);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 注文件数の多い順に商品情報取得（人気順）
	 * 
	 * @param pagination ページング情報
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByOrderByOrderItemListSizeDesc(HashMap<String, String> pagination) throws DataAccessException {
		// ページング情報を取得
		int limit = Integer.parseInt(pagination.get("limit"));
		int page = Integer.parseInt(pagination.get("page")) -1;
		// SQL文を定義
		String sql = "SELECT *, count(*) AS order_num FROM items AS i"
					+ " LEFT JOIN order_items AS oi ON i.id = oi.item_id"
					+ " GROUP BY oi.item_id ORDER BY order_num DESC"
					+ " LIMIT :limit OFFSET :offset";
		// プレースホルダに割り当てるパラメータを用意
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", limit);
		params.addValue("offset", limit * page);
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = namedParameterJdbcTemplate.queryForList(sql, params);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 日付の新しい順にカテゴリ別で商品情報を取得（新着順+カテゴリ別）
	 * 
	 * @param categoryId カテゴリーID
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByCategoryIdOrderByInsertDateDesc(int categoryId) throws DataAccessException {
		// SQL文を定義
		String sql = "SELECT * FROM items"
					+ " WHERE category_id = ?"
					+ " ORDER BY insert_date DESC";
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = jdbcTemplate.queryForList(sql, categoryId);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 日付の新しい順にカテゴリ別で商品情報を取得（新着順+カテゴリ別）
	 * 
	 * @param pagination ページング情報
	 * @param categoryId カテゴリーID
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByCategoryIdOrderByInsertDateDesc(HashMap<String, String> pagination, int categoryId) throws DataAccessException {
		// ページング情報を取得
		int limit = Integer.parseInt(pagination.get("limit"));
		int page = Integer.parseInt(pagination.get("page")) -1;
		// SQL文を定義
		String sql = "SELECT * FROM items"
					+ " WHERE category_id = :categoryId"
					+ " ORDER BY insert_date DESC"
					+ " LIMIT :limit OFFSET :offset";
		// プレースホルダに割り当てるパラメータを用意
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("categoryId", categoryId);
		params.addValue("limit", limit);
		params.addValue("offset", limit * page);
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = namedParameterJdbcTemplate.queryForList(sql, params);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 注文件数の多い順にカテゴリ別で商品情報取得（人気順+カテゴリ別）
	 * 
	 * @param categoryId カテゴリーID
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByCategoryIdOrderByOrderItemListSizeDesc(int categoryId) throws DataAccessException {
		// SQL文を定義
		String sql = "SELECT *, count(*) AS order_num FROM items AS i"
					+ " LEFT JOIN order_items AS oi ON i.id = oi.item_id"
					+ " WHERE category_id = ?"
					+ " GROUP BY oi.item_id ORDER BY order_num DESC";
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = jdbcTemplate.queryForList(sql, categoryId);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}
	
	/**
	 * 注文件数の多い順にカテゴリ別で商品情報取得（人気順+カテゴリ別）
	 * 
	 * @param pagination ページング情報
	 * @param categoryId カテゴリーID
	 * @return itemList 取得した商品情報
	 */
	@Override
	public List<Item> findByCategoryIdOrderByOrderItemListSizeDesc(HashMap<String, String> pagination, int categoryId) throws DataAccessException {
		// ページング情報を取得
		int limit = Integer.parseInt(pagination.get("limit"));
		int page = Integer.parseInt(pagination.get("page")) -1;
		// SQL文を定義
		String sql = "SELECT *, count(*) AS order_num FROM items AS i"
					+ " LEFT JOIN order_items AS oi ON i.id = oi.item_id"
					+ " WHERE category_id = :categoryId"
					+ " GROUP BY oi.item_id ORDER BY order_num DESC"
					+ " LIMIT :limit OFFSET :offset";
		// プレースホルダに割り当てるパラメータを用意
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("categoryId", categoryId);
		params.addValue("limit", limit);
		params.addValue("offset", limit * page);
		// クエリを実行し商品情報を取得
		List<Map<String,Object>> getList = namedParameterJdbcTemplate.queryForList(sql, params);
		// 返却用の商品リストを生成
		List<Item> itemList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			// Itemインスタンスを生成
			Item item = new Item();
			// 取得した商品情報をセット
			item.setId((Integer) map.get("id"));
			item.setName((String) map.get("name"));
			item.setPrice((Integer) map.get("price"));
			item.setDescription((String) map.get("description"));
			item.setStock((Integer) map.get("stock"));
			item.setImage((String) map.get("image"));
			item.setCategoryId((Integer) map.get("category_id"));
			item.setDeleteFlag((Integer) map.get("delete_flag"));
			item.setInsertDate((LocalDateTime) map.get("insert_date"));
			// 商品リストに追加
			itemList.add(item);
		}
		return itemList;
	}

	/**
	 * 商品の在庫数を更新
	 * 
	 * @param item 商品情報
	 */
	@Override
	public void updateStock(Item item) throws DataAccessException {
		// SQL文を定義
		String sql = "UPDATE items SET"
					+ " stock = ?"
					+ " WHERE id = ?";
		// クエリを実行
		jdbcTemplate.update(sql, item.getStock(), item.getId());
	}
}
