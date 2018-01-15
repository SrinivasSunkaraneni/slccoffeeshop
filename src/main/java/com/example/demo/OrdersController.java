package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class OrdersController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String productsList(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }
    
    
    @RequestMapping(value = "/ordersplaced", method = RequestMethod.GET)
    public String productsList1(Model model){
        model.addAttribute("orders", orderRepository.findAll());
        return "orders2process";
    }

    @RequestMapping(value="/createorder", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrder(@RequestParam String firstName, @RequestParam String lastName, @RequestParam(value="productIds[]") Long[] productIds){
    		//Saving customer
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customerRepository.save(customer);
        
        //saving customer order
        //products(inmput productIds[] --- > Set<Product>), total, customer
        CustomerOrder customerOrder = new CustomerOrder();
        //set Customer
        customerOrder.setCustomer(customerRepository.findOne(customer.getCustomerId()));
        
        //set products
        Set<Product> productSet = new HashSet();
        for (Long productId:productIds){
            productSet.add(productRepository.findOne(productId));
        }
        customerOrder.setProducts(productSet);
        
        //total
        Double total = 0.0;
        for (Long productId:productIds){
            total = total + (productRepository.findOne(productId).getProductPrice());
        }
        customerOrder.setTotal(total);
        
        
        //saving
        orderRepository.save(customerOrder);

        return customerOrder.getOrderId().toString();
    }

    @RequestMapping(value = "/removeorder", method = RequestMethod.POST)
    @ResponseBody
    public String removeOrder(@RequestParam Long Id){
        orderRepository.delete(Id);
        return Id.toString();
    }
}