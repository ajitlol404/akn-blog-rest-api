package com.akn.blog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.akn.blog.entity.Role;
import com.akn.blog.entity.User;
import com.akn.blog.repository.RoleRepository;
import com.akn.blog.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class AknBlogRestApiApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	void testCreateUser() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "12345678";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		User user = new User();
		user.setName("akn");
		user.setEmail("akn@mail.com");
		user.setUsername("akn");
		user.setPassword(encodedPassword);

		User savedUser = userRepository.save(user);

		assertThat(savedUser).isNotNull();

	}

	@Test
	void testGetUserByUsernameOrEmail() {
		Optional<User> findByUsernameOrEmail = userRepository.findByUsernameOrEmail("akn", "akn");
		System.out.println(findByUsernameOrEmail);
		assertThat(findByUsernameOrEmail).isNotNull();
	}

	@Test
	void testCreateRoles() {
		Role admin = new Role("ROLE_ADMIN");
		Role editor = new Role("ROLE_EDITOR");
		Role customer = new Role("ROLE_CUSTOMER");

		List<Role> savedRoles = roleRepository.saveAll(List.of(admin, editor, customer));

		assertThat(savedRoles).hasSizeGreaterThan(3);
	}

	@Test
	void testAssignRolesToUser() {
		Long userId = 1L;
		User user = userRepository.findById(userId).get();
		user.addRole(new Role(1L, "ROLE_ADMIN"));
		User savedUser = userRepository.save(user);

		assertThat(savedUser).isNotNull();

//		Long userId = 2L;
//		User user = userRepository.findById(userId).get();
//		user.addRole(new Role(2L,"ROLE_EDITOR"));
//		user.addRole(new Role(3L,"ROLE_CUSTOMER"));

	}

//	@Test
//	void testCountRoleandCountUser() {
//		long userCount = userRepository.count();
//		long roleCount = roleRepository.count();
//		System.out.println("Total User: - " + userCount + " , " + "Total Role: - " + roleCount);
//	}

}
