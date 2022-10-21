package com.thebeastshop.banner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import java.util.Timer;
import java.util.TimerTask;

import com.thebeastshop.banner.sensitive.KWSeeker;
import com.thebeastshop.banner.sensitive.KeyWord;
import com.thebeastshop.banner.sensitive.SensitiveWordResult;
import com.thebeastshop.banner.sensitive.SimpleKWSeekerProcessor;

/**
 * 敏感词过滤器，以过滤速度优化为主。<br/>
 * * 增加一个敏感词：{@link #put(String)} <br/>
 * * 过滤一个句子：{@link #filter(String, char)} <br/>
 * * 获取默认的单例：{@link #DEFAULT}
 */
public class BannerFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认的单例，使用自带的敏感词库
     */
    public static BannerFilter DEFAULT;
    
    private SimpleKWSeekerProcessor processor;
    
    public final static String SENSI_WORD_DIR = "/data/config/sensi_words";
    
    public final static String SENSI_WORD_PATH = SENSI_WORD_DIR+"/sensi_words.txt";
    
    public final static String SENSI_WORD_LOCAL_PATH = "sensi_words.txt";
    
    public final static String SENSI_WORD_AREA = "default";
    
    static{
    	boolean linuxFlag = init();
    	
		if(linuxFlag){
			new Thread(){
				public void run() {
					try {
						new FileWatch(SENSI_WORD_DIR).handleEvents(DEFAULT);
					} catch (InterruptedException e) {
						System.out.println("文件监听失败");
						e.printStackTrace();
					}
				};
			}.start();
		}
    }
    
    public BannerFilter(BufferedReader reader) {
    	try {
        	List<String> dataList = new ArrayList<String>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            	dataList.add(line);
            }
            processor = SimpleKWSeekerProcessor.newInstance(dataList);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean init(){
    	boolean linuxFlag = false;
		try {
			DEFAULT = new BannerFilter(new BufferedReader(new InputStreamReader(new FileInputStream(SENSI_WORD_PATH), StandardCharsets.UTF_8)));
			linuxFlag = true;
		} catch (FileNotFoundException e) {
			DEFAULT = new BannerFilter(new BufferedReader(new InputStreamReader(BannerFilter.class.getClassLoader().getResourceAsStream(SENSI_WORD_LOCAL_PATH), StandardCharsets.UTF_8)));
		}
		return linuxFlag;
    }
    
    public void put(String word) {
    	KWSeeker seeker = processor.getKWSeeker(SENSI_WORD_AREA);
    	seeker.addWord(new KeyWord(word));
    }

    /**
     * 对句子进行敏感词过滤<br/>
     * </code>
     */
    public BannerResp process(String sentence) {
    	BannerResp resp = new BannerResp();
    	KWSeeker seeker = processor.getKWSeeker(SENSI_WORD_AREA);
    	sentence = sentence.replace(" ", "");
    	List<SensitiveWordResult> words = seeker.findWords(sentence);
    	
    	if(!words.isEmpty()){
    		resp.setHasSensitiveWords(true);
    		for(SensitiveWordResult result : words){
    			resp.addSensitiveWord(result.getWord());
    		}
    		resp.setFilterStr(seeker.replaceWords(sentence));
    	}else{
    		resp.setFilterStr(sentence);
    	}
    	return resp;
    }
}
