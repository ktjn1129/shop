package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemDao;

/**
 * 商品Serviceクラス
 * 
 * @author koto
 */
@Service
public class ItemService {

	/**
	 * 商品Daoの紐付け
	 */
	@Autowired
	@Qualifier("ItemDaoJdbc")
	ItemDao dao;
	
	/**
	 * 商品ID検索メソッド
	 * 
	 * @param id 商品ID
	 * @return dao.findById(id) 商品情報
	 */
	public Item findById(int id) {
		
		return dao.findById(id);
	}
	
	/**
	 * 商品名検索メソッド
	 * 
	 * @param name 商品名
	 * @return dao.findByName(name) 商品情報
	 */
	public Item findByName(String name) {
		
		return dao.findByName(name);
	}
	
	/**
	 * 商品全件検索メソッド
	 * 
	 * @return dao.findAll() 商品情報
	 */
	public List<Item> findAll() {
		
		return dao.findAll();
	}
	
	/**
	 * 商品全件検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @return dao.findAll(pagination) 商品情報
	 */
	public List<Item> findAll(Map<String, String> pagination) {
		
		return dao.findAll(pagination);
	}
	
	/**
	 * 商品新着順検索メソッド
	 * 
	 * @return dao.SortByNewestToOldest() 商品情報
	 */
	public List<Item> SortByNewestToOldest() {
		
		return dao.SortByNewestToOldest();
	}
	
	/**
	 * 商品新着順検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @return dao.SortByNewestToOldest(pagination) 商品情報
	 */
	public List<Item> SortByNewestToOldest(Map<String, String> pagination) {
		
		return dao.SortByNewestToOldest(pagination);
	}
	
	/**
	 * 商品売人気順検索メソッド
	 * 
	 * @return dao.SortByPopularity() 商品情報
	 */
	public List<Item> SortByPopularity() {
		
		return dao.SortByPopularity();
	}
	
	/**
	 * 商品売人気順検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @return dao.SortByPopularity(pagination) 商品情報
	 */
	public List<Item> SortByPopularity(Map<String, String> pagination) {
		
		return dao.SortByPopularity(pagination);
	}
	
	/**
	 * 商品新着順+カテゴリ別検索メソッド
	 * 
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdSortByNewestToOldest(categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdSortByNewestToOldest(int categoryId) {
	
		return dao.findByCategoryIdSortByNewestToOldest(categoryId);
	}
	
	/**
	 * 商品新着順+カテゴリ別検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdSortByNewestToOldest(paginaiton, categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdSortByNewestToOldest(Map<String, String> pagination, int categoryId) {
	
		return dao.findByCategoryIdSortByNewestToOldest(pagination, categoryId);
	}
	
	/**
	 * 商品人気順+カテゴリ別検索メソッド
	 * 
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdSortByPopularity(categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdSortByPopularity(int categoryId) {
		
		return dao.findByCategoryIdSortByPopularity(categoryId);
	}
	
	/**
	 * 商品人気順+カテゴリ別検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdSortByPopularity(pagination, categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdSortByPopularity(Map<String, String> pagination, int categoryId) {
		
		return dao.findByCategoryIdSortByPopularity(pagination, categoryId);
	}
	
	/**
	 * 商品在庫数更新メソッド
	 * 
	 * @param item 商品情報
	 */
	@Transactional
	public void updateStock(Item item) {
		
		dao.updateStock(item);
	}
}
