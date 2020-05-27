package com.hhh.fund.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	private HTMLFilter htmlFilter;

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		htmlFilter = new HTMLFilter();
	} 
  
    @Override  
    public String getQueryString() {
    	String str = "";
		if(super.getQueryString() != null){
			str = htmlFilter.filter(super.getQueryString());
		}
        return str;
    }  
	
    @Override  
    public String getParameter(String name) {
    	String str = "";
		if(super.getParameter(name) != null){
			str = htmlFilter.filter(super.getParameter(name));
		}
        return str;
    }  
  
    @Override  
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);  
        if(values != null) {  
            int length = values.length;  
            String[] escapseValues = new String[length];  
            for(int i = 0; i < length; i++){  
                escapseValues[i] = htmlFilter.filter(values[i]);  
            }  
            return escapseValues;  
        }  
        return super.getParameterValues(name);  
    }  
}
