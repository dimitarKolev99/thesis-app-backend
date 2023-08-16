package com.liebherr.hau.localapibackend;

import com.google.gson.Gson;
import com.liebherr.hau.localapibackend.utils.KeyIVGeneratorAES;
import com.liebherr.hau.localapibackend.utils.mocks.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.FileWriter;

@SpringBootApplication
public class LocalapibackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalapibackendApplication.class, args);
	}

}
