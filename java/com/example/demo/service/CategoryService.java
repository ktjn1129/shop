package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryDao;

/**
 * カテゴリーServiceクラス
 * 
 * @author koto
 */
@Transactional
@Service
public class CategoryService {
	
	/**
	 * カテゴリDaoの紐付け
	 */
	@Autowired
	@Qualifier("CategoryDaoJdbc")
	CategoryDao dao;
	
	/**
	 * カテゴリ全件検索メソッド
	 * 
	 * @return dao.findAll() カテゴリ情報
	 */
	public List<Category> findAll() {
		
		return dao.findAll();
	}
}