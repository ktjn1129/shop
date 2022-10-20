package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.form.UserForm;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * 会員変更機能コントローラ
 * 
 * @author koto
 */
@Controller
public class UserUpdateCustomerController {

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
	 * 会員変更入力画面表示メソッド
	 * 
	 * @param form フォーム入力情報
	 * @param backFlg trueの場合：会員変更確認画面からの「戻る」ボタン押下時
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "user/user_update_input" 会員変更入力画面への遷移
	 */
	@RequestMapping(path = "/user/update/input", method = RequestMethod.POST)
	public String updateInput(@ModelAttribute UserForm form, Boolean backFlg, User user, Model model) {
		
		if (!backFlg) {
			// POST送信されたIDを基にユーザー情報を取得し、Viewに渡す
			model.addAttribute("user", userService.findById(user.getId()));
			
		} else if (backFlg) {
			// フォーム入力された値をViewに渡す（入力保持）
			model.addAttribute("user", form);
		}
		return "user/user_update_input";
	}

	/**
	 * 会員変更入力画面表示メソッド
	 * ※入力エラー発生時のリダイレクト処理
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "user/user_update_input" 会員変更入力画面への遷移
	 */
	@RequestMapping(path = "/user/update/input", method = RequestMethod.GET)
	public String updateInputRedirect(@ModelAttribute UserForm form, Model model) {
		
		// フォーム入力された値をViewに渡す（入力保持）
		model.addAttribute("user", form);
		
		return "user/user_update_input";
	}

	/**
	 * 会員変更入力チェックメソッド
	 * 
	 * @param form フォーム入力情報
	 * @param result 入力チェックの結果
	 * @param model Viewへの値渡し
	 * @return "user/user_update_check" 会員変更確認画面への遷移
	 */
	@RequestMapping(path = "/user/update/check", method = RequestMethod.POST)
	public String updateCheck(@Valid @ModelAttribute UserForm form, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			// 入力エラーがある場合、会員入力画面にリダイレクト
			return updateInputRedirect(form, model);
			
		} else {
			return "user/user_update_check";
		}
	}

	/**
	 * 会員変更メソッド
	 * 
	 * @param form フォーム入力情報
	 * @return "user/user_update_complete" 会員変更完了画面への遷移
	 */
	@RequestMapping(path = "/user/update/complete", method = RequestMethod.POST)
	public String updateComplete(@ModelAttribute UserForm form) {
		
		// POST送信されたユーザーIDを取得
		Integer userId = form.getId();
		// IDを基にユーザー情報を取得
		User user = userService.findById(userId);
		// Emailをセット
		user.setEmail(form.getEmail());
		// パスワードをセット
		user.setPassword(form.getPassword());
		// 氏名をセット
		user.setName(form.getName());
		// 郵便番号をセット
		user.setPostalCode(form.getPostalCode());
		// 住所をセット
		user.setAddress(form.getAddress());
		// 電話番号をセット
		user.setPhoneNumber(form.getPhoneNumber());
		// セットした情報をDBに登録
		userService.update(user);
		// セッションにユーザー情報を保存
		session.setAttribute("user", user);

		return "user/user_update_complete";
	}
}