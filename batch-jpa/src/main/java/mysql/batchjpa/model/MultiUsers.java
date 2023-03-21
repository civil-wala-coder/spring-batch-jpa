package mysql.batchjpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultiUsers {

    @Id
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
}
