package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.CategoryService;

/**
 * カテゴリ別検索表示用フィルター
 * 
 * @author koto
 */
@Component
public class GetCategoryListFilter implements Filter{
	
	@Autowired
	CategoryService categoryService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// カテゴリを全件検索し、Viewに渡す（カテゴリ別検索用）
		request.setAttribute("categories", categoryService.findAll());
			
		chain.doFilter(request,  response);
	}
}