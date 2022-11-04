package com.example.demo.jdbc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryDao;

/**
 * カテゴリーDao JDBCクラス
 * 
 * @author koto
 */
@Repository("CategoryDaoJdbc")
public class CategoryDaoJdbc implements CategoryDao{
	
	/**
	 * JDBC Templateの紐付け
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * カテゴリーを全件検索
	 * 
	 * @return categoryList 取得したカテゴリー情報
	 */
	@Override
	public List<Category> findAll() throws DataAccessException {

		List<Map<String,Object>> getList = jdbcTemplate.queryForList("SELECT * FROM categories");
		List<Category> categoryList = new ArrayList<>();
		
		for(Map<String, Object> map : getList) {
			Category category = new Category();
			category.setId((Integer) map.get("id"));
			category.setName((String) map.get("name"));
			category.setDescription((String) map.get("description"));
			category.setDeleteFlag((Boolean) map.get("delete_flag"));
			category.setInsertDate((LocalDateTime) map.get("insert_date"));
			categoryList.add(category);
		}
		return categoryList;
	}
}
