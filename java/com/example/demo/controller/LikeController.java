package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.LikeListBean;
import com.example.demo.model.Item;
import com.example.demo.model.Like;
import com.example.demo.model.User;
import com.example.demo.service.ItemService;
import com.example.demo.service.LikeService;

/**
 * お気に入り機能コントローラ
 * 
 * @author koto
 */
@Controller
public class LikeController {
	
	/**
	 * HTTPセッションの紐付け
	 */
	@Autowired
	HttpSession session;
	
	/**
	 * お気に入り商品リポジトリの紐付け
	 */
	@Autowired
	LikeService likeService;
	
	/**
	 * 商品リポジトリの紐付け
	 */
	@Autowired
	ItemService itemService;
	
	/**
	 * お気に入りリスト表示メソッド
	 * 
	 * @param itemId 商品ID
	 * @return "like/like_list" お気に入り画面への遷移
	 */
	@RequestMapping(path = "/like/list", method = RequestMethod.GET)
	public String showLikeList(Model model) {
		
		// セッションに保存されているユーザー情報を取得
		User user = (User) session.getAttribute("user");
		Integer userId = user.getId();
		// ユーザーIDを基にお気に入りリストを取得し、Viewに渡す
		List<LikeListBean> likeList = likeService.findByUserId(userId);
		model.addAttribute("likeList", likeList);
		
		return "like/like_list";
	}
	
	/**
	 * お気に入り商品登録メソッド
	 * 
	 * @param itemId 商品ID
	 * @return "like/like_list" お気に入り画面への遷移
	 */
	@RequestMapping(path = "/like/add", method = RequestMethod.POST)
	public String addItemToLikeList(Item item, Model model) {
		
		// セッションに保存されているユーザー情報を取得
		User user = (User) session.getAttribute("user");
		Integer userId = user.getId();
		Integer itemId = item.getId();
		// ユーザーIDを基にお気に入りリストを取得し、Viewに渡す
		List<LikeListBean> likeList = likeService.findByUserId(userId);
		
		for (LikeListBean likeItem : likeList) {
			if (likeItem.getItemId() == itemId) {
				// 既にお気に入りリストに商品がある場合、商品を追加せずにお気に入りリスト画面に遷移
				model.addAttribute("likeList", likeList);
				
				return "like/like_list";
			}
		}
		// お気に入り商品情報をセット
		Like like = new Like();
		like.setUserId(userId);
		like.setItemId(itemId);
		// セットした情報をDBに登録
		likeService.addLike(like);
		// ユーザーIDを基にお気に入りリストを取得し、Viewに渡す
		List<LikeListBean> newLikeList = likeService.findByUserId(userId);
		model.addAttribute("likeList", newLikeList);
		
		return "like/like_list";
	}
	
	/**
	 * お気に入り商品削除メソッド
	 * 
	 * @param itemId 商品ID
	 * @return "like/like_list" お気に入り画面への遷移
	 */
	@RequestMapping(path = "/like/remove", method = RequestMethod.POST)
	public String removeItemFromLikeList(@ModelAttribute LikeListBean likeItem, Model model) {
		
		// セッションに保存されているユーザー情報を取得
		User user = (User) session.getAttribute("user");
		Integer userId = user.getId();
		// 商品IDを取得
		Integer itemId = likeItem.getItemId();
		// お気に入りリストから商品を削除
		likeService.removeLike(userId, itemId);
		// お気に入りリストを取得しViewに渡す
		List<LikeListBean> likeList = likeService.findByUserId(userId);
		model.addAttribute("likeList", likeList);
		
		return "like/like_list";
	}
}
