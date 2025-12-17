package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "divisions")
@Getter
@Setter
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @Column(name = "division")
    private String division_name;

    @CreationTimestamp
    @Column(name = "create_date")
    private Date create_date;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Date last_update;

    // This is the actual foreign key field in your DB
    @Column(name = "Country_ID", nullable = false)
    private Long country_id;

    // This maps the foreign key relationship — not insertable/updatable directly
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Country_ID", insertable = false, updatable = false)
    private Country country;

   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "division")
    //private Set<Customer> customers;

    // Getters and Setters

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getDivision_name() {
//        return division_name;
//    }
//
//    public void setDivision_name(String division_name) {
//        this.division_name = division_name;
//    }
//
//    public Date getCreate_date() {
//        return create_date;
//    }
//
//    public void setCreate_date(Date create_date) {
//        this.create_date = create_date;
//    }
//
//    public Date getLast_update() {
//        return last_update;
//    }
//
//    public void setLast_update(Date last_update) {
//        this.last_update = last_update;
//    }
//
//    public Long getCountry_id() {
//        return country_id;
//    }
//
//    public void setCountry_id(Long country_id) {
//        this.country_id = country_id;
//    }
//
//    public Country getCountry() {
//        return country;
//    }

    // Optional setter if needed for convenience
    public void setCountry(Country country) {
        this.country = country;
        if (country != null) {
            this.country_id = country.getId(); // sync IDs
        }
    }

//    public Set<Customer> getCustomers() {
//        return customers;
//    }
//
//    public void setCustomers(Set<Customer> customers) {
//        this.customers = customers;
//    }
}