package xlsgenerator;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.xmlbeans.impl.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Test {
	
	public static void main(String args[]){
		
		String json = "eyJCMiI6IkVFIFBST1QgTklTU0FOIiwiQjMiOiIwMDkuODcwLjQ0IiwiQjQiOiIwMDkuODcwLjQ0IiwiQjUiOiIxODcwMCIsIkI2IjoiTHVpcyBQYWxvbWFyIiwiQjciOiIwMSIsIkI4IjoiRU5ERVNBIEVORVJHSUEgUy5BLlUuIiwiQjkiOiJBODE5NDgwNzciLCJCMTAiOiJDTC4gUklCRVJBIERFTCBMT0lSQSwgNjAiLCJCMTEiOiJNQURSSUQiLCJCMTIiOiIyODA0NiIsIkIxMyI6Ik1BRFJJRCIsIkIxNCI6IlNyYS4gTWlsYWdyb3MgTWFydFx1MDBlZG4gSHVlc2EiLCJCMTUiOiJtaWxhZ3Jvcy5tYXJ0aW5AZW5kZXNhLmVzIiwiQjE3IjoiMi4wMCIsIkIxOCI6IjIuODMiLCJCMTkiOiIyLjUwIiwiQjIwIjoiMC42MCIsIkIyMSI6IjAuNjAiLCJCMjIiOiI1LjAwIiwiQjIzIjoiNS4wMCIsIkIyNSI6IjcwLjAwIiwiQjI2IjoiNTIuMDAiLCJCMjciOiIzNC4wMCIsIkIyOSI6IjEyLjYzIiwiQjMwIjoiNjAuMDAiLCJCMzIiOiI5MzQ0NTQ0MzAiLCJCMzMiOiJsdWlzLnBhbG9tYXJAc3BhcmtpYmVyaWNhLmNvbSIsIkIzNSI6IjA5IiwiQjM2IjoiT01FWE9NIENvbnRyb2wiLCJCMzciOiIwM1wvMTJcLzIwMTUiLCJCMzgiOiIwM1wvMTJcLzIwMTUifQ==";
		byte[] decodedBytes = null;
		String jsonDecoded = null;
		try {
			decodedBytes = Base64.decode(json.getBytes());
			jsonDecoded = new String(decodedBytes, "UTF-8");
			jsonDecoded = StringEscapeUtils.unescapeJava(jsonDecoded);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
		JSONObject jsonObj = (JSONObject) JSONValue.parse(jsonDecoded);
		Set<String> keys = jsonObj.keySet();
		for(String key : keys){
				String value = (String)jsonObj.get(key);
				System.out.println(key + "->" + value);
		}

		
		
		//System.out.println(jsonObj);
	}
}
