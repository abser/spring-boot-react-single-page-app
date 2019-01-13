package com.practice.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

@RequestMapping({ "/help","/upload","/import" })
   public String index() {
       return "forward:/index.html";
   }
}