
## liteBanner
liteBanner是一个轻量级的本地违禁词检测，整句过滤的Jar包

* 项目不带违禁词库，请自己构建
* 可以输出过滤后的字符串
* 可以自动检测出带空格，加分隔符的违禁词
* 1秒可以过滤一部红楼梦
* 1秒峰值可以过滤50w的词语

```java

	// 加载默认词典
	BannerFilter filter = BannerFilter.DEFAULT;
	// 向过滤器增加一个词，额外造个词
	filter.put("buffer");
    filter.put("打野英雄");
    filter.put("孤胆流氓");
	
	String sentence = "我是一个有红buffer的打野英雄，追杀孤胆流氓";
	BannerResp resp = filter.process(sentence);
	System.out.println(resp);

```
返回：

```
BannerResp [hasSensitiveWords=true, sensitiveWords=[buffer, 打野英雄, 孤胆流氓], filterStr=我是一个有红******的****，追杀***]
```

