package nl.appli.cookbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "ESTIMATED_TIME")
public class EstimatedTime {

    @Id
    private Long id;

    private Integer hours;

    private Integer minutes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    @MapsId
    @JsonIgnore
    private Recipe recipe;

}
