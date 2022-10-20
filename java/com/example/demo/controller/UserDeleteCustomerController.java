package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * 会員削除機能コントローラ
 * 
 * @author koto
 */
@Controller
public class UserDeleteCustomerController {

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
	 * 会員削除確認画面表示メソッド
	 * 
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "user/user_delete_check" 会員削除確認画面への遷移
	 */
	@RequestMapping(path = "/user/delete/check", method = RequestMethod.POST)
	public String deleteCheck(User user, Model model) {
		
		// POST送信されたIDを基にユーザー情報を取得し、Viewに渡す
		model.addAttribute("user", userService.findById(user.getId()));
		
		return "user/user_delete_check";
	}

	/**
	 * 会員削除メソッド
	 * 
	 * @param user ユーザー情報
	 * @return "user/user_delete_complete" 会員削除完了画面への遷移
	 */
	@RequestMapping(path = "/user/delete/complete", method = RequestMethod.POST)
	public String deleteComplete(User user) {
		
		// POST送信されたユーザーIDを取得
		Integer userId = user.getId();		
		// IDを基に削除したいユーザーの情報を取得
		User deleteUser = userService.findById(userId);
		// 削除フラグをtrueにセット
		deleteUser.setDeleteFlag(1);
		// DBに変更した内容を保存
		userService.update(deleteUser);
		// セッション情報を破棄
		session.invalidate();

		return "user/user_delete_complete";
	}
}
