package org.example.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        // Verify redirection to the products page
        assertTrue(productsPage.isOnProductsPage(), "Login success should display the products page");
        logPass("User standard_user successfully logged in and sees the products page");
    }

    @Test
    public void testLoginLockedOut() {
        loginPage.open();
        loginPage.login("locked_out_user", "secret_sauce");

        // Expected: always stay on the login page and display an error message
        assertTrue(loginPage.isOnLoginPage(), "Locked-out user should not be able to log in");
        String error = loginPage.getErrorMessage();
        assertTrue(error.contains("locked"), "Error message should contain 'locked'");
        logInfo("User locked_out_user is locked out, login failed");
    }
}