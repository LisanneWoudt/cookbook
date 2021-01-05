package nl.appli.cookbook.domain;

import lombok.Data;
import nl.appli.cookbook.domain.enums.Status;

import javax.persistence.*;

@Data
@Entity(name = "JOIN_COOKBOOK_REQUEST")
public class JoinCookbookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COOKBOOK_ID")
    private long cookbookId;

    @Transient
    private String cookbookName;

    @Column(name = "CHEF_ID")
    private long chefId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;
}
