package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserService() {
        User newUser = new User(999, "newUser", "aaaBBB8*", "newUser", "USER");

        // Save
        userService.save(newUser);
        // Find
        User foundUser = userService.findByUsername("newUser");
        assertNotNull(foundUser);

        // Update
        Integer id = foundUser.getId();
        foundUser.setUsername("foundUser");
        foundUser.setFullname("foundUser");
        foundUser.setPassword("aaaBBB8*");
        foundUser.setRole("ADMIN");
        userService.updateUser(id, foundUser);
        assertEquals("foundUser", foundUser.getUsername());
        assertEquals("ADMIN", foundUser.getRole());

        // Find
        User byId = userService.findById(id);
        assertNotNull(byId);

        // Delete
        userService.delete(foundUser);
        User deletedUser = userService.findByUsername("foundUser");
        assertNull(deletedUser);

    }

}
