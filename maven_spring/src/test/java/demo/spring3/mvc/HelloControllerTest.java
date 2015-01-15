package demo.spring3.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/resources/applicationContext.xml",
		"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
public class HelloControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void getHello() throws Exception {
		this.mockMvc.perform(get("/hello").accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("hello"));
	}
	
	@Test
	public void getHellosome() throws Exception {
		this.mockMvc.perform(get("/hellosomeone").param("someone", "jack")
				.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("\"hello,jack\""));
	}

}