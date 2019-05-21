package dev.dowell.website;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private final String markdown = "#Some Markdown Title\n*emphasis woop woop*";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postMarkdownAuthenticatedAllowed() throws Exception {
        mockMvc.perform(post("/markdown")
                .content(markdown)
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void postMarkdownAuthenticatedWithoutCsrfNotAllowed() throws Exception {
        mockMvc.perform(post("/markdown")
                .content(markdown))
                .andExpect(status().isForbidden());
    }

    @Test
    public void anonymousAccessToPostMarkdownNotAllowed() throws Exception {
        mockMvc.perform(post("/markdown")
                .content(markdown))
                .andExpect(status().isForbidden());
    }

    @Test
    public void anonymousAccessToGetMarkdownAllowed() throws Exception {
        mockMvc.perform(get("/markdown"))
                .andExpect(status().isOk());
    }

    @Test
    public void anonymousAccessToIndexAllowed() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}