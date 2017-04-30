package edu.lyon1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RootControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void rootShouldContainTitleAndBody() throws Exception {
    String headerName = "headerName";
    String acceptHtml = "text/html";
    String headerValue = "headerValue";
    MvcResult mvcResult = this.mockMvc.perform(
        get("/")
            .accept(acceptHtml)
            .header(headerName, headerValue)
    )
        .andExpect(status().isOk())
        .andReturn();
    String html = mvcResult.getResponse().getContentAsString();
    assertThat(html).contains("Accept", acceptHtml);
    assertThat(html).contains(headerName, headerValue);
  }

  @Test
  public void rootPostShouldExist() throws Exception {
    this.mockMvc.perform(
        post("/"))
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));
  }

  @Test
  public void userGetShouldHaveLuke() throws Exception {
    String nom = "Skywalker";
    String prenom = "Luke";
    this.mockMvc.perform(
        get("/user")
            .param("nom", nom)
            .param("prenom", prenom)
    )
        .andExpect(status().isOk())
        .andExpect(content().json("{ prenom: \"" + prenom + "\", nom: \"" + nom + "\" }"));
  }

  private void browserShouldBeIdentified(String userAgent, String nom) throws Exception {
    this.mockMvc.perform(
        get("/browser")
            .header("User-Agent", userAgent)
    )
        .andExpect(status().isOk())
        .andExpect(content().json("{ userAgent: \"" + userAgent + "\", nom: \"" + nom + "\" }"));
  }

  @Test
  public void mozillaShouldBeIdentified() throws Exception {
    browserShouldBeIdentified("Firefox/40.1", "Firefox");
  }

  @Test
  public void ieShouldBeIdentified() throws Exception {
    browserShouldBeIdentified("MSIE", "Internet Explorer");
  }

  @Test
  public void safariShouldBeIdentified() throws Exception {
    browserShouldBeIdentified("AppleWebKit/537.75.14", "Safari");
  }

  @Test
  public void chromeShouldBeIdentified() throws Exception {
    browserShouldBeIdentified("Chrome/41.0.2228.0", "Chrome");
  }

}