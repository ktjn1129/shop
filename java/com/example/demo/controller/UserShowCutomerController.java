package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * 会員表示機能コントローラ
 * 
 * @author koto
 */
@Controller
public class UserShowCutomerController {

	/**
	 * ユーザーリポジトリの紐付け
	 */
	@Autowired
	UserService userService;
	
	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;

	/**
	 * 会員詳細表示メソッド
	 * ※一般会員向け
	 * 
	 * @param model Viewへの値渡し
	 * @return "user/user_detail" 会員詳細画面への遷移
	 */
	@RequestMapping(path = "/user/detail", method = RequestMethod.GET)
	public String showUserDetail(Model model) {
		
		// セッションに保存されているユーザー情報をViewに渡す
		model.addAttribute("user", session.getAttribute("user"));
		
		return "user/user_detail";
	}
	
	/**
	 * 会員詳細表示メソッド
	 * ※システム管理者、運用管理者向け
	 * 
	 * @param id ユーザーID
	 * @param model Viewへの値渡し
	 * @return "user/user_detail" 会員詳細画面への遷移
	 */
	@RequestMapping(path = "/user/detail/{id}", method = RequestMethod.GET)
	public String showUserDetailById(@PathVariable int id, Model model) {
		
		// GETパラメータのIDを基にユーザー情報を取得し、Viewに渡す
		model.addAttribute("user", userService.findById(id));
		
		return "user/user_detail";
	}

	/**
	 * 会員詳細表示メソッド
	 * ※会員変更入力画面、会員削除確認画面からの「戻る」ボタン押下時
	 * 
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "user/user_detail" 会員詳細画面への遷移
	 */
	@RequestMapping(path = "/user/detail/{id}", method = RequestMethod.POST)
	public String showUserDetailBack(User user, Model model) {
		
		// POST送信されたIDを基にユーザー情報を取得し、Viewに渡す
		model.addAttribute("user", userService.findById(user.getId()));
		
		return "user/user_detail";
	}
}