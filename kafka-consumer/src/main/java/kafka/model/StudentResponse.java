package kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private String firstName;

    private String lastName;

    private String email;

    private int studentNo;

    private String schoolName;

    private String imageName;
}
