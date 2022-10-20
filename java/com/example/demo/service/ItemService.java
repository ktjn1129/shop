package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

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
@Transactional
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
	public List<Item> findAll(HashMap<String, String> pagination) {
		
		return dao.findAll(pagination);
	}
	
	/**
	 * 商品新着順検索メソッド
	 * 
	 * @return dao.findByOrderByInsertDateDesc() 商品情報
	 */
	public List<Item> findByOrderByInsertDateDesc() {
		
		return dao.findByOrderByInsertDateDesc();
	}
	
	/**
	 * 商品新着順検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @return dao.findByOrderByInsertDateDesc(pagination) 商品情報
	 */
	public List<Item> findByOrderByInsertDateDesc(HashMap<String, String> pagination) {
		
		return dao.findByOrderByInsertDateDesc(pagination);
	}
	
	/**
	 * 商品売人気順検索メソッド
	 * 
	 * @return dao.findByOrderByOrderItemListSizeDesc() 商品情報
	 */
	public List<Item> findByOrderByOrderItemListSizeDesc() {
		
		return dao.findByOrderByOrderItemListSizeDesc();
	}
	
	/**
	 * 商品売人気順検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @return dao.findByOrderByOrderItemListSizeDesc(pagination) 商品情報
	 */
	public List<Item> findByOrderByOrderItemListSizeDesc(HashMap<String, String> pagination) {
		
		return dao.findByOrderByOrderItemListSizeDesc(pagination);
	}
	
	/**
	 * 商品新着順+カテゴリ別検索メソッド
	 * 
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdOrderByInsertDateDesc(categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdOrderByInsertDateDesc(int categoryId) {
	
		return dao.findByCategoryIdOrderByInsertDateDesc(categoryId);
	}
	
	/**
	 * 商品新着順+カテゴリ別検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdOrderByInsertDateDesc(paginaiton, categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdOrderByInsertDateDesc(HashMap<String, String> pagination, int categoryId) {
	
		return dao.findByCategoryIdOrderByInsertDateDesc(pagination, categoryId);
	}
	
	/**
	 * 商品人気順+カテゴリ別検索メソッド
	 * 
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdOrderByOrderItemListSizeDesc(categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdOrderByOrderItemListSizeDesc(int categoryId) {
		
		return dao.findByCategoryIdOrderByOrderItemListSizeDesc(categoryId);
	}
	
	/**
	 * 商品人気順+カテゴリ別検索メソッド
	 * 
	 * @param pagination ページング情報
	 * @param categoryId カテゴリーID
	 * @return dao.findByCategoryIdOrderByOrderItemListSizeDesc(pagination, categoryId) 商品情報
	 */
	public List<Item> findByCategoryIdOrderByOrderItemListSizeDesc(HashMap<String, String> pagination, int categoryId) {
		
		return dao.findByCategoryIdOrderByOrderItemListSizeDesc(pagination, categoryId);
	}
	
	/**
	 * 商品在庫数更新メソッド
	 * 
	 * @param item 商品情報
	 */
	public void updateStock(Item item) {
		
		dao.updateStock(item);
	}
}
