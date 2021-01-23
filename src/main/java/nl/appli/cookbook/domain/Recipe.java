package nl.appli.cookbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "RECIPE")
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

    @Column(name = "SERVING_SIZE")
    private Integer servingSize;

    private String url;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> categories;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL)
    private EstimatedTime estimatedTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private List<Ingredient> ingredients;

    @Column(name = "COOKBOOK_ID")
    private long cookbookId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "COOKBOOK_ID", insertable = false, updatable = false)
    private Cookbook cookbook;

}
