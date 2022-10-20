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

import com.example.demo.form.LoginForm;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

/**
 * ログイン機能コントローラ
 * 
 * @author koto
 */
@Controller
public class LoginController {

	/**
	 * ユーザーServiceの紐付け
	 */
	@Autowired
	UserService userService;

	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;

	/**
	 * ログイン画面表示メソッド ※ログイン失敗時のリダイレクト処理にも使用
	 * 
	 * @param form フォーム入力情報
	 * @return "login" ログイン画面への遷移
	 */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login(LoginForm form) {
		
		session.removeAttribute("user");
		
		return "login";
	}

	/**
	 * ログイン実行メソッド
	 * 
	 * @param form フォーム入力情報
	 * @param result 入力チェックの結果
	 * @param model ビューへの値渡し
	 * @return login(form) ログイン画面へのリダイレクト
	 * @return "redirect:/" トップ画面へのリダイレクト
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String doLogin(@Valid @ModelAttribute LoginForm form, BindingResult result, Model model) {
		
		User user = userService.findByEmail(form.getEmail());
		String password = user.getPassword();
		Integer deleteFlag = user.getDeleteFlag();
		String errMsg = "Eメールもしくはパスワードが正しくありません。";
		
		if (result.hasErrors() || user == null) {
			model.addAttribute("error", errMsg);
			return login(form);
		} else {
			if (form.getPassword().equals(password) && deleteFlag == 0) {
				session.setAttribute("user", user);
				return "redirect:/";
			} else {
				// パスワードがDBと一致しないまたは削除フラグがtrueの場合
				model.addAttribute("error", errMsg);
				return login(form);
			}
		}
	}
}