package com.aj.diningreview;

import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;


//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = UserRepository.class))
public class UserRepositoryTests {

    @Autowired
    UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSearchUsers() {
        String keyword = "john";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAllSearch(keyword, pageable);

        List<User> userList = page.getContent();

        userList.forEach(System.out::println);

        assertEquals(userList.size(), greaterThan(0));
    }

    @Test
    public void testGetUserByEmail() {
        String email = "abc@def.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }
}
