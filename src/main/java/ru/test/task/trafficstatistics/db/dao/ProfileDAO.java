package ru.test.task.trafficstatistics.db.dao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
public class ProfileDAO {

    @Id
    @Column(unique = true)
    private long id;
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<ProfileFractionDAO> fraction = new ArrayList<>();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProfileFractionDAO> getFraction() {
        return this.fraction;
    }

    public void setFraction(List<ProfileFractionDAO> fraction) {
        fraction.forEach(f -> {
            f.setProfile(this);
            this.fraction.add(f);
        });
    }
}
