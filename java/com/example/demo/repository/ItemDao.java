package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.Item;

/**
 * 商品Dao
 * 
 * @author koto
 */
public interface ItemDao {
	
	public Item findById(int id) throws DataAccessException;
	
	public Item findByName(String name) throws DataAccessException;
	
	public List<Item> findAll() throws DataAccessException;
	
	public List<Item> findAll(Map<String, String> pagination) throws DataAccessException;
	
	public List<Item> SortByNewestToOldest() throws DataAccessException;
	
	public List<Item> SortByNewestToOldest(Map<String, String> pagination) throws DataAccessException;
	
	public List<Item> SortByPopularity() throws DataAccessException;
	
	public List<Item> SortByPopularity(Map<String, String> pagination) throws DataAccessException;
	
	public List<Item> findByCategoryIdSortByNewestToOldest(int categoryId) throws DataAccessException;
	
	public List<Item> findByCategoryIdSortByNewestToOldest(Map<String, String> pagination, int categoryId) throws DataAccessException;
	
	public List<Item> findByCategoryIdSortByPopularity(int categoryId) throws DataAccessException;
	
	public List<Item> findByCategoryIdSortByPopularity(Map<String, String> pagination, int categoryId) throws DataAccessException;
	
	public void updateStock(Item item) throws DataAccessException;
}
