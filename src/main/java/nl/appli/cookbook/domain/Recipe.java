package nl.appli.cookbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    private Integer calories;

    @Column(name = "COOKBOOK_ID")
    private long cookbookId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "COOKBOOK_ID", insertable = false, updatable = false)
    private Cookbook cookbook;

}
