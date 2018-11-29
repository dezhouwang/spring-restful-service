package com.simple.spring.simpleservices;

import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {
	
	Services services = new Services();
	
	// Output JSONObject of users' salary based on query parameters
    @RequestMapping(value="/users")
    public JSONObject greeting(
    		@RequestParam(name = "min", required = false, defaultValue = "0") String min,
            @RequestParam(name = "max", required = false, defaultValue = "4000") String max, 
            Model model) 
    {
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        
        double minimum = 0;//default min salary
        double maximum = 4000;//default max salary
        
        
        try {
        	minimum = Double.parseDouble(min);
        } catch (NumberFormatException e) {
        	//min input is invalid, fallback to 0
        }
        
        try {
        	maximum = Double.parseDouble(max);
        } catch (NumberFormatException e) {
        	//max input is invalid, fallback to 4000
        }
        
        //return as a JSONObject
        return services.getUsers(minimum,maximum);
    }

}