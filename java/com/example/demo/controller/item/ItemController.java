package com.example.demo.controller.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 商品一覧表示機能コントローラ
 * 
 * @author koto
 */
@Controller
public class ItemController {
	
	/**
	 * 商品リポジトリの紐付け
	 */
	@Autowired
	ItemService itemService;
	
	private final String limitPerPage = "8";
	private final int PageSizeToDisplay = 3;
	
	/**
	 * クエリ用ページング情報定義メソッド
	 * 
	 * @param page ページ番号
	 * @return paginationInfo ページング情報
	 */
	private Map<String, String> buildPaginationInfo(String page) {
		
		String currPageNum = page;
		if(currPageNum == null) {
			currPageNum = "1";
		}
		Map<String, String> paginationInfo = new HashMap<String, String>();
		paginationInfo.put("limit", limitPerPage);
		paginationInfo.put("page", currPageNum);
		
		return paginationInfo;
	}
	
	/**
	 * ページネーションセットメソッド
	 * 
	 * 
	 * @param　itemList 商品リスト
	 * @param page ページ番号
	 * @param model Viewへの値渡し
	 * @return 
	 */
	private Map<String, Integer> setupPagination(Integer itemListNum, String page, Model model) {
		
		int totalItemNum = itemListNum;
		int totalPageNum = (totalItemNum + Integer.parseInt(limitPerPage) -1) / Integer.parseInt(limitPerPage);
		int currPageNum = (page == null) ? 1 : Integer.parseInt(page);
		int firstPageNum = currPageNum - (currPageNum -1) % PageSizeToDisplay;
		int lastPageNum = (firstPageNum + PageSizeToDisplay -1 > totalPageNum) ? totalPageNum : firstPageNum + PageSizeToDisplay -1;
		
		Map<String, Integer> pagination = new HashMap<String, Integer>();
		pagination.put("total", totalItemNum);
		pagination.put("totalPage", totalPageNum);
		pagination.put("currPage", currPageNum);
		pagination.put("firstPage", firstPageNum);
		pagination.put("lastPage", lastPageNum);
		
		return pagination;
	}
	
	/**
	 * 商品一覧表示メソッド
	 * （初期状態は[新着順]とする）
	 * 
	 * @param page ページ番号
	 * @param model Viewへの値渡し
	 * @return "index" トップ画面への遷移
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(@RequestParam(name = "page", required = false) String page, Model model) {
		
		Map<String, String> paginationInfo = buildPaginationInfo(page);
		List<Item> itemListPerPage = itemService.SortByNewestToOldest(paginationInfo);
		model.addAttribute("itemList", itemListPerPage);
		
		Integer itemListNum = itemService.SortByNewestToOldest().size();
		Map<String, Integer> pagination = setupPagination(itemListNum, page, model);
		model.addAttribute("page", pagination);
		
		return "index";
	}
	
	/**
	 * 商品絞り込み検索メソッド
	 * 
	 * @param sortType 並び順（1:新着順、2:人気順）
	 * @param categoryId カテゴリーID
	 * @param page ページ番号
	 * @param model Viewへの値渡し
	 * @return "index" トップ画面への遷移
	 */
	@RequestMapping(path = "/item/list", method = RequestMethod.GET)
	public String showItemsWithFilter(@RequestParam(name = "sortType", required = false) int sortType,
									@RequestParam(name = "categoryId", required = false) int categoryId,
									@RequestParam(name = "page", required = false) String page, Model model) {
		
		Map<String, String> paginationInfo = buildPaginationInfo(page);
		
		if (sortType == 1 && categoryId == 0) {
			// 全商品　+ 新着順
			List<Item> itemListPerPage = itemService.SortByNewestToOldest(paginationInfo);
			model.addAttribute("itemList", itemListPerPage);
			
			Integer itemListNum = itemService.SortByNewestToOldest().size();
			Map<String, Integer> pagination = setupPagination(itemListNum, page, model);
			model.addAttribute("page", pagination);
			
		} else if (sortType == 2 && categoryId == 0) {
			// 全商品 + 人気順
			List<Item> itemListPerPage = itemService.SortByPopularity(paginationInfo);
			model.addAttribute("itemList", itemListPerPage);
			
			Integer itemListNum = itemService.SortByPopularity().size();
			Map<String, Integer> pagination = setupPagination(itemListNum, page, model);
			model.addAttribute("page", pagination);
			
		} else if (sortType == 1 && categoryId != 0) {
			// カテゴリー別 + 新着順
			List<Item> itemListPerPage = itemService.findByCategoryIdSortByNewestToOldest(paginationInfo, categoryId);
			model.addAttribute("itemList", itemListPerPage);
			
			Integer itemListNum = itemService.findByCategoryIdSortByNewestToOldest(categoryId).size();
			Map<String, Integer> pagination = setupPagination(itemListNum, page, model);
			model.addAttribute("page", pagination);
			
		} else if (sortType == 2 && categoryId != 0) {
			// カテゴリー別 + 人気順
			List<Item> itemListPerPage = itemService.findByCategoryIdSortByPopularity(paginationInfo, categoryId);
			model.addAttribute("itemList", itemListPerPage);
			
			Integer itemListNum = itemService.findByCategoryIdSortByPopularity(categoryId).size();
			Map<String, Integer> pagination = setupPagination(itemListNum, page, model);
			model.addAttribute("page", pagination);
		}
		// 検索条件の保持用パラメータ
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
		
		model.addAttribute("item", itemService.findById(id));
		
		return "item/item_detail";
	}
}
