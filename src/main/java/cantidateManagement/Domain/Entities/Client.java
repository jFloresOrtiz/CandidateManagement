package cantidateManagement.Domain.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @Column(name = "first_name", nullable = false, length = 50) 
    private String name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false) 
    private int age;

    @Column(name = "birth_date") 
    private String birthdate; 

    @Column(name = "creation_date")
    private String creationdate;

}
