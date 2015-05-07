package cn.walle.demo.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.walle.system.service.WlUserManager;

@Controller
public class DemoController {
	
	@Autowired
	private WlUserManager userManager;

	@RequestMapping("test")
	public ModelAndView test() {
		System.out.println("controller test");
		ModelAndView mv = new ModelAndView("demo/mvc/test");
		mv.addObject("xxName", "xxxyyyzzz");
		mv.addObject("users", this.userManager.getAll(null, null));
		return mv;
	}
	
}
