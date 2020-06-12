package com.razi.ubbtt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SubmitSuggestionTest {
    @Autowired
    private MockMvc mockMvc;

    @WithMockUser("admin")
    @Test
    public void testReport() throws Exception {
         mockMvc.perform(get("/report"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Submit a suggestion/complaint</title>")))
                .andExpect(content().string(containsString("If you are reporting a bug, please give a clear description of the problem you are experiencing and how to reproduce it")));
    }
}
