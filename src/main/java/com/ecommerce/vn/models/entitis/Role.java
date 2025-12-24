package com.ecommerce.vn.models.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name; // ROLE_ADMIN, ROLE_USER, ROLE_MANAGER, etc.

    @Column(name = "description", length = 255)
    private String description;
}
