package demo.model;

import javax.persistence.*;

@Entity
@Table(name = "es")
public class EscortGirl {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean isRent;
    private int price = 5000;
    private int age;
    private int weight;
    private int growth;

    @Column(name = "tits")
    private int breastSize;

    @ManyToOne
    @JoinColumn(name = "pimp_id")
    private Pimp pimp;

    private double wallet;

    public EscortGirl() {

    }

    public EscortGirl(String name, int age, int weight, int growth, int breastSize) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.growth = growth;
        this.breastSize = breastSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EscortGirl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRent() {
        return isRent;
    }

    public void setRent(boolean rent) {
        isRent = rent;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Pimp getPimp() {
        return pimp;
    }

    public void setPimp(Pimp pimp) {
        this.pimp = pimp;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getBreastSize() {
        return breastSize;
    }

    public void setBreastSize(int breastSize) {
        this.breastSize = breastSize;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return name + " " + isRent + " " + age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EscortGirl)) return false;

        EscortGirl that = (EscortGirl) o;

        if (getAge() != that.getAge()) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getAge();
        return result;
    }
}
