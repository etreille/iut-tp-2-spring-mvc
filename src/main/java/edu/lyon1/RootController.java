package edu.lyon1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/")
public class RootController {

  @RequestMapping(value="/" , method= RequestMethod.GET)
  public ModelAndView get(@RequestHeader HttpHeaders headers, HttpServletResponse response) {
    ModelAndView mav = new ModelAndView();

    List<HttpHeader> headersList = new ArrayList<HttpHeader>();
    //= new ArrayList<String> (headers.keySet());
    for (Map.Entry<String, List<String>> entry : headers.entrySet()){
      headersList.add(new HttpHeader(entry.getKey(),entry.getValue().toString()));
    }
    mav.addObject("headers",headersList);
    mav.addObject("titre", "IUT");
    if (response.getStatus() == HttpStatus.OK.value()){
      mav.addObject("corps", "Accept");
    }else{
      mav.addObject("corps", "Fail");
    }


    mav.setViewName("template");
    return mav;
  }

  @RequestMapping(value="/" , method= RequestMethod.POST)
  @ResponseBody
  public String post() {


    return "OK";
  }

  @RequestMapping(value="/user" , method= RequestMethod.GET)
  @ResponseBody
  public User userGet(@RequestHeader HttpHeaders headers, @RequestParam("nom") String nom, @RequestParam("prenom") String prenom) {

    return new User(nom, prenom);
  }

  private class HttpHeader {
    private final String name;
    private final String value;

    public HttpHeader(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }
  }

  private class User {
    private final String nom;
    private final String prenom;

    public User(String nom, String prenom) {
      this.nom = nom;
      this.prenom = prenom;
    }

    public String getNom() {
      return nom;
    }

    public String getPrenom() {
      return prenom;
    }
  }

}
