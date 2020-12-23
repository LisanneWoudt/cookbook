package nl.appli.cookbook.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    @Column(name = "LAST_SELECTED_COOKBOOK_ID")
    private Long lastSelectedCookbookId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "CHEF_COOKBOOK",
            joinColumns = { @JoinColumn(name = "CHEF_ID") },
            inverseJoinColumns = { @JoinColumn(name = "COOKBOOK_ID") }
    )
    private List<Cookbook> cookbooks;

}
