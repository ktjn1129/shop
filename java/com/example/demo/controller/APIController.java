package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 外部API接続用コントローラ
 * 
 * @author koto
 */
@Controller
public class APIController {
	
	/**
	 * 天気予報取得メソッド
	 * 
	 * @param model ビューへの値渡し
	 * @return "weather" 天気予報画面に遷移
	 */
	@RequestMapping(path = "/weather", method = RequestMethod.GET)
	public String getWeather(Model model) throws IOException {
		// 天気予報情報をJson形式で取得
		String url = "https://www.jma.go.jp/bosai/forecast/data/overview_forecast/260000.json";
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		String responseBody = response.body().string();
		System.out.println(responseBody);
		
		// 取得したJson形式データをObject形式にマッピング
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> weather = mapper.readValue(responseBody, new TypeReference<HashMap<String, Object>>(){});
		model.addAttribute("weather", weather);
		
		return "weather";
	}
}
