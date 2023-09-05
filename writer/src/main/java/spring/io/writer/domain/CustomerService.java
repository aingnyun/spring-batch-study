package spring.io.writer.domain;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public void logCustomer(Customer cus) {
        System.out.println("I just save " + cus);
    }

    public void logCustomerAddress(String address,
                                   String city,
                                   String state,
                                   String zip) {
        System.out.println(
                String.format("I just saved the address:\n%s\n%s, %s\n%s",
                        address,
                        city,
                        state,
                        zip));
    }
}
