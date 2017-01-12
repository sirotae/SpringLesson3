package lesson3;

import lesson3.dao.UserDaoWithTransactionTemplate;
import lesson3.model.User;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@Configuration
public class MockConfig {

    @Bean
    @Primary
    public UserDaoWithTransactionTemplate watcherDaoMock() {
        List<User> users = new ArrayList<>();
        UserDaoWithTransactionTemplate userDao = Mockito.mock(UserDaoWithTransactionTemplate.class);

        users.add(User.builder().id(1).name("Name1").surname("Surname1").build());
        users.add(User.builder().id(2).name("Name2").surname("Surname2").build());
        when(userDao.findAll()).thenReturn(users);
        when(userDao.findUserById(anyInt())).then(
                i->User.builder().id(i.getArgumentAt(0, Integer.class)).name("Name1").surname("Surname1").build());
        when(userDao.count()).thenReturn(2L);
        return userDao;
    }


}
