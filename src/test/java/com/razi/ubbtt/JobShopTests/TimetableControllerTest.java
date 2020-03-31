package com.razi.ubbtt.JobShopTests;

import com.razi.ubbtt.configuration.WebMvcConfig;
import com.razi.ubbtt.controllers.LoginController;
import com.razi.ubbtt.controllers.TimetableController;
import com.razi.ubbtt.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TimetableControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TimetableController timetableController;

    @WithMockUser("admin")
    @Test
    public void tst() throws Exception {
        mockMvc.perform(get("/generate_timetable"))
                .andExpect(status().isOk());
    }
}
