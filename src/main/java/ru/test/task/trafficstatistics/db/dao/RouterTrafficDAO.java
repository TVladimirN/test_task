package ru.test.task.trafficstatistics.db.dao;

import ru.test.task.trafficstatistics.common.Month;

import javax.persistence.*;

@Entity
@Table(name = "router_traffic", uniqueConstraints = {@UniqueConstraint(columnNames = {"router_id", "month"})})
public class RouterTrafficDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "router_id", nullable = false)
    private RouterDAO router;
    private Month month;
    private int value;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RouterDAO getRouter() {
        return this.router;
    }

    public void setRouter(RouterDAO router) {
        this.router = router;
    }

    public Month getMonth() {
        return this.month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
