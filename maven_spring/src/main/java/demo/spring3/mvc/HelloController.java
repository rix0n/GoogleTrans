package demo.spring3.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String hello(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
		
		modelMap.addAttribute("message", "Hello World!");
		
		return "hello";
	}
	
	@RequestMapping(value="/hellosomeone", method=RequestMethod.GET)
	public @ResponseBody String hellosomeone(@RequestParam String someone){
		return "hello," + someone;
	}
	

}
