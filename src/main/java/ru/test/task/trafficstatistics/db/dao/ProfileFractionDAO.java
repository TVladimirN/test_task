package ru.test.task.trafficstatistics.db.dao;

import ru.test.task.trafficstatistics.common.Month;

import javax.persistence.*;

@Entity
@Table(name = "profile_fraction", uniqueConstraints = {@UniqueConstraint(columnNames = {"profile_id", "month"})})
public class ProfileFractionDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileDAO profile;
    private Month month;
    private double fraction;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProfileDAO getProfile() {
        return this.profile;
    }

    public void setProfile(ProfileDAO profile) {
        this.profile = profile;
    }

    public Month getMonth() {
        return this.month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public double getFraction() {
        return this.fraction;
    }

    public void setFraction(double fraction) {
        this.fraction = fraction;
    }
}
