package com.example.demo.controller.user;

import java.time.LocalDateTime;

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
 * 会員管理機能コントローラ
 * 
 * @author koto
 */
@Controller
public class UserController {

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
	 * （会員登録確認画面からの「戻る」ボタン押下時）
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "user/user_regist_input" 会員登録入力画面への遷移
	 */
	@RequestMapping(path = "/user/regist/input/back", method = RequestMethod.POST)
	public String registInputBack(@ModelAttribute UserForm form, Model model) {
		
		model.addAttribute("userForm", form);
		
		return "user/user_regist_input";
	}

	/**
	 * 会員登録入力チェックメソッド
	 * 
	 * @param form フォーム入力情報
	 * @param result 入力チェックの結果
	 * @param model Viewへの値渡し
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
	 * @param model Viewへの値渡し
	 * @return "user/user_regist_complete" 会員登録完了画面への遷移
	 * @return registInput(form) 会員登録画面への遷移
	 */
	@RequestMapping(path = "/user/regist/complete", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute UserForm form, Model model) {
		
		User registeredUser = userService.findByEmail(form.getEmail());
		
		if (registeredUser == null) {
			User user = new User();
			User setUser = setUser(user, form);
			Integer newUserId = userService.regist(setUser);
			User newUser =  userService.findById(newUserId);
			session.setAttribute("user", newUser);
			
			return "user/user_regist_complete";
			
		} else if (registeredUser.getDeleteFlag() == 1) {
			User setUser = setUser(registeredUser, form);
			userService.update(setUser);
			User updatedUser = userService.findByEmail(form.getEmail());
			session.setAttribute("user", updatedUser);
			
			return "user/user_regist_complete";
			
		} else {
			model.addAttribute("error", "入力されたEmailは既に登録されています。");
			
			return registInput(form);
		}
	}
	
	/**
	 * 会員詳細表示メソッド
	 * 
	 * @param model Viewへの値渡し
	 * @return "user/user_detail" 会員詳細画面への遷移
	 */
	@RequestMapping(path = "/user/detail", method = RequestMethod.GET)
	public String showUserDetail(Model model) {
		
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		
		return "user/user_detail";
	}

	/**
	 * 会員詳細表示メソッド
	 * （会員変更入力画面、会員削除確認画面からの「戻る」ボタン押下時）
	 * 
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "user/user_detail" 会員詳細画面への遷移
	 */
	@RequestMapping(path = "/user/detail/{id}", method = RequestMethod.POST)
	public String showUserDetailBack(User user, Model model) {
		
		User userData = userService.findById(user.getId());
		model.addAttribute("user", userData);
		
		return "user/user_detail";
	}
	
	/**
	 * 会員変更入力画面表示メソッド
	 * 
	 * @param form フォーム入力情報
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "user/user_update_input" 会員変更入力画面への遷移
	 */
	@RequestMapping(path = "/user/update/input", method = RequestMethod.POST)
	public String updateInput(@ModelAttribute UserForm form, User user, Model model) {
		
		User userData = userService.findById(user.getId());
		model.addAttribute("user", userData);
		
		return "user/user_update_input";
	}

	/**
	 * 会員変更入力画面表示メソッド
	 * （入力エラー発生時のリダイレクト処理）
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "user/user_update_input" 会員変更入力画面への遷移
	 */
	@RequestMapping(path = "/user/update/input", method = RequestMethod.GET)
	public String updateInputRedirect(@ModelAttribute UserForm form, Model model) {
		
		model.addAttribute("user", form);
		
		return "user/user_update_input";
	}
	
	/**
	 * 会員変更入力画面表示メソッド
	 * （会員変更確認画面からの「戻る」ボタン押下時）
	 * 
	 * @param form フォーム入力情報
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "user/user_update_input" 会員変更入力画面への遷移
	 */
	@RequestMapping(path = "/user/update/input/back", method = RequestMethod.POST)
	public String updateInputBack(@ModelAttribute UserForm form, User user, Model model) {
		
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
		
		User user = userService.findById(form.getId());
		User setUser = setUser(user, form);
		User updatedUser = userService.update(setUser);
		session.setAttribute("user", updatedUser);
		
		return "user/user_update_complete";
	}
	
	/**
	 * 会員削除確認画面表示メソッド
	 * 
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "user/user_delete_check" 会員削除確認画面への遷移
	 */
	@RequestMapping(path = "/user/delete/check", method = RequestMethod.POST)
	public String deleteCheck(User user, Model model) {
		
		User userData = userService.findById(user.getId());
		model.addAttribute("user", userData);
		
		return "user/user_delete_check";
	}

	/**
	 * 会員削除（退会）メソッド
	 * 
	 * @param user ユーザー情報
	 * @return "user/user_delete_complete" 会員削除完了画面への遷移
	 */
	@RequestMapping(path = "/user/delete/complete", method = RequestMethod.POST)
	public String deleteComplete(User user) {
		
		User deleteUser = userService.findById(user.getId());
		deleteUser.setDeleteFlag(1);
		userService.update(deleteUser);
		session.invalidate();
		
		return "user/user_delete_complete";
	}
	
	/**
	 * 会員情報セットメソッド
	 * 
	 * @param user ユーザー情報
	 * @param form フォーム入力情報
	 * @return user ユーザー情報
	 */
	private User setUser(User user, UserForm form) {
		
		LocalDateTime currTime = LocalDateTime.now();
		user.setEmail(form.getEmail());
		user.setPassword(form.getPassword());
		user.setName(form.getName());
		user.setPostalCode(form.getPostalCode());
		user.setAddress(form.getAddress());
		user.setPhoneNumber(form.getPhoneNumber());
		user.setUsername(form.getEmail());
		user.setAuthority(2);
		user.setDeleteFlag(0);
		user.setInsertDate(currTime);
		
		return user;
	}
}
