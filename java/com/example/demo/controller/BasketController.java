package com.example.demo.controller;

import java.util.ArrayList;

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
	 * 買い物かご商品追加メソッド
	 * 
	 * @param item 商品情報
	 * @param model ビューへの値渡し
	 * @return "forward:/basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/add", method = RequestMethod.POST)
	public String addItem(Item item, Model model) {
		
		Item basketItem = itemService.findById(item.getId());
		BasketBean basketBean = new BasketBean();
		basketBean.setId(basketItem.getId());
		basketBean.setName(basketItem.getName());
		basketBean.setPrice(basketItem.getPrice());
		basketBean.setStock(basketItem.getStock());
		basketBean.setImage(basketItem.getImage());
		basketBean.setOrderNum(1);
		
		if (session.getAttribute("basketList") == null) {
			// 買い物かご情報がない場合
			ArrayList<BasketBean> basketList = new ArrayList<BasketBean>();
			basketList.add(basketBean);
			session.setAttribute("basketList", basketList);
			model.addAttribute("basketList", basketList);
			
			return "forward:/basket/list";
		} else {
			@SuppressWarnings("unchecked")
			ArrayList<BasketBean> basketList = (ArrayList<BasketBean>) session.getAttribute("basketList");
			
			for (BasketBean listItem : basketList) {
				Integer itemId = listItem.getId();
				Integer basketItemId = basketItem.getId();
				Integer stock = listItem.getStock();
				Integer orderNum = listItem.getOrderNum();
				
				if (itemId == basketItemId && stock > orderNum) {
					// 商品が既に買い物かごにあり、在庫数が注文個数を上回る場合
					listItem.setOrderNum(orderNum + 1);
					session.setAttribute("basketList", basketList);
					model.addAttribute("basketList", basketList);
					
					return "forward:/basket/list";
					
				} else if (itemId == basketItemId && stock <= orderNum) {
					// 商品が既に買い物かごにあり、在庫数が注文個数以下の場合
					session.setAttribute("basketList", basketList);
					model.addAttribute("basketList", basketList);
					
					return "forward:/basket/list";
				}
			}
			// 買い物かご情報があり、商品が買い物かごにない場合
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
	 * @param model ビューへの値渡し
	 * @return "forward:/basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/delete", method = RequestMethod.POST)
	public String deleteItem(@ModelAttribute BasketBean basketItem, Model model) {
		
		@SuppressWarnings("unchecked")
		ArrayList<BasketBean> basketList = (ArrayList<BasketBean>) session.getAttribute("basketList");
		
		for (BasketBean listItem : basketList) {
			Integer itemId = listItem.getId();
			Integer basketItemId = basketItem.getId();
			Integer orderNum = listItem.getOrderNum();
			
			if (itemId == basketItemId && orderNum == 1) {
				basketList.remove(listItem);
				session.setAttribute("basketList", basketList);
				model.addAttribute("basketList", basketList);
				
				return "forward:/basket/list";
				
			} else if (itemId == basketItemId && orderNum >= 2) {
				listItem.setOrderNum(orderNum - 1);
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
	 * 買い物かご画面表示メソッド
	 * ※買い物かご商品追加/削除後のフォワード処理
	 * 
	 * @param model ビューへの値渡し
	 * @return "basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/list", method = RequestMethod.POST)
	public String basketList(Model model) {
		
		return "basket/basket_list";
	}
	
	/**
	 * 買い物かご画面表示メソッド
	 * ※ナビゲーションバー「買い物かご」ボタン押下時
	 * 
	 * @param model ビューへの値渡し
	 * @return "basket/list" 買い物かご一覧画面への遷移
	 */
	@RequestMapping(path = "/basket/list", method = RequestMethod.GET)
	public String basketListGet(Model model) {
		
		@SuppressWarnings("unchecked")
		ArrayList<BasketBean> basketList = (ArrayList<BasketBean>) session.getAttribute("basketList");
		model.addAttribute("basketList", basketList);
		
		return "basket/basket_list";
	}
}