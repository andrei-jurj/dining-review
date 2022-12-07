package com.aj.diningreview;

import com.aj.diningreview.model.User;
import com.aj.diningreview.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataLoader {
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void loadData() {
        userRepository.saveAll(List.of(
            new User(1L, "john123", "Barcelona", "Catalonia", "08001", false, false, true),
            new User(2L, "george2022", "Cluj-Napoca", "Cluj", "278114", false, true, false),
            new User(3L, "aj081", "Alba Iulia", "Alba", "89214A", false, false, false),
            new User(4L, "claud09", "Sibiu", "Sibiu", "98122", false, false, true),
            new User(5L, "ionm99199", "Sebes", "Alba", "78221K", true, false, true)
        ));
    }
}