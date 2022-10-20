package com.example.demo.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.model.Category;

/**
 * カテゴリーDao
 * 
 * @author koto
 */
public interface CategoryDao {

	// カテゴリー情報を全件取得
	public List<Category> findAll() throws DataAccessException;
}
