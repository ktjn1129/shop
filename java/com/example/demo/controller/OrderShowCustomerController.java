package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.User;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;

/**
 * 注文表示機能コントローラ
 * 
 * @author koto
 */
@Controller
public class OrderShowCustomerController {

	/**
	 * 注文リポジトリの紐付け
	 */
	@Autowired
	OrderService orderService;
	
	/**
	 * 注文リポジトリの紐付け
	 */
	@Autowired
	OrderItemService orderItemService;
	
	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;

	/**
	 * 注文一覧表示メソッド
	 * 
	 * @param model Viewへの値渡し
	 * @return "order/order_list" 注文一覧画面への遷移
	 */
	@RequestMapping(path = "order/list", method = RequestMethod.GET)
	public String showOrders(Model model) {
		
		// セッションに保存されているログインユーザー情報を取得
		User user = (User) session.getAttribute("user");
		// ログインユーザー情報からユーザーIDを取得
		Integer userId = user.getId();
		// ログインユーザーの注文履歴を取得し、Viewに渡す
		model.addAttribute("orders", orderService.findByUserId(userId));
		
		return "order/order_list";
	}

	/**
	 * 注文詳細表示メソッド
	 * 
	 * @param id 注文ID
	 * @param model Viewへの値渡し
	 * @return "order/order_detail" 注文詳細画面への遷移
	 */
	@RequestMapping(path = "order/detail/{id}", method = RequestMethod.GET)
	public String showOrderDetail(@PathVariable int id, Model model) {
		
		// GETパラメータのIDを基に注文情報を取得し、Viewに渡す
		model.addAttribute("order", orderService.findById(id));
		// GETパラメータのIDを基に注文商品情報を取得し、Viewに渡す
		model.addAttribute("orderItems", orderItemService.findByOrderId(id));
		
		return "order/order_detail";
	}
}
