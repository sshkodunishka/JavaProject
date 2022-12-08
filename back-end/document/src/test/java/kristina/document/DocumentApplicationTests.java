package kristina.document;

import kristina.document.Repository.DocumentRepository;
import kristina.document.models.Document;
import kristina.document.models.Role;
import kristina.document.security.jwt.JwtTokenProvider;
import org.hamcrest.Matchers;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DocumentApplicationTests {
	@MockBean
	private DocumentRepository documentRepository;
	@Test
	void contextLoads() {
	}
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	private MockMvc mockMvc;
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	@Test
	@WithMockUser(username = "user")
	public void getAllDocuments() throws Exception {
		setUp();
		List<Document> documents = Arrays.asList(
				new Document("xc","zxc"),
				new Document("asd","asd1")
		);

		when(documentRepository.findAll()).thenReturn(documents);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/documents"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[*].description", Matchers.containsInAnyOrder("zxc", "asd1")))
				.andExpect(jsonPath("$[*].title", Matchers.containsInAnyOrder("xc", "asd")));
	}

	@Test
	void testSetJwtTokenProviderUser() throws Exception {
		setUp();
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setName("ROLE_USER");
		roles.add(role);
		String token = jwtTokenProvider.createToken("user", roles);
		Authentication authentication = jwtTokenProvider.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/documents").header("Authorization", "Bearer_" + token))
				.andExpect(status().isOk());
	}

	@Test
	void testNotFoundPagesComments() throws Exception {
		setUp();
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		role.setName("ROLE_USER");
		roles.add(role);
		String token = jwtTokenProvider.createToken("user", roles);
		Authentication authentication = jwtTokenProvider.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/documents/1/comments").header("Authorization", "Bearer_" + token))
				.andExpect(status().is4xxClientError());
	}

}
