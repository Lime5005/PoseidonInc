package com.nnk.springboot.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RedirectTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Foo", authorities = {"USER"})
    public void loginWithRoleUserThenExpectAdminPageForbidden() throws Exception {
        this.mockMvc.perform(get("/user/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Bar", authorities = {"ADMIN"})
    public void loginWithRoleAdminThenExpectAdminContent() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User List")));
    }

    @Test
    public void loginWithRoleUserThenExpectIndexPageRedirect() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
                .user("Foo")
                .password("aaaBBB8*");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("Foo"))
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void loginWithRoleAdminThenExpectAdminPageRedirect() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
                .user("Bar")
                .password("aaaBBB8*");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("Bar"))
                .andExpect(redirectedUrl("/user/list"));
    }

}
