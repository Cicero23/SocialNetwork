package socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class Utilizator extends Entity<Long>{
    private String firstName;
    private String lastName;
    //private Long codCom;
    private List<Utilizator> friends = new ArrayList<Utilizator>();

    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Utilizator> getFriends() {
        return friends;
    }

    public void  addFriend(Utilizator u){
        friends.add(u);
    }

    public void removeFriend(Utilizator u){
        friends.remove(u);
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + " ->friends: " + friends.stream().map(x -> {
            return x.getFirstName() +" " + x.getLastName();
        }).reduce("", (a,b)-> a+", "+b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }

}