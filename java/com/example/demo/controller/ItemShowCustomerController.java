package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Item;
import com.example.demo.service.ItemService;

/**
 * 商品表示機能コントローラ
 * 
 * @author koto
 */
@Controller
public class ItemShowCustomerController {
	
	// 1ページあたりの表示数
	private final String limit = "8";
	// ページネーションで表示するページ数
	private int showPageSize = 3;
	
	/**
	 * 商品リポジトリの紐付け
	 */
	@Autowired
	ItemService itemService;
	
	/**
	 * 商品一覧表示メソッド
	 * 
	 * @param model Viewへの値渡し
	 * @return "index" トップ画面への遷移
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(Model model, @RequestParam(name = "page", required = false) String page) {
		// パラメータから現在のページ番号を取得
		String currentPage = page;
		// 初回表示時はパラメータがnullのため、ページ番号を1に設定
		if(currentPage == null) {
			currentPage = "1";
		}
		// 商品検索に使用するページング情報を用意
		HashMap<String, String> pagination = new HashMap<String, String>();
		pagination.put("limit", limit);
		pagination.put("page", currentPage);
		// 商品情報を取得しListに格納
		List<Item> itemList = itemService.findAll(pagination);
		// 全商品の総数を取得
		int total = itemService.findAll().size();
		// 総数を基に総ページ数を算出
		int totalPage = (total + Integer.parseInt(limit) -1) / Integer.parseInt(limit);
		// 現在のページ番号を取得
		int pageNum = Integer.parseInt(currentPage);
		// 最初のページ番号を算出
		int firstPage = pageNum - (pageNum -1) % showPageSize;
		// 最後のページ番号を算出
		int lastPage = (firstPage + showPageSize -1 > totalPage) ? totalPage : firstPage + showPageSize -1;
		// 商品情報とページング情報をViewに渡す
		model.addAttribute("itemList", itemList);
		model.addAttribute("total", total);
		model.addAttribute("page", pageNum);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("firstPage", firstPage);
		model.addAttribute("lastPage", lastPage);
		
		return "index";
	}
	
	/**
	 * 商品絞り込み検索メソッド
	 * 
	 * @param sortType 並べ替え（1:新着順、2:人気順）
	 * @param categoryId カテゴリーID
	 * @param model Viewへの値渡し
	 * @return "index" トップ画面への遷移
	 */
	@RequestMapping(path = "/item/list", method = RequestMethod.GET)
	public String showItemsWithFilter(@RequestParam(name = "sortType", required = false) int sortType,
									@RequestParam(name = "categoryId", required = false) int categoryId,
									@RequestParam(name = "page", required = false) String page, Model model) {
		// パラメータから現在のページ番号を取得
		String currentPage = page;
		// 初回表示時はパラメータがnullのため、ページ番号を1に設定
		if(currentPage == null) {
			currentPage = "1";
		}
		// 商品検索に使用するページング情報を用意
		HashMap<String, String> pagination = new HashMap<String, String>();
		pagination.put("limit", limit);
		pagination.put("page", currentPage);
		
		if (sortType == 1 && categoryId == 0) {
			// 商品を[新着順]で取得
			List<Item> itemList = itemService.findByOrderByInsertDateDesc(pagination);
			// 商品の総数を取得
			int total = itemService.findByOrderByInsertDateDesc().size();
			// 総数を基に総ページ数を算出
			int totalPage = (total + Integer.parseInt(limit) -1) / Integer.parseInt(limit);
			// 現在のページ番号を取得
			int pageNum = Integer.parseInt(currentPage);
			// 最初のページ番号を算出
			int firstPage = pageNum - (pageNum -1) % showPageSize;
			// 最後のページ番号を算出
			int lastPage = (firstPage + showPageSize -1 > totalPage) ? totalPage : firstPage + showPageSize -1;
			// 商品情報とページング情報をViewに渡す
			model.addAttribute("itemList", itemList);
			model.addAttribute("total", total);
			model.addAttribute("page", pageNum);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("firstPage", firstPage);
			model.addAttribute("lastPage", lastPage);
			
		} else if (sortType == 2 && categoryId == 0) {
			// 商品を[人気順]で取得
			List<Item> itemList = itemService.findByOrderByOrderItemListSizeDesc(pagination);
			// 全商品の総数を取得
			int total = itemService.findByOrderByOrderItemListSizeDesc().size();
			// 総数を基に総ページ数を算出
			int totalPage = (total + Integer.parseInt(limit) -1) / Integer.parseInt(limit);
			// 現在のページ番号を取得
			int pageNum = Integer.parseInt(currentPage);
			// 最初のページ番号を算出
			int firstPage = pageNum - (pageNum -1) % showPageSize;
			// 最後のページ番号を算出
			int lastPage = (firstPage + showPageSize -1 > totalPage) ? totalPage : firstPage + showPageSize -1;
			// 商品情報とページング情報をViewに渡す
			model.addAttribute("itemList", itemList);
			model.addAttribute("total", total);
			model.addAttribute("page", pageNum);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("firstPage", firstPage);
			model.addAttribute("lastPage", lastPage);
			
		} else if (sortType == 1 && categoryId != 0) {
			// 商品を[新着順+カテゴリ別]で取得
			List<Item> itemList = itemService.findByCategoryIdOrderByInsertDateDesc(pagination, categoryId);
			// 商品の総数を取得
			int total = itemService.findByCategoryIdOrderByInsertDateDesc(categoryId).size();
			// 総数を基に総ページ数を算出
			int totalPage = (total + Integer.parseInt(limit) -1) / Integer.parseInt(limit);
			// 現在のページ番号を取得
			int pageNum = Integer.parseInt(currentPage);
			// 最初のページ番号を算出
			int firstPage = pageNum - (pageNum -1) % showPageSize;
			// 最後のページ番号を算出
			int lastPage = (firstPage + showPageSize -1 > totalPage) ? totalPage : firstPage + showPageSize -1;
			// 商品情報とページング情報をViewに渡す
			model.addAttribute("itemList", itemList);
			model.addAttribute("total", total);
			model.addAttribute("page", pageNum);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("firstPage", firstPage);
			model.addAttribute("lastPage", lastPage);
			
		} else if (sortType == 2 && categoryId != 0) {
			// 商品を[人気順+カテゴリ別]で取得
			List<Item> itemList = itemService.findByCategoryIdOrderByOrderItemListSizeDesc(pagination, categoryId);
			// 商品の総数を取得
			int total = itemService.findByCategoryIdOrderByOrderItemListSizeDesc(categoryId).size();
			// 総数を基に総ページ数を算出
			int totalPage = (total + Integer.parseInt(limit) -1) / Integer.parseInt(limit);
			// 現在のページ番号を取得
			int pageNum = Integer.parseInt(currentPage);
			// 最初のページ番号を算出
			int firstPage = pageNum - (pageNum -1) % showPageSize;
			// 最後のページ番号を算出
			int lastPage = (firstPage + showPageSize -1 > totalPage) ? totalPage : firstPage + showPageSize -1;
			// 商品情報とページング情報をViewに渡す
			model.addAttribute("itemList", itemList);
			model.addAttribute("total", total);
			model.addAttribute("page", pageNum);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("firstPage", firstPage);
			model.addAttribute("lastPage", lastPage);
		}
		// 検索条件保持用にパラメータをViewに渡す
		model.addAttribute("selectedSort", sortType);
		model.addAttribute("selectedCategory", categoryId);
		
		return "index";
	}
	
	/**
	 * 商品詳細表示メソッド
	 * 
	 * @param id 商品ID
	 * @param model Viewへの値渡し
	 * @return "item/item_detali" 商品詳細画面への遷移
	 */
	@RequestMapping(path = "/item/detail/{id}", method = RequestMethod.GET)
	public String showItemDetail(@PathVariable int id, Model model) {
		
		// GETパラメータのＩＤを基に商品情報を取得し、Viewに渡す
		model.addAttribute("item", itemService.findById(id));
		
		return "item/item_detail";
	}
}
