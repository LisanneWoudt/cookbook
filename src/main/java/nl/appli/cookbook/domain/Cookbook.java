package nl.appli.cookbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Cookbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "cookbook")
    private List<Recipe> recipes;

    @JsonIgnore
    @ManyToMany(mappedBy = "cookbooks", fetch = FetchType.LAZY)
    private List<Chef> chefs;

    @Override
    public String toString() {
        return "Cookbook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
