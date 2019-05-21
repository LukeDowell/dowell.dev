package dev.dowell.website.markdown;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarkdownControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MarkdownRepository markdownRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postMarkdownExistsInDb() throws Exception {
        final UUID someUuid = UUID.randomUUID();
        final String markdown = String.format("This is valid markdown\n=======\n\n*%s*", someUuid);

        mockMvc.perform(post("/markdown").content(markdown)
                .with(csrf()))
                .andExpect(status().isOk());

        final List<MarkdownPost> markdownPosts = StreamSupport.stream(markdownRepository.findAll().spliterator(), false).collect(Collectors.toList());
        final boolean containsExpectedMarkdown = markdownPosts.stream().anyMatch(p -> p.getContent().contains(someUuid.toString()));

        Assert.assertTrue(containsExpectedMarkdown);
    }

    @Test
    public void getMarkdownSuccess() throws Exception {
        var uuidOne = UUID.randomUUID().toString();
        var uuidTwo = UUID.randomUUID().toString();
        var markdownOne = MarkdownPost.builder()
                .content(String.format("Markdown One\n=====\n%s", uuidOne))
                .localDateTime(LocalDateTime.now())
                .build();
        var markdownTwo = MarkdownPost.builder()
                .content(String.format("Markdown Two\n=====\n%s", uuidTwo))
                .localDateTime(LocalDateTime.now())
                .build();

        markdownRepository.saveAll(Arrays.asList(markdownOne, markdownTwo));

        var markdownPostsString = mockMvc.perform(get("/markdown")).andReturn().getResponse().getContentAsString();
        List<MarkdownPost> markdownPosts = objectMapper.readValue(markdownPostsString, new TypeReference<List<MarkdownPost>>(){});

        var containsBothPosts = markdownPosts.stream()
                .filter(p -> p.getContent().contains(uuidOne) || p.getContent().contains(uuidTwo)).count() == 2;

        Assert.assertTrue(containsBothPosts);
    }
}