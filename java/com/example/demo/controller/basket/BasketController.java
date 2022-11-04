package com.example.demo.controller.basket;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.BasketBean;
import com.example.demo.model.Item;
import com.example.demo.service.ItemService;

/**
 * 買い物かご機能コントローラ
 * 
 * @author koto
 */
@Controller
public class BasketController {
	
	/**
	 * 商品リポジトリの紐付け
	 */
	@Autowired
	ItemService itemService;
	
	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;
	
	/**
	 * 買い物かご画面表示メソッド
	 * 
	 * @param model Viewへの値渡し
	 * @return "basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/list", method = RequestMethod.GET)
	public String basketListGet(Model model) {
		
		List<BasketBean> basketList = (List<BasketBean>) session.getAttribute("basketList");
		model.addAttribute("basketList", basketList);
		
		return "basket/basket_list";
	}
	
	/**
	 * 買い物かご画面表示メソッド
	 * （買い物かご商品追加/削除後のフォワード処理）
	 * 
	 * @return "basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/list", method = RequestMethod.POST)
	public String basketList() {
		
		return "basket/basket_list";
	}
	
	/**
	 * 買い物かご商品追加メソッド
	 * 
	 * @param item 商品情報
	 * @param model Viewへの値渡し
	 * @return "forward:/basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/add", method = RequestMethod.POST)
	public String addItem(Item item, Model model) {
		
		BasketBean basketBean = setBasketBean(item.getId());
		
		if (session.getAttribute("basketList") == null) {
			List<BasketBean> newBasketList = new ArrayList<BasketBean>();
			newBasketList.add(basketBean);
			session.setAttribute("basketList", newBasketList);
			model.addAttribute("basketList", newBasketList);
			
			return "forward:/basket/list";
		} else {
			List<BasketBean> basketList = (List<BasketBean>) session.getAttribute("basketList");
			
			for (BasketBean listItem : basketList) {
				if (listItem.getId().equals(item.getId()) && listItem.getStock() > listItem.getOrderNum()) {
					listItem.setOrderNum(listItem.getOrderNum() + 1);
					session.setAttribute("basketList", basketList);
					model.addAttribute("basketList", basketList);
					
					return "forward:/basket/list";
				} else if ((listItem.getId().equals(item.getId()) && listItem.getStock() <= listItem.getOrderNum())) {
					session.setAttribute("basketList", basketList);
					model.addAttribute("basketList", basketList);
					
					return "forward:/basket/list";
				}
			}
			basketList.add(basketBean);
			session.setAttribute("basketList", basketList);
			model.addAttribute("basketList", basketList);
			
			return "forward:/basket/list";
		}
	}

	/**
	 * 買い物かご商品削除メソッド
	 * 
	 * @param basketItem 買い物かご商品情報
	 * @param model Viewへの値渡し
	 * @return "forward:/basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/delete", method = RequestMethod.POST)
	public String deleteItem(@ModelAttribute BasketBean basketItem, Model model) {
		
		List<BasketBean> basketList = (List<BasketBean>) session.getAttribute("basketList");
		
		for (BasketBean listItem : basketList) {
			if (listItem.getId().equals(basketItem.getId()) && listItem.getOrderNum() >= 2) {
				listItem.setOrderNum(listItem.getOrderNum() - 1);
				session.setAttribute("basketList", basketList);
				model.addAttribute("basketList", basketList);
				
				return "forward:/basket/list";
				
			} else if (listItem.getId().equals(basketItem.getId()) && listItem.getOrderNum() == 1) {
				basketList.remove(listItem);
				session.setAttribute("basketList", basketList);
				model.addAttribute("basketList", basketList);
				
				return "forward:/basket/list";
			}
		}
		return "forward:/basket/list";
	}
	
	/**
	 * 買い物かご全商品削除メソッド
	 * 
	 * @return "forward:/basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/deleteAll", method = RequestMethod.POST)
	public String deleteAll() {
		
		session.removeAttribute("basketList");
		
		return "forward:/basket/list";
	}
	
	/**
	 * 買い物かご商品情報セットメソッド
	 * 
	 * @param itemId 商品ID
	 * @return basketBean 買い物かご商品情報
	 */
	private BasketBean setBasketBean(Integer itemId) {
		
		BasketBean basketBean = new BasketBean();
		Item item = itemService.findById(itemId);
		basketBean.setId(item.getId());
		basketBean.setName(item.getName());
		basketBean.setPrice(item.getPrice());
		basketBean.setStock(item.getStock());
		basketBean.setImage(item.getImage());
		basketBean.setOrderNum(1);
		
		return basketBean;
	}
}