package lesson3.model;


import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private int age;
    private String name;
    private String surname;

    @Singular
    private Map<String,Integer> priorities;

    public static void main(String[] args) {
        User user = User.builder()
                .age(12).id(12).name("Vasya").priority("drama", 70).priority("action", 90)
                .build();
    }
}
