package com.example.demo.bootstrap;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) {
        // Skip adding customers if more than 5 exist
        if (customerRepository.count() >= 5) {
            System.out.println("Sample customers already added. Skipping initialization.");
            return;
        }

        // Sample customer data: firstName, lastName, phone, address, postalCode, divisionId
        String[][] customersArray = {
                {"David", "Darkoh", "(109)4567790", "1 Elm ST", "54321", "2"},
                {"Mike", "Jordan", "(435)6399784", "2 Main ST", "98765", "3"},
                {"Crystal", "Clear", "(766)9721372", "3 Dublin ST", "73421", "4"},
                {"John", "Jones", "(872)8588947", "4 Pink ST", "43015", "5"},
                {"James", "Bret", "(345)0133854", "5 Blue ST", "43207", "6"}
        };

        for (String[] customerData : customersArray) {
            Customer customer = new Customer();
            customer.setFirstName(customerData[0]);
            customer.setLastName(customerData[1]);
            customer.setPhone(customerData[2]);
            customer.setAddress(customerData[3]);
            customer.setPostal_code(customerData[4]);

            Long divisionId = Long.valueOf(customerData[5]);
            Optional<Division> divisionOptional = divisionRepository.findById(divisionId);

            if (divisionOptional.isPresent()) {
                customer.setDivision(divisionOptional.get());
                customerRepository.save(customer);
            } else {
                System.err.printf("Division ID %d not found. Skipping customer %s %s.%n",
                        divisionId, customerData[0], customerData[1]);
            }
        }

        System.out.println("Sample customers added successfully.");
    }
}
