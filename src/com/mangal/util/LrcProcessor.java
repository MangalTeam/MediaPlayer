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
	    private long currentTime = 0;//�����ʱʱ��  
		private String currentContent = null;//�����ʱ���         
    
	    public LrcInfo parser(String path) throws Exception{  
	        InputStream in = readLrcFile(path); 
	        parser(in);  
	        return lrcInfo;  
	    }
	    
	    /** 
	     * �����ļ�·������ȡ�ļ�������һ�������� 
	     *  
	     * @param path 
	     *            ·�� 
	     * @return ������ 
	     * @throws FileNotFoundException 
	     */  
	    private InputStream readLrcFile(String path) throws FileNotFoundException {  
	        File f = new File(path);  
	        InputStream is = new FileInputStream(f);  
	        return is;  
	    }  
	 
	      
	    /** 
	     * ���������е���Ϣ����������һ��LrcInfo���� 
	     *  
	     * @param inputStream 
	     *            ������ 
	     * @return �����õ�LrcInfo���� 
	     * @throws IOException 
	     */  
	    public void parser(InputStream inputStream) throws IOException {  
	    	// �����װ  
	        InputStreamReader inr = new InputStreamReader(inputStream);  
	        BufferedReader reader = new BufferedReader(inr);  
	        // һ��һ�еĶ���ÿ��һ�У�����һ��  
	        String line = null;  
	        while ((line = reader.readLine()) != null) {  
	            parserLine(line);  
	        } 
	    }  	
	    
	    /** 
	     * ����������ʽ����ÿ�о������ 
	     * ���ڽ���������󣬽�������������Ϣ������LrcInfo������ 
	     *  
	     * @param str 
	     */  
	    private void parserLine(String str) {  
	        // ȡ�ø�������Ϣ  
	        if (str.startsWith("[ti:")) {  
	            String title = str.substring(4, str.length() - 1);  
	            System.out.println("title--->" + title);  
	            lrcInfo.setTi(title);  
	  
	        }// ȡ�ø�����Ϣ  
	        else if (str.startsWith("[ar:")) {  
	            String singer = str.substring(4, str.length() - 1);  
	            System.out.println("singer--->" + singer);  
	            lrcInfo.setAr(singer);  
	  
	        }// ȡ��ר����Ϣ  
	        else if (str.startsWith("[al:")) {  
	            String album = str.substring(4, str.length() - 1);  
	            System.out.println("album--->" + album);  
	            lrcInfo.setAl(album);  
	  
	        }//ȡ����������Ϣ
	        else if(str.startsWith("[by:")){
	        	String by = str.substring(4, str.length() - 1);  
	            System.out.println("by--->" + by);  
	            lrcInfo.setBy(by);  
	        }// ͨ������ȡ��ÿ������Ϣ  
	        else {  
	            // �����������  
	            String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";  
	            // ����  
	            Pattern pattern = Pattern.compile(reg);  
	            Matcher matcher = pattern.matcher(str);  
	  
	            // �������ƥ�����ִ�����²���  
	            while (matcher.find()) {  
	                // �õ�ƥ�����������  
	                //String msg = matcher.group();  
	                // �õ����ƥ���ʼ������  
	                //int start = matcher.start();  
	                // �õ����ƥ�������������  
	                //int end = matcher.end();  
	  
	                // �õ����ƥ�����е�����  
	                int groupCount = matcher.groupCount();  
	                // �õ�ÿ����������  
	                for (int i = 0; i <= groupCount; i++) {  
	                    String timeStr = matcher.group(i);  
	                    if (i == 1) {  
	                        // ���ڶ����е���������Ϊ��ǰ��һ��ʱ���  
	                        currentTime = timeToLong(timeStr);  
	                    }  
	                }
	  
	                // �õ�ʱ���������  
	                String[] content = pattern.split(str);  
	                // �����������  
	                for (int i = 0; i < content.length; i++) {  
	                    if (i == content.length - 1) {  
	                        // ����������Ϊ��ǰ����  
	                        currentContent = content[i];  
	                    }  
	                }
	                lrcInfo.setTimeMills(currentTime);
	                lrcInfo.setMessages(currentContent);
	            }  
	        }  
	    }  
	
	
	//�����ӣ���ȫ��ת���ɺ��루ǧ��֮һ��
	public Long timeToLong(String timeStr){
		String s[] = timeStr.split(":");
		int min = Integer.parseInt(s[0]);
		String ss[] = s[1].split("\\.");
		int sec = Integer.parseInt(ss[0]);
		int mill = Integer.parseInt(ss[1]);
		return min * 60 * 1000 + sec * 1000 + mill *10L;
	}
}
