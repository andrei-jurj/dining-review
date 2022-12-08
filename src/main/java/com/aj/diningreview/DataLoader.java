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
        long totalUsers = userRepository.count() + 1;

        if (totalUsers == 0) {
            userRepository.saveAll(List.of(
                new User(totalUsers++, "john123", "Barcelona", "Catalonia", "08001", false, false, true),
                new User(totalUsers++, "george2022", "Cluj-Napoca", "Cluj", "278114", false, true, false),
                new User(totalUsers++, "aj081", "Alba Iulia", "Alba", "89214A", false, false, false),
                new User(totalUsers++, "claud09", "Sibiu", "Sibiu", "98122", false, false, true),
                new User(totalUsers++, "ionm99199", "Sebes", "Alba", "78221K", true, false, true),
                new User(totalUsers++, "jane2000", "Paris", "Seine", "892kj", true, false, true),
                new User(totalUsers++, "iund", "Cluj-Napoca", "Cluj", "278114", true, true, false),
                new User(totalUsers++, "ikmjohn9", "Alba Iulia", "Alba", "89214A", true, false, false),
                new User(totalUsers++, "fox2022", "Sibiu", "Sibiu", "98122", true, false, true),
                new User(totalUsers++, "fran099", "München", "Bavaria", "K212994", false, false, false),
                new User(totalUsers++, "klos0", "Barcelona", "Catalonia", "08001", false, false, false),
                new User(totalUsers++, "alin", "Cluj-Napoca", "Cluj", "278114", false, false, false),
                new User(totalUsers++, "florin07", "Alba Iulia", "Alba", "89214A", true, false, false),
                new User(totalUsers++, "aurelk021", "Sibiu", "Sibiu", "98122", true, false, true),
                new User(totalUsers++, "hm2022", "Sebes", "Alba", "78221K", false, false, false),
                new User(totalUsers++, "jdolar91", "Barcelona", "Catalonia", "08001", false, false, false),
                new User(totalUsers++, "epik000", "Cluj-Napoca", "Cluj", "278114", true, true, false),
                new User(totalUsers++, "iu1000", "Alba Iulia", "Alba", "89214A", false, false, false),
                new User(totalUsers++, "kun92", "Sibiu", "Sibiu", "98122", true, false, true),
                new User(totalUsers++, "ionut", "Sebes", "Alba", "78221K", false, false, false)
            ));
        }
    }
}