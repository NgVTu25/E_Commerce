package com.ecommerce.vn.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class to generate BCrypt encoded passwords
 * Run this class to generate encoded password for admin user
 */
public class PasswordEncoderUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Generate password for admin
        String adminPassword = "admin123";
        String encodedAdminPassword = encoder.encode(adminPassword);

        System.out.println("=".repeat(80));
        System.out.println("BCrypt Password Generator");
        System.out.println("=".repeat(80));
        System.out.println();
        System.out.println("Plain password: " + adminPassword);
        System.out.println("Encoded password: " + encodedAdminPassword);
        System.out.println();
        System.out.println("Copy the encoded password above and update it in init-auth.sql");
        System.out.println("=".repeat(80));

        // You can add more passwords here
        String[] testPasswords = { "user123", "manager123" };
        System.out.println("\nAdditional test passwords:");
        for (String password : testPasswords) {
            System.out.println("Plain: " + password + " -> Encoded: " + encoder.encode(password));
        }
    }
}
