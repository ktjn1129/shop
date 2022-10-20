package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.Item;

/**
 * 商品Dao
 * 
 * @author koto
 */
public interface ItemDao {
	
	// 商品IDを基に商品情報を取得
	public Item findById(int id) throws DataAccessException;
	
	// 商品名を基に商品情報を取得
	public Item findByName(String name) throws DataAccessException;
	
	// 商品情報を全件取得
	public List<Item> findAll() throws DataAccessException;
	
	// 商品情報を全件取得
	public List<Item> findAll(HashMap<String, String> pagenation) throws DataAccessException;
	
	// 日付の新しい順に商品情報を取得<新着順>
	public List<Item> findByOrderByInsertDateDesc() throws DataAccessException;
	
	// 日付の新しい順に商品情報を取得<新着順>
	public List<Item> findByOrderByInsertDateDesc(HashMap<String, String> pagenation) throws DataAccessException;
	
	// 注文件数の多い順に商品情報取得<人気順>
	public List<Item> findByOrderByOrderItemListSizeDesc() throws DataAccessException;
	
	// 注文件数の多い順に商品情報取得<人気順>
	public List<Item> findByOrderByOrderItemListSizeDesc(HashMap<String, String> pagenation) throws DataAccessException;
	
	// 日付の新しい順に商品情報を取得<新着順+カテゴリ別>
	public List<Item> findByCategoryIdOrderByInsertDateDesc(int categoryId) throws DataAccessException;
	
	// 日付の新しい順に商品情報を取得<新着順+カテゴリ別>
	public List<Item> findByCategoryIdOrderByInsertDateDesc(HashMap<String, String> pagenation, int categoryId) throws DataAccessException;
	
	// 注文件数の多い順に商品情報取得<人気順+カテゴリ別>
	public List<Item> findByCategoryIdOrderByOrderItemListSizeDesc(int categoryId) throws DataAccessException;
	
	// 注文件数の多い順に商品情報取得<人気順+カテゴリ別>
	public List<Item> findByCategoryIdOrderByOrderItemListSizeDesc(HashMap<String, String> pagenation, int categoryId) throws DataAccessException;
	
	// 商品の在庫数を更新
	public void updateStock(Item item) throws DataAccessException;
}
