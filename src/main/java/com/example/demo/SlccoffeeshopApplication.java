package com.example.demo;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlccoffeeshopApplication implements CommandLineRunner   {
	
	@Autowired
	ProductRepository productRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(SlccoffeeshopApplication.class, args);
	}
	
	 @Override
	    public void run(String... strings) throws Exception {
		    if(CollectionUtils.isEmpty(productRepository.findAll())){
			    	Product mocha = new Product();
			        mocha.setProductName("Mocha");
			        mocha.setProductPrice(3.95);
			
			        Product capuccinno = new Product();
			        capuccinno.setProductName("Capuccinno");
			        capuccinno.setProductPrice(4.95);
			
			        productRepository.save(mocha);
			        productRepository.save(capuccinno);
		    }
	    }
}
