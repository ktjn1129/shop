package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.BasketBean;
import com.example.demo.bean.OrderItemBean;
import com.example.demo.form.AddressForm;
import com.example.demo.form.OrderForm;
import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.service.ItemService;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

/**
 * 注文登録機能コントローラ
 * 
 * @author koto
 */
@Controller
public class OrderRegistCustomerController {
	
	/**
	 * ユーザーリポジトリの紐付け
	 */
	@Autowired
	UserService userService;
	
	/**
	 * 商品リポジトリの紐付け
	 */
	@Autowired
	ItemService itemService;
	
	/**
	 * 注文リポジトリの紐付け
	 */
	@Autowired
	OrderService orderService;
	
	/**
	 * 注文商品リポジトリの紐付け
	 */
	@Autowired
	OrderItemService orderItemService;
	
	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;
	
	/**
	 * お届け先入力画面表示メソッド
	 * 
	 * @param form フォーム入力情報
	 * @param backFlg trueの場合:支払い方法選択画面からの「戻る」ボタン押下
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "order/order_address_input" お届け先入力画面への遷移
	 */
	@RequestMapping(path = "/address/input", method = RequestMethod.POST)
	public String inputAddress(@ModelAttribute AddressForm form, Boolean backFlg, User user, Model model) {

		if (!backFlg) {
			// POST送信されたIDを基にユーザー情報を取得し、Viewに渡す
			model.addAttribute("addressForm", userService.findById(user.getId()));

		} else if (backFlg) {
			// フォーム入力された値をViewに渡す（入力保持）
			model.addAttribute("addressForm", form);
		}
		return "order/order_address_input";
	}

	/**
	 * お届け先入力画面表示メソッド
	 * ※入力エラー発生時のリダイレクト処理
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "order/order_address_input" お届け先入力画面への遷移
	 */
	@RequestMapping(path = "/address/input", method = RequestMethod.GET)
	public String inputAddressRedirect(@ModelAttribute AddressForm form, Model model) {

		// フォーム入力された値をViewに渡す（入力保持）
		model.addAttribute("adressForm", form);

		return "order/order_address_input";
	}

	/**
	 * お届け先情報入力チェックメソッド
	 * 
	 * @param form フォーム入力情報
	 * @param result 入力チェックの結果
	 * @param backFlg trueの場合:注文登録確認画面からの「戻る」ボタン押下
	 * @param model Viewへの値渡し
	 * @return "order/order_payment_input" 支払い方法選択画面への遷移
	 */
	@RequestMapping(path = "/payment/input", method = RequestMethod.POST)
	public String inputPayment(@Valid @ModelAttribute AddressForm form, BindingResult result, Boolean backFlg, Model model) {

		if (!backFlg && result.hasErrors()) {
			// 入力エラーがある場合、お届け先入力画面にリダイレクト
			return inputAddressRedirect(form, model);

		} else if (backFlg) {
			// フォーム入力された値をViewに渡す（入力保持）
			model.addAttribute("addressForm", form);
		}
		return "order/order_payment_input";
	}

	/**
	 * 注文最終確認画面表示メソッド
	 * ※在庫チェックの実施
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "order/order_check" 注文最終確認画面への遷移
	 */
	@RequestMapping(path = "/order/check", method = RequestMethod.POST)
	public String checkOrder(@ModelAttribute OrderForm form, Model model) {

		// セッションに保存されている買い物かご情報を取得
		@SuppressWarnings("unchecked")
		ArrayList<BasketBean> basketList = (ArrayList<BasketBean>) session.getAttribute("basketList");
		// 新しい注文商品リストを生成
		ArrayList<OrderItemBean> orderItemList = new ArrayList<OrderItemBean>();

		for (BasketBean listItem : basketList) {
			// 買い物かご商品のIDを基に商品情報を取得
			Item item = itemService.findById(listItem.getId());
			// 最新の在庫数を取得
			Integer stock = item.getStock();
			// 買い物かご商品の注文個数を取得
			Integer orderNum = listItem.getOrderNum();

			if (stock >= orderNum) {
				// 在庫数が注文個数以上の場合、注文商品情報を生成
				OrderItemBean orderItemBean = new OrderItemBean();
				// 商品名をセット
				orderItemBean.setName(item.getName());
				// 商品画像をセット
				orderItemBean.setImage(item.getImage());
				// 単価をセット
				orderItemBean.setPrice(item.getPrice());
				// 数量をセット
				orderItemBean.setQuantity(orderNum);
				// 小計をセット
				orderItemBean.setSubtotal(item.getPrice() * orderNum);
				// 最新の在庫数をセット（在庫有無の表示用）
				orderItemBean.setStock(stock);
				// 注文商品情報を注文商品リストに追加
				orderItemList.add(orderItemBean);
				
			} else if (stock < orderNum) {
				// 在庫数が注文個数以上の場合、注文商品情報を生成
				OrderItemBean orderItemBean = new OrderItemBean();
				// 商品名をセット
				orderItemBean.setName(item.getName());
				// 商品画像をセット
				orderItemBean.setImage(item.getImage());
				// 単価をセット
				orderItemBean.setPrice(item.getPrice());
				// 数量を在庫数分のみにセット
				orderItemBean.setQuantity(stock);
				// 小計をセット
				orderItemBean.setSubtotal(item.getPrice() * stock);
				// 最新の在庫数をセット（在庫有無の表示用）
				orderItemBean.setStock(stock);
				// 注文商品情報を注文商品リストに追加
				orderItemList.add(orderItemBean);
			}
		}
		// 合計金額用の変数を宣言
		Integer total = 0;

		for (OrderItemBean orderItem : orderItemList) {
			// すべての小計を足し、合計金額を算出
			total += orderItem.getSubtotal();
		}

		// セッションに注文商品リストを保存
		session.setAttribute("orderItemList", orderItemList);
		// 注文商品リストをViewに渡す
		model.addAttribute("orderItemList", orderItemList);
		// 注文商品の合計金額をViewに渡す
		model.addAttribute("total", total);
		// お届け先情報をViewに渡す
		model.addAttribute("orderForm", form);

		return "order/order_check";
	}

	/**
	 * 注文登録メソッド
	 * 
	 * @param form フォーム入力情報
	 * @return "order/order_complete" 注文完了画面への遷移
	 */
	@RequestMapping(path = "order/complete", method = RequestMethod.POST)
	public String completeOrder(@ModelAttribute OrderForm form) {

		// ログインユーザー情報を取得
		User user = (User) session.getAttribute("user");
		// ログインユーザーIDくを取得
		Integer userId = user.getId();
		// 新しい注文情報を生成
		Order order = new Order();
		// 郵便番号をセット
		order.setPostalCode(form.getPostalCode());
		// 住所をセット
		order.setAddress(form.getAddress());
		// 氏名をセット
		order.setName(form.getName());
		// 電話番号をセット
		order.setPhoneNumber(form.getPhoneNumber());
		// 支払い方法をセット
		order.setPayMethod(form.getPayMethod());
		// ユーザーIDセット
		order.setUserId(userId);
		// 現在日時を取得
		LocalDateTime currentTime = LocalDateTime.now();
		// 現在日時をセット
		order.setInsertDate(currentTime);
		// セットした情報をDBに登録し戻り値の注文IDを取得
		Integer orderId = orderService.register(order);
		
		// セッションに保存されている注文商品リストを取得
		@SuppressWarnings("unchecked")
		ArrayList<OrderItemBean> orderItemList = (ArrayList<OrderItemBean>) session.getAttribute("orderItemList");

		for (OrderItemBean orderItemBean : orderItemList) {
			// 商品名を基に商品情報を取得
			Item item = itemService.findByName(orderItemBean.getName());
			// 商品の在庫数を取得
			Integer stock = item.getStock();
			// 注文個数を取得
			Integer orderNum = orderItemBean.getQuantity();
			// 新しい注文商品情報を生成
			OrderItem orderItem = new OrderItem();
			// 注文個数をセット
			orderItem.setQuantity(orderNum);
			// 注文情報をセット
			orderItem.setOrderId(orderId);
			// 商品情報をセット
			orderItem.setItemId(item.getId());
			// 単価をセット
			orderItem.setPrice(orderItemBean.getPrice());
			// セットした情報をDBに登録
			orderItemService.register(orderItem);
			// 商品の在庫数を注文個数分マイナスしてセット
			item.setStock(stock - orderNum);
			// セットした情報をDBに登録
			itemService.updateStock(item);
		}
		// セッションに保存されていた買い物かご情報を破棄
		session.removeAttribute("basketList");
		// セッションに保存されていた注文商品情報を破棄
		session.removeAttribute("orderItemList");

		return "order/order_complete";
	}
}