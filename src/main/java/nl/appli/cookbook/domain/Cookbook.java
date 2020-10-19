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

    @Column(name = "CHEF_ID")
    private long chefId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CHEF_ID", insertable = false, updatable = false)
    private Chef chef;

}
