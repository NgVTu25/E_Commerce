package com.ecommerce.vn.models.entitis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employees {
    @Id
    @Column(name = "EmployeeID")
    private Integer id;

    @Column(name = "LastName", length = 20)
    private String lastName;

    @Column(name = "FirstName", length = 10)
    private String firstName;

    @Column(name = "Title", length = 30)
    private String title;

    @Column(name = "TitleOfCourtesy", length = 25)
    private String titleOfCourtesy;

    @Column(name = "BirthDate")
    private LocalDateTime birthDate;

    @Column(name = "HireDate")
    private LocalDateTime hireDate;

    @Column(name = "Address", length = 60)
    private String address;

    @Column(name = "City", length = 15)
    private String city;

    @Column(name = "Region", length = 15)
    private String region;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Country", length = 15)
    private String country;

    @Column(name = "HomePhone", length = 24)
    private String homePhone;

    @Column(name = "Extension", length = 4)
    private String extension;

    @JsonIgnore
    @Lob
    @Column(name = "Photo")
    private byte[] photo;

    @Column(name = "Notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "ReportsTo")
    private Integer reportsTo;

    @Column(name = "PhotoPath")
    private String photoPath;

    @Column(name = "Salary")
    private String salary;
}
