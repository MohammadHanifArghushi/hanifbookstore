package fi.haagahelia.hanifbookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
public class BookRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void statusOkAndJsonContentTypeTest() throws Exception {

        this.mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void checkAmountOfBooks() throws Exception {

        this.mockMvc.perform(get("/api/books"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void getSingleBookTest() throws Exception {

        this.mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("A Farewell to Arms"));
    }

    @Test
    public void accessDeniedTest() throws Exception {

        this.mockMvc.perform(get("/api/books"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "http://localhost/login"));
    }
}