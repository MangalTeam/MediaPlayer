package com.mangal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mangal.module.LrcInfo;


public class LrcProcessor {
		private LrcInfo lrcInfo = new LrcInfo();  
	    private long currentTime = 0;//存放临时时间  
		private String currentContent = null;//存放临时歌词         
    
	    public LrcInfo parser(String path) throws Exception{  
	        InputStream in = readLrcFile(path); 
	        parser(in);  
	        return lrcInfo;  
	    }
	    
	    /** 
	     * 根据文件路径，读取文件，返回一个输入流 
	     *  
	     * @param path 
	     *            路径 
	     * @return 输入流 
	     * @throws FileNotFoundException 
	     */  
	    private InputStream readLrcFile(String path) throws FileNotFoundException {  
	        File f = new File(path);  
	        InputStream is = new FileInputStream(f);  
	        return is;  
	    }  
	 
	      
	    /** 
	     * 将输入流中的信息解析，返回一个LrcInfo对象 
	     *  
	     * @param inputStream 
	     *            输入流 
	     * @return 解析好的LrcInfo对象 
	     * @throws IOException 
	     */  
	    public void parser(InputStream inputStream) throws IOException {  
	    	// 三层包装  
	        InputStreamReader inr = new InputStreamReader(inputStream);  
	        BufferedReader reader = new BufferedReader(inr);  
	        // 一行一行的读，每读一行，解析一行  
	        String line = null;  
	        while ((line = reader.readLine()) != null) {  
	            parserLine(line);  
	        } 
	    }  	
	    
	    /** 
	     * 利用正则表达式解析每行具体语句 
	     * 并在解析完该语句后，将解析出来的信息设置在LrcInfo对象中 
	     *  
	     * @param str 
	     */  
	    private void parserLine(String str) {  
	        // 取得歌曲名信息  
	        if (str.startsWith("[ti:")) {  
	            String title = str.substring(4, str.length() - 1);  
	            System.out.println("title--->" + title);  
	            lrcInfo.setTi(title);  
	  
	        }// 取得歌手信息  
	        else if (str.startsWith("[ar:")) {  
	            String singer = str.substring(4, str.length() - 1);  
	            System.out.println("singer--->" + singer);  
	            lrcInfo.setAr(singer);  
	  
	        }// 取得专辑信息  
	        else if (str.startsWith("[al:")) {  
	            String album = str.substring(4, str.length() - 1);  
	            System.out.println("album--->" + album);  
	            lrcInfo.setAl(album);  
	  
	        }//取得作词人信息
	        else if(str.startsWith("[by:")){
	        	String by = str.substring(4, str.length() - 1);  
	            System.out.println("by--->" + by);  
	            lrcInfo.setBy(by);  
	        }// 通过正则取得每句歌词信息  
	        else {  
	            // 设置正则规则  
	            String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";  
	            // 编译  
	            Pattern pattern = Pattern.compile(reg);  
	            Matcher matcher = pattern.matcher(str);  
	  
	            // 如果存在匹配项，则执行以下操作  
	            while (matcher.find()) {  
	                // 得到匹配的所有内容  
	                //String msg = matcher.group();  
	                // 得到这个匹配项开始的索引  
	                //int start = matcher.start();  
	                // 得到这个匹配项结束的索引  
	                //int end = matcher.end();  
	  
	                // 得到这个匹配项中的组数  
	                int groupCount = matcher.groupCount();  
	                // 得到每个组中内容  
	                for (int i = 0; i <= groupCount; i++) {  
	                    String timeStr = matcher.group(i);  
	                    if (i == 1) {  
	                        // 将第二组中的内容设置为当前的一个时间点  
	                        currentTime = timeToLong(timeStr);  
	                    }  
	                }
	  
	                // 得到时间点后的内容  
	                String[] content = pattern.split(str);  
	                // 输出数组内容  
	                for (int i = 0; i < content.length; i++) {  
	                    if (i == content.length - 1) {  
	                        // 将内容设置为当前内容  
	                        currentContent = content[i];  
	                    }  
	                }
	                lrcInfo.setTimeMills(currentTime);
	                lrcInfo.setMessages(currentContent);
	            }  
	        }  
	    }  
	
	
	//将分钟，秒全部转换成毫秒（千分之一）
	public Long timeToLong(String timeStr){
		String s[] = timeStr.split(":");
		int min = Integer.parseInt(s[0]);
		String ss[] = s[1].split("\\.");
		int sec = Integer.parseInt(ss[0]);
		int mill = Integer.parseInt(ss[1]);
		return min * 60 * 1000 + sec * 1000 + mill *10L;
	}
}
