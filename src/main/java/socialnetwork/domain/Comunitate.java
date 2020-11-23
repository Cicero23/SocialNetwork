package socialnetwork.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.jar.JarEntry;

public class Comunitate extends Entity<Long>{
    private List<Utilizator> util;

    public Comunitate() {
        util = new ArrayList<Utilizator>();
    }

    public void addUtil (Utilizator id){
        util.add(id);
    }
/*
    public  void addPrietenie(Tuple<Long,Long> id){
        prietenii.add(id);
    }
*/
    public int getSize(){
        return  util.size();
    }

    public List<Utilizator> getUtil() {
        return util;
    }
/*
    public List<Tuple<Long, Long>> getPrietenii() {
        return prietenii;
    }
*/
}
