package kafka.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentResponse {

    private String firstName;

    private String lastName;

    private String email;

    private int studentNo;

    private String schoolName;

    private String imageName;
}
