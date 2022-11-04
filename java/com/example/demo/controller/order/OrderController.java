package com.example.demo.controller.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.BasketBean;
import com.example.demo.bean.OrderDetailBean;
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
import com.example.demo.value.PayMethod;

/**
 * 注文管理機能コントローラ
 * 
 * @author koto
 */
@Controller
public class OrderController {
	
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
	 * @param user ユーザー情報
	 * @param model Viewへの値渡し
	 * @return "order/order_address_input" お届け先入力画面への遷移
	 */
	@RequestMapping(path = "/address/input", method = RequestMethod.POST)
	public String inputAddress(@ModelAttribute AddressForm form, User user, Model model) {
		
		User userData = userService.findById(user.getId());
		model.addAttribute("addressForm", userData);
		
		return "order/order_address_input";
	}

	/**
	 * お届け先入力画面表示メソッド
	 * （入力エラー発生時のリダイレクト処理）
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "order/order_address_input" お届け先入力画面への遷移
	 */
	@RequestMapping(path = "/address/input", method = RequestMethod.GET)
	public String inputAddressRedirect(@ModelAttribute AddressForm form, Model model) {
		
		model.addAttribute("addressForm", form);
		
		return "order/order_address_input";
	}
	
	/**
	 * お届け先入力画面表示メソッド
	 * （支払い方法選択画面からの「戻る」ボタン押下時）
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "order/order_address_input" お届け先入力画面への遷移
	 */
	@RequestMapping(path = "/address/input/back", method = RequestMethod.GET)
	public String inputAddressBack(@ModelAttribute AddressForm form, Model model) {
		
		model.addAttribute("addressForm", form);
		
		return "order/order_address_input";
	}

	/**
	 * 支払い方法選択画面表示メソッド
	 * 
	 * @param form フォーム入力情報
	 * @param result 入力チェックの結果
	 * @param model Viewへの値渡し
	 * @return "order/order_payment_input" 支払い方法選択画面への遷移
	 */
	@RequestMapping(path = "/payment/input", method = RequestMethod.POST)
	public String inputPayment(@Valid @ModelAttribute AddressForm form, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			
			return inputAddressRedirect(form, model);
		} else {
			List<PayMethod> payMethodList = Arrays.asList(PayMethod.values());
			model.addAttribute("payMethodList", payMethodList);
			
			return "order/order_payment_input";
		}
	}
	
	/**
	 * 支払い方法選択画面表示メソッド
	 * （注文内容確認画面からの「戻る」ボタン押下時）
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "order/order_payment_input" 支払い方法選択画面への遷移
	 */
	@RequestMapping(path = "/payment/input/back", method = RequestMethod.POST)
	public String inputPayment(@ModelAttribute AddressForm form, Model model) {
		
		model.addAttribute("addressForm", form);
		List<PayMethod> payMethodList = Arrays.asList(PayMethod.values());
		model.addAttribute("payMethodList", payMethodList);
		
		return "order/order_payment_input";
	}

	/**
	 * 注文内容確認画面表示メソッド
	 * 
	 * @param form フォーム入力情報
	 * @param model Viewへの値渡し
	 * @return "order/order_check" 注文内容確認画面への遷移
	 */
	@RequestMapping(path = "/order/check", method = RequestMethod.POST)
	public String checkOrder(@ModelAttribute OrderForm form, Model model) {

		List<OrderItemBean> orderItemList = setOrderItemList();
		Integer totalAmount = 0;
		
		for (OrderItemBean orderItem : orderItemList) {
			totalAmount += orderItem.getSubtotal();
		}
		session.setAttribute("orderItemList", orderItemList);
		model.addAttribute("orderItemList", orderItemList);
		model.addAttribute("total", totalAmount);
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
		
		User loginUser = (User) session.getAttribute("user");
		Order order = setOrder(form, loginUser.getId());
		Integer orderId = orderService.regist(order);
		
		List<OrderItemBean> orderItemList = (List<OrderItemBean>) session.getAttribute("orderItemList");
		for (OrderItemBean orderItemBean : orderItemList) {
			Item item = itemService.findByName(orderItemBean.getName());
			OrderItem orderItem = setOrderItem(orderId, orderItemBean, item);
			orderItemService.regist(orderItem);
			
			item.setStock(item.getStock() - orderItemBean.getQuantity());
			itemService.updateStock(item);
		}
		session.removeAttribute("basketList");
		session.removeAttribute("orderItemList");
		
		return "order/order_complete";
	}
	
	/**
	 * 注文履歴一覧表示メソッド
	 * 
	 * @param model Viewへの値渡し
	 * @return "order/order_list" 注文履歴一覧画面への遷移
	 */
	@RequestMapping(path = "order/list", method = RequestMethod.GET)
	public String showOrders(Model model) {
		
		User loginUser = (User) session.getAttribute("user");
		List<Order> orderList = orderService.findByUserId(loginUser.getId());
		
		model.addAttribute("orderList", orderList);
		
		return "order/order_list";
	}

	/**
	 * 注文履歴詳細表示メソッド
	 * 
	 * @param orderId 注文ID
	 * @param model Viewへの値渡し
	 * @return "order/order_detail" 注文履歴詳細画面への遷移
	 */
	@RequestMapping(path = "order/detail/{id}", method = RequestMethod.GET)
	public String showOrderDetail(@PathVariable Integer id, Model model) {
		
		Order order = orderService.findById(id);
		List<OrderDetailBean> orderItems = orderItemService.findByOrderId(id);
		
		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems);
		
		return "order/order_detail";
	}
	
	/**
	 * 注文情報セットメソッド
	 * 
	 * @param form お届け先および支払情報
	 * @param userId ユーザーID
	 * @return order 注文情報
	 */
	private Order setOrder(OrderForm form, Integer userId) {
		
		Order order = new Order();
		LocalDateTime currTime = LocalDateTime.now();
		order.setPostalCode(form.getPostalCode());
		order.setAddress(form.getAddress());
		order.setName(form.getName());
		order.setPhoneNumber(form.getPhoneNumber());
		order.setPayMethod(form.getPayMethod());
		order.setUserId(userId);
		order.setInsertDate(currTime);
		
		return order;
	}
	
	/**
	 * 注文商品情報セットメソッド
	 * 
	 * @param orderId 注文ID
	 * @param orderItemBean 注文商品情報
	 * @param item 商品情報
	 * @return orderItem 注文商品情報
	 */
	private OrderItem setOrderItem(Integer orderId, OrderItemBean orderItemBean, Item item) {
		
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(orderItemBean.getQuantity());
		orderItem.setOrderId(orderId);
		orderItem.setItemId(item.getId());
		orderItem.setPrice(orderItemBean.getPrice());
		
		return orderItem;
	}
	
	/**
	 * 注文商品情報セットメソッド
	 * 
	 * @param item 商品情報
	 * @param quantity 数量
	 * @return orderItemBean 注文商品情報
	 */
	private OrderItemBean setOrderItemBean(Item item, Integer quantity) {
		
		OrderItemBean orderItemBean = new OrderItemBean();
		orderItemBean.setName(item.getName());
		orderItemBean.setImage(item.getImage());
		orderItemBean.setPrice(item.getPrice());
		orderItemBean.setQuantity(quantity);
		orderItemBean.setSubtotal(item.getPrice() * quantity);
		orderItemBean.setStock(item.getStock());
		
		return orderItemBean;
	}
	
	/**
	 * 注文商品リストセットメソッド
	 * 
	 * @return orderItemList 注文商品リスト
	 */
	private List<OrderItemBean> setOrderItemList() {
		
		List<OrderItemBean> orderItemList = new ArrayList<OrderItemBean>();
		List<BasketBean> basketList = (List<BasketBean>) session.getAttribute("basketList");
		
		for (BasketBean listItem : basketList) {
			Item item = itemService.findById(listItem.getId());
			
			if (listItem.getOrderNum() <= item.getStock()) {
				OrderItemBean orderItemBean = setOrderItemBean(item, listItem.getOrderNum());
				orderItemList.add(orderItemBean);
			} else {
				OrderItemBean orderItemBean = setOrderItemBean(item, item.getStock());
				orderItemList.add(orderItemBean);
			}
		}
		return orderItemList;
	}
}
