package com.blog;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.entities.Role;
import com.blog.respositories.RoleRepo;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

//	@Autowired
//	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
//	configure 3rd party ModelMapper bean for the conversion
	@Bean // equivalent to <bean id ..../> in xml file
	public ModelMapper mapper() {
		ModelMapper modelMapper = new ModelMapper();		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
	.setPropertyCondition(Conditions.isNotNull());
		return modelMapper;
	}
	
	@Autowired
	RoleRepo repo;
//	configure password encoder bean as depcy for DaoAuthProvider
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	@Override
	public void run(String... args) throws Exception {
//		System.out.println(this.passwordEncoder.encode("AS1999"));
		try {
			Role role = new Role();
			role.setId(501);
			role.setName("ADMIN_USER");
			
			Role role2 = new Role();
			role2.setId(502);
			role2.setName("NORMAL_USER");
			
			List<Role> roles = List.of(role,role2);
			List<Role> result = repo.saveAll(roles);
			result.forEach(r ->{
			System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
