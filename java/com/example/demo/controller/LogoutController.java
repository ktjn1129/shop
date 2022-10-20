package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ログアウト機能コントローラ
 * 
 * @author koto
 */
@Controller
public class LogoutController {
	
	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;
	
	/**
	 * ログアウト実行メソッド
	 * 
	 * @return "redirect:/login" ログイン画面へのリダイレクト
	 */
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String doLogout(Model model) {
		
		// セッション情報を破棄
		session.invalidate();
		
		return "redirect:/login";
	}
}