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

    @OneToMany(mappedBy = "cookbook", cascade = CascadeType.REMOVE)
    private List<Recipe> recipes;

    @Column(name = "CREATOR_ID")
    private Long creatorId;

    @JsonIgnore
    @ManyToMany(mappedBy = "cookbooks", fetch = FetchType.LAZY)
    private List<Chef> chefs;

    public void removeCookbook(List<Chef> chefs) {
        chefs.forEach(chef -> chef.getCookbooks().remove(this));
    }

    @Override
    public String toString() {
        return "Cookbook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
