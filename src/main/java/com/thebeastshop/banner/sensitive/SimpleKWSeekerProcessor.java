package com.thebeastshop.banner.sensitive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * 简单的敏感词处理器，从配置文件读取敏感词初始化，<br>
 * 
 */
public class SimpleKWSeekerProcessor extends KWSeekerManage {

    private static volatile SimpleKWSeekerProcessor instance;

    /**
     * 获取实例
     * 
     * @return
     */
    public static SimpleKWSeekerProcessor newInstance(List<String> dataList) {
        synchronized (SimpleKWSeekerProcessor.class) {
        	instance = new SimpleKWSeekerProcessor(dataList);
        }
        return instance;
    }

    /**
     * 私有构造器
     */
    private SimpleKWSeekerProcessor(List<String> dataList) {
        initialize(dataList);
    }

    /**
     * 初始化敏感词
     */
    private void initialize(List<String> dataList) {
        Map<String, KWSeeker> seekers = new HashMap<String, KWSeeker>();
        Set<KeyWord> kws;

        kws = new HashSet<KeyWord>();
        for (String word : dataList) {
            kws.add(new KeyWord(word));
        }
        seekers.put("default", new KWSeeker(kws));
        this.seekers.clear();
        this.seekers.putAll(seekers);
    }
}
