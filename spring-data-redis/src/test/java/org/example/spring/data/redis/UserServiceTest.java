package org.example.spring.data.redis;

import org.example.spring.data.redis.user.domian.User;
import org.example.spring.data.redis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void addUser() {
        // Create
        User user = new User("1", "John Doe", 30);
        userService.saveUser(user);

        // Read
        User fetchedUser = userService.getUserById("1");
        assertEquals("John Doe", fetchedUser.getName());
        assertEquals(30, fetchedUser.getAge());
    }

    @Test
    public void updateUser() {
        User user = userService.getUserById("1");
        // Update
        user.setAge(31);
        userService.updateUser(user);
        User updatedUser = userService.getUserById("1");
        assertEquals(31, updatedUser.getAge());
    }

    @Test
    public void deleteUser() {

        // Delete
        userService.deleteUserById("1");
        User deletedUser = userService.getUserById("1");
        assertNull(deletedUser);
    }

    @Test
    public void testUserWithExpiration() throws InterruptedException {
        // Create with expiration
        User user = new User("2", "Jane Doe", 25);
        userService.saveUserWithExpiration(user, 2L, TimeUnit.SECONDS);

        // Verify existence
        User fetchedUser = userService.getUserById("2");
        assertNotNull(fetchedUser);
        // Wait for expiration
        Thread.sleep(3000);

        // Verify expiration
        User expiredUser = userService.getUserById("2");
        assertNull(expiredUser);
    }
}