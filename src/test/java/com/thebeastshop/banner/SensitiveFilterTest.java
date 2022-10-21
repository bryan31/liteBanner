package com.thebeastshop.banner;

import junit.framework.TestCase;

public class SensitiveFilterTest extends TestCase{
	
	public void test() throws Exception{
		// 加载默认词典
		BannerFilter filter = BannerFilter.DEFAULT;
		// 向过滤器增加一个词，额外造个词
		filter.put("坏孩子");
		
		String sentence = "我是一个TMD的坏孩子";
		BannerResp resp = filter.process(sentence);
		System.out.println(resp);
	}
}
