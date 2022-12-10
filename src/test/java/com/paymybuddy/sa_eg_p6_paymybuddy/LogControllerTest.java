package com.paymybuddy.sa_eg_p6_paymybuddy;

import com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity.Log;
import com.paymybuddy.sa_eg_p6_paymybuddy.service.LogService;
import com.paymybuddy.sa_eg_p6_paymybuddy.web.dto.NewUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.InputMismatchException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
public class LogControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    @Autowired
    private WebApplicationContext context;

    @Test
    public void createUserTest() throws Exception {
        mockMvc.perform(get("/createUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("saveUser"));
    }


}
