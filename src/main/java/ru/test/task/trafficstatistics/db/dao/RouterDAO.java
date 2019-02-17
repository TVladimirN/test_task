package ru.test.task.trafficstatistics.db.dao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "router")
public class RouterDAO {

    @Id
    @Column(unique = true)
    private long id;
    @OneToOne(optional = false)
    private ProfileDAO profile;
    @OneToMany(mappedBy = "router", cascade = CascadeType.ALL)
    private List<RouterTrafficDAO> traffic = new ArrayList<>();


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<RouterTrafficDAO> getTraffic() {
        return this.traffic;
    }

    public void setTraffic(List<RouterTrafficDAO> traffic) {
        traffic.forEach(t -> {
            t.setRouter(this);
            this.traffic.add(t);
        });
    }

    public ProfileDAO getProfile() {
        return this.profile;
    }

    public void setProfile(ProfileDAO profile) {
        this.profile = profile;
    }
}
