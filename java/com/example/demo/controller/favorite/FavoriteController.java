package com.example.demo.controller.favorite;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.FavoriteListBean;
import com.example.demo.model.Favorite;
import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.ItemService;

/**
 * お気に入り機能コントローラ
 * 
 * @author koto
 */
@Controller
public class FavoriteController {
	
	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;
	
	/**
	 * お気に入り商品リポジトリの紐付け
	 */
	@Autowired
	FavoriteService favoriteService;
	
	/**
	 * 商品リポジトリの紐付け
	 */
	@Autowired
	ItemService itemService;
	
	/**
	 * お気に入りリスト表示メソッド
	 * 
	 * @param model Viewへの値渡し
	 * @return "favorite/favorite_list" お気に入り画面への遷移
	 */
	@RequestMapping(path = "/favorite/list", method = RequestMethod.GET)
	public String showFavoriteList(Model model) {
		
		User loginUser = (User) session.getAttribute("user");
		List<FavoriteListBean> favoriteList = favoriteService.findByUserId(loginUser.getId());
		model.addAttribute("favoriteList", favoriteList);
		
		return "favorite/favorite_list";
	}
	
	/**
	 * お気に入り商品登録メソッド
	 * 
	 * @param item 商品情報
	 * @param model Viewへの値渡し
	 * @return "favorite/favorite_list" お気に入り画面への遷移
	 */
	@RequestMapping(path = "/favorite/add", method = RequestMethod.POST)
	public String addItemToFavoriteList(Item item, Model model) {
		
		User loginUser = (User) session.getAttribute("user");
		List<FavoriteListBean> favoriteList = favoriteService.findByUserId(loginUser.getId());
		
		for (FavoriteListBean favoriteItem : favoriteList) {
			if (favoriteItem.getItemId().equals(item.getId())) {
				model.addAttribute("favoriteList", favoriteList);
				
				return "favorite/favorite_list";
			}
		}
		Favorite favorite = setFavorite(loginUser.getId(), item.getId());
		favoriteService.addFavorite(favorite);
		List<FavoriteListBean> newFavoriteList = favoriteService.findByUserId(loginUser.getId());
		model.addAttribute("favoriteList", newFavoriteList);
		
		return "favorite/favorite_list";
	}
	
	/**
	 * お気に入り商品削除メソッド
	 * 
	 * @param favoriteItem お気に入り商品ID
	 * @param model Viewへの値渡し
	 * @return "favorite/favorite_list" お気に入り画面への遷移
	 */
	@RequestMapping(path = "/favorite/remove", method = RequestMethod.POST)
	public String removeItemFromFavoriteList(@ModelAttribute FavoriteListBean favoriteItem, Model model) {
		
		User loginUser = (User) session.getAttribute("user");
		favoriteService.removeFavorite(loginUser.getId(), favoriteItem.getItemId());
		List<FavoriteListBean> favoriteList = favoriteService.findByUserId(loginUser.getId());
		model.addAttribute("favoriteList", favoriteList);
		
		return "favorite/favorite_list";
	}
	
	/**
	 * お気に入り情報セットメソッド
	 * 
	 * @param userId ユーザーID
	 * @param itemId 商品ID
	 * @return favorite お気に入り情報
	 */
	private Favorite setFavorite(Integer userId, Integer itemId) {
		
		Favorite favorite = new Favorite();
		favorite.setUserId(userId);
		favorite.setItemId(itemId);
		
		return favorite;
	}
}