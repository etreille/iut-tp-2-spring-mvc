package edu.lyon1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/")
public class RootController {

  @RequestMapping("/")
  public ModelAndView test(@RequestHeader HttpHeaders headers, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView();

    List<String> headersList= new ArrayList<String> (headers.keySet());
    mav.addObject("headers",headers);
    mav.addObject("titre", "IUT");
    if (response.getStatus() == HttpStatus.OK.value()){
      mav.addObject("corps", "Accept");
    }else{
      mav.addObject("corps", "Fail");
    }


    mav.setViewName("template");
    return mav;
  }

}
