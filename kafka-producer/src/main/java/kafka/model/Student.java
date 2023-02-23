package kafka.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Student implements Serializable {


    private String firstName;

    private String lastName;

    private String email;

    private int studentNo;

    private School school;

    private String imageName;


}
