package com.example.demo.controller;

import java.time.LocalDateTime;

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
 * 会員登録機能コントローラ
 * 
 * @author koto
 */
@Controller
public class UserRegistCustomerController {

	/**
	 * ユーザーリポジトリの紐付け
	 */
	@Autowired
	UserService userService;

	/**
	 * 会員登録画面表示メソッド
	 * 
	 * @param form フォーム入力情報 
	 * @return "user/user_regist_input" 会員登録入力画面への遷移
	 */
	@RequestMapping(path = "/user/regist/input", method = RequestMethod.GET)
	public String registInput(UserForm form) {
		
		return "user/user_regist_input";
	}

	/**
	 * 会員登録画面表示メソッド
	 * ※会員登録確認画面からの「戻る」ボタン押下時（フォーム入力保持）
	 * 
	 * @param form フォーム入力情報
	 * @param model ビューへの値渡し
	 * @return "user/user_regist_input" 会員登録入力画面への遷移
	 */
	@RequestMapping(path = "/user/regist/input", method = RequestMethod.POST)
	public String registInputBack(@ModelAttribute UserForm form, Model model) {
		
		model.addAttribute("userForm", form);
		
		return "user/user_regist_input";
	}

	/**
	 * 会員登録入力チェックメソッド
	 * 
	 * @param form フォーム入力情報
	 * @param result 入力チェックの結果
	 * @param model ビューへの値渡し
	 * @return "user/user_regist_check" 会員登録確認画面への遷移
	 */
	@RequestMapping(path = "/user/regist/check", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute UserForm form, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			
			return registInput(form);
		} else {
			model.addAttribute("userForm", form);
			
			return "user/user_regist_check";
		}
	}

	/**
	 * 会員登録メソッド
	 * 
	 * @param form フォーム入力情報
	 * @return "user/user_regist_complete" 会員登録完了画面への遷移
	 * @return regidtInput(form) 会員登録画面への遷移
	 */
	@RequestMapping(path = "/user/regist/complete", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute UserForm form, Model model) {
		
		String formEmail = form.getEmail();
		User registeredUser = userService.findByEmail(formEmail);
		LocalDateTime currTime = LocalDateTime.now();
		String errMsg = "入力されたEmailは既に登録されています。";
		
		if (registeredUser == null) {
			// フォーム入力されたEmailが存在しない場合
			User user = new User();
			user.setEmail(formEmail);
			user.setPassword(form.getPassword());
			user.setName(form.getName());
			user.setPostalCode(form.getPostalCode());
			user.setAddress(form.getAddress());
			user.setPhoneNumber(form.getPhoneNumber());
			user.setUsername(form.getEmail());
			user.setAuthority(2); //一般会員用の権限
			user.setDeleteFlag(0);
			user.setInsertDate(currTime);
			userService.register(user);
			
			return "user/user_regist_complete";
		} else {
			// フォーム入力されたEmailが既に存在する場合
			model.addAttribute("error", errMsg);
			
			return registInput(form);
		}
	}
}