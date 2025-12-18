package com.ecommerce.vn.models.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private short id;

    @Column(name = "CategoryName", nullable = false)
    private String categoryName;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(name = "Picture")
    private String picture;

}
