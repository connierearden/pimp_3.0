package demo.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pi")
public class Pimp {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "pimp", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<EscortGirl> escortGirlList;

    @Column(name = "cash")
    private double money;

    public Pimp() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pimp(String name, List<EscortGirl> escortGirlList) {
        this.name = name;
        this.escortGirlList = escortGirlList;
    }

    public Pimp(String name) {
        this.name = name;
    }

    public Pimp(List<EscortGirl> escortGirlList) {
        this.escortGirlList = escortGirlList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EscortGirl> getEscortGirlList() {
        return escortGirlList;
    }

    public void setEscortGirlList(List<EscortGirl> escortGirlList) {
        this.escortGirlList = escortGirlList;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pimp)) return false;

        Pimp pimp = (Pimp) o;

        if (getName() != null ? !getName().equals(pimp.getName()) : pimp.getName() != null) return false;
        return getEscortGirlList() != null ? getEscortGirlList().equals(pimp.getEscortGirlList()) : pimp.getEscortGirlList() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getEscortGirlList() != null ? getEscortGirlList().hashCode() : 0);
        return result;
    }
}
