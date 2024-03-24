package com.eniac.projects.bet.apis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; 

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eniac.projects.bet.config.AppConfig;
import com.eniac.projects.bet.config.TilesConfig;
import com.eniac.projects.bet.config.WebSecurityConfig;
import com.eniac.projects.bet.model.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.eniac.projects.bet.model.BaseBean.Mode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TilesConfig.class, WebSecurityConfig.class})
@WebAppConfiguration
public class UserRestControllerTest {

	@Autowired
	private WebApplicationContext applicationContext;
	
	@Autowired
	MockHttpSession session;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testGetDatatable() throws Exception {
		this.mockMvc.perform(get("/user/api/datatable").session(session)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserADD_InvalidValues() throws Exception {
		
		UserBean user = new UserBean();
		user.setName("User");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.ADD);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserADD_UniqueUsername() throws Exception {
		
		UserBean user = new UserBean();
		user.setName("User");
		user.setUsername("user");
		user.setPassword("P@ssw0rd");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.ADD);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isInternalServerError());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserADD_ValidValues() throws Exception {
		
		UserBean user = new UserBean();
		user.setName("User");
		user.setUsername("user789");
		user.setPassword("P@ssw0rd");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.ADD);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserEDIT_InvalidValues() throws Exception {
		
		UserBean user = new UserBean();
		user.setName("User");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.EDIT);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserEDIT_UniqueUsername() throws Exception {
		
		UserBean user = new UserBean();
		user.setId(2);
		user.setName("User");
		user.setUsername("super-user");
		user.setPassword("P@ssw0rd");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.EDIT);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isInternalServerError());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserEDIT_ValidValues() throws Exception {
		
		UserBean user = new UserBean();
		user.setId(2);
		user.setName("User");
		user.setUsername("user779");
		user.setPassword("P@ssw0rd");
		user.setRole("ROLE_ADMIN");
		user.setMode(Mode.EDIT);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserPASSWORD_InvalidValues() throws Exception {
		
		UserBean user = new UserBean();
		user.setId(1);
		user.setMode(Mode.PASSWORD);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserPASSWORD_WrongPassword() throws Exception {
		
		UserBean user = new UserBean();
		user.setId(1);
		user.setPassword("Wrong Password");
		user.setNewPassword("P@ssw0rd");
		user.setMode(Mode.PASSWORD);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isInternalServerError());
	}
	
	@Test
	@WithMockUser(roles= "ADMIN", password="P@ssw0rd")
	public void testPostUserPASSWORD_ValidValues() throws Exception {
		
		UserBean user = new UserBean();
		user.setId(1);
		user.setPassword("P@ssw0rd");
		user.setNewPassword("P@ssw0rd");
		user.setMode(Mode.PASSWORD);
		
		ObjectMapper mapper = new ObjectMapper();
		    
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(user);

		mockMvc.perform(post("/user/api/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
		        .andExpect(status().isOk());
	}
	
}
