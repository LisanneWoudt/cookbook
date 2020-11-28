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

    private Integer calories;

    @Column(name = "INGREDIENT_LIST")
    private String ingredientList;

    private String directions;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> categories;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @Column(name = "COOKBOOK_ID")
    private long cookbookId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "COOKBOOK_ID", insertable = false, updatable = false)
    private Cookbook cookbook;

}
