package test11;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import  java.util.*;
public class demo {
	private static Jedis jedis;
	private static BufferedReader bufferedReader;	
	public static void loadUser(String path) throws IOException{
		FileWriter file = new FileWriter(path);
		file.flush();
		for (String key : jedis.keys("*")) {	
			String jsonStr;
		    file.write("{");
			String username=key;
	        Gson gsonObj = new Gson();
	        if(jedis.type(key).equalsIgnoreCase("hash")){
		    Map<String, String> properties = jedis.hgetAll(username);
		    jsonStr = gsonObj.toJson(properties);}
	        else
	        {
	        	Set<String> set = jedis.smembers(key);
	        	 jsonStr = gsonObj.toJson(set);
	        }
		    String s="";
		    for(int i=0;i<username.length();i++)
		    {	   
				if(username.charAt(i)==':')
		    		s=s+"}";
		    }
		    String ns=username.replace(":", "\":{\"");
		 try  { 
			 if(jedis.type(key).equalsIgnoreCase("hash")){
			 JSONParser parser = new JSONParser(); 
			    JSONObject json = (JSONObject) parser.parse(jsonStr);
			    file.write("\""+ns+"\":");
	            file.write(json.toJSONString());}
			 else{
				 JSONParser parser = new JSONParser(); 
				    JSONArray json = (JSONArray) parser.parse(jsonStr);
				    file.write("\""+ns+"\":");
		            file.write(json.toJSONString());
			 }   
	            file.write(s+"}"+"\n"+System.getProperty( "line.separator" ));
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
				e.printStackTrace();
			}	
				}
		 file.close();
	}
	
	
	public static void readJsonFile(String path) {
    	int p=0,n=0;
        BufferedReader br = null;
        try {
            String sCurrentLine;
            FileReader fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            while((sCurrentLine = bufferedReader.readLine()) != null) {
            	if(sCurrentLine.isEmpty())
            	{
            		continue;
            	}
            	 p=sCurrentLine.lastIndexOf('{');
                 n=sCurrentLine.indexOf('}');
            String js=sCurrentLine.substring(p,n+1);
               String s=((sCurrentLine.substring(0,p-1)).replace("{","")).replace("\"","");
                Map<String, Object> retMap = new Gson().fromJson(
                	    js, new TypeToken<HashMap<String, Object>>() {}.getType()
                	);
                for (Map.Entry<String, Object> entry : retMap.entrySet()) {
                	Map<String, String> val = new HashMap<String, String>();
                	String key = entry.getKey();
                    Object value = entry.getValue();
                   
                    val.put(key,value+"");
                    jedis.hmset( s, val);
                      
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                }
            }
        }
    
	public static void main(String[] args)throws Exception {		
	    try{
	    	String path=args[0];
	    	String port=args[1];
	    	String part=args[2];
	    	String fun=args[3];
	    jedis = new Jedis("localhost",Integer.parseInt(port));
        jedis.select(Integer.parseInt(part));
	    System.out.println("Connected");
	    if(fun.equalsIgnoreCase("f"))
	        loadUser(String.format(path));
	    else
	    	readJsonFile(path);
	    }catch(Exception e)
	    {
	    	System.out.println(e);
	   	}
	    }
	}

