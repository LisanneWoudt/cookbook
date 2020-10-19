package nl.appli.cookbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String amount;

    @Column(name = "RECIPE_ID")
    private long recipeId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RECIPE_ID", insertable = false, updatable = false)
    private Recipe recipe;



}
