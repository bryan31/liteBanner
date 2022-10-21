/**
 * <p>Title: liteBanner</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * @author Bryan.Zhang
 * @email 47483522@qq.com
 * @Date 2017-3-5
 * @version 1.0
 */
package com.thebeastshop.banner;

import java.util.ArrayList;
import java.util.List;

public class BannerResp {
	
	private boolean hasSensitiveWords;
	
	private List<String> sensitiveWords = new ArrayList<String>();
	
	private String filterStr;

	public boolean isHasSensitiveWords() {
		return hasSensitiveWords;
	}

	public void setHasSensitiveWords(boolean hasSensitiveWords) {
		this.hasSensitiveWords = hasSensitiveWords;
	}

	public List<String> getSensitiveWords() {
		return sensitiveWords;
	}

	public void setSensitiveWords(List<String> sensitiveWords) {
		this.sensitiveWords = sensitiveWords;
	}

	public String getFilterStr() {
		return filterStr;
	}

	public void setFilterStr(String filterStr) {
		this.filterStr = filterStr;
	}
	
	public void addSensitiveWord(String str){
		sensitiveWords.add(str);
	}

	@Override
	public String toString() {
		return "BannerResp [hasSensitiveWords=" + hasSensitiveWords
				+ ", sensitiveWords=" + sensitiveWords + ", filterStr="
				+ filterStr + "]";
	}
}