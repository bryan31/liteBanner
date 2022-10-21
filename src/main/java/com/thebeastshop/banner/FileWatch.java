package com.thebeastshop.banner;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileWatch {
	
	private WatchService watchService;
	
	public FileWatch(String pathStr){
		try{
			Path path = Paths.get(pathStr);
			watchService = FileSystems.getDefault().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE);  
		}catch(Exception e){
			System.out.println("watch create fail");
			e.printStackTrace();
		}
	}
	
	public void handleEvents(BannerFilter bannerFilter) throws InterruptedException{
        while(true){  
            WatchKey key = watchService.take();  
            for(WatchEvent<?> event : key.pollEvents()){  
                WatchEvent.Kind kind = event.kind();  
                  
                if(kind == StandardWatchEventKinds.OVERFLOW){
                    continue;  
                }  
                  
                WatchEvent<Path> e = (WatchEvent<Path>)event;  
                Path fileName = e.context();  
                
                System.out.printf("事件%s触发,文件名为%s%n",kind.name(),fileName);
                bannerFilter.init();
            }  
            if(!key.reset()){  
                break;  
            }  
        }  
    }  
}
