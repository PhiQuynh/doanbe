package com.doan;

import com.doan.service.FileDBServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
@AllArgsConstructor
public class DoanApplication  implements CommandLineRunner {
	@Resource
	FileDBServiceImpl fileDBService;

	public static void main(String[] args) {
		SpringApplication.run(DoanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileDBService.init();

	}
}
