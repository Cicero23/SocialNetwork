package socialnetwork.service;

import socialnetwork.domain.*;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.AccountDBrepo;
import socialnetwork.repository.factory.Factory;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorService extends Observable{
    private Repository<Long, Utilizator> repoUtilizatori;
    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenii;
    private Repository<Long, Message> repoMessage;
    private Repository<Long, Invitatie> repoInvitatie;
    private AccountDBrepo repoAccount;
    Factory<Long,Utilizator> utilizatorFactory;
    Factory<Tuple<Long,Long>,Prietenie> prietenieFactory;
    private static int l= -1;
    public UtilizatorService(Repository<Long, Utilizator> repoUtilizatori, Repository<Tuple<Long, Long>, Prietenie> repoPrietenii, Factory<Long,Utilizator> utilizatorFactory, Factory<Tuple<Long,Long>,Prietenie> prietenieFactory, Repository<Long, Message> repoMessage, Repository<Long, Invitatie> repoInvitatie,AccountDBrepo repoAccount) {
        super();
        this.repoUtilizatori = repoUtilizatori;
        this.repoPrietenii = repoPrietenii;
        this.utilizatorFactory = utilizatorFactory;
        this.prietenieFactory = prietenieFactory;
        this.repoMessage = repoMessage;
        this.repoInvitatie = repoInvitatie;
        this.repoAccount = repoAccount;
        uniteRepo();
    }

    private void uniteRepo(){
        loadPrieteniIntoUtil();

    }

    private void loadPrieteniIntoUtil(){
        repoPrietenii.findAll().forEach(x ->{
            Utilizator u1 = repoUtilizatori.findOne(x.getId().getLeft());
            Utilizator u2 = repoUtilizatori.findOne(x.getId().getRight());
            if(u1 !=null && u2 !=null){
                u1.addFriend(u2);
                u2.addFriend(u1);
            }

        });
    }



    public Utilizator addUtilizator(String s) {
        Utilizator utilizator = utilizatorFactory.extractEntity(s);
        Utilizator task = repoUtilizatori.save(utilizator);
        if(task == null){
            notifyObservers();
        }
        return task;
    }

    public Utilizator removeUtil(Long id) {
        Utilizator task = repoUtilizatori.delete(id);
        if(task != null) {
            task.getFriends().forEach(x -> {
                x.removeFriend(task);
                repoPrietenii.delete(new Tuple<Long, Long>(x.getId(), id));
                repoPrietenii.delete(new Tuple<Long, Long>(id, x.getId()));
            });
            notifyObservers();
        }
        return task;
    }

    public Iterable<Utilizator> getAll(){
        return repoUtilizatori.findAll();
    }

    public Prietenie addPrietenie(String s) {
        Prietenie pr = prietenieFactory.extractEntity(s);
        if(repoUtilizatori.findOne(pr.getId().getLeft())==null || repoUtilizatori.findOne(pr.getId().getRight()) == null){
            return pr;
        }
        Prietenie task = repoPrietenii.save(pr);
        if(task == null) {
            Utilizator u1 = repoUtilizatori.findOne(pr.getId().getLeft());
            Utilizator u2 = repoUtilizatori.findOne(pr.getId().getRight());
            u1.addFriend(u2);
            u2.addFriend(u1);
            notifyObservers();
        }
        return task;
    }

    public Prietenie removePrietenie(Tuple<Long,Long> id) {

        Prietenie task = repoPrietenii.delete(id);
        if(task != null) {
            Utilizator u1 = repoUtilizatori.findOne(task.getId().getLeft());
            Utilizator u2 = repoUtilizatori.findOne(task.getId().getRight());
            u1.removeFriend(u2);
            u2.removeFriend(u1);
            StreamSupport.stream(repoInvitatie.findAll().spliterator(), false)
                    .filter(x->{
                        return x.getId_from()== id.getRight() && x.getId_to() == id.getLeft() || x.getId_from()== id.getLeft() && x.getId_to() == id.getRight();
                    })
                    .forEach(x-> {
                        System.out.println(x.getStare());
                        repoInvitatie.delete(x.getId());
                    });

            notifyObservers();

        }
        return task;
    }

    public Prietenie getPrietenie(Tuple<Long,Long> id){
        return  repoPrietenii.findOne(id);
    }

    ///TO DO: add othemethods

    private List<Comunitate> createComunitati(){
        Map<Long,Long> com = new HashMap<Long, Long>();
        Map<Long,Comunitate> comunitati =  new HashMap<Long,Comunitate>();
        repoUtilizatori.findAll().forEach(x->{
            com.put(x.getId(),x.getId());
        });
        for(Prietenie x: repoPrietenii.findAll()){
            Long st = x.getId().getLeft();
            Long dr = x.getId().getRight();
            //System.out.println(st + " " + com.get(st) + " ->" +dr + " " + com.get(dr));

            if(com.get(st) != com.get(dr)){
                Long st1 =com.get(st);
                Long dr1 =com.get(dr);
                repoUtilizatori.findAll().forEach(y->{
                    if(com.get(y.getId()) == st1){
                        com.put(y.getId(),dr1);
                    }
                });
            }
            /*com.values().forEach(y->{
                System.out.print(y + " ");

            });
            System.out.println();
            */

        }


        for(Utilizator x: repoUtilizatori.findAll()){
            Long comId = com.get(x.getId());
            Comunitate co =comunitati.get(comId);
            if( co == null){
                Comunitate c = new Comunitate();
                c.addUtil(x);
                c.setId(comId);
                comunitati.put(comId,c);
            }
            else{
                co.addUtil(x);
            }

        }
        /*
        comunitati.values().forEach(x->{
            System.out.println("Comunitate:");
            x.getUtil().forEach(System.out::println);
        });*/
        /*comunitati.values().forEach(x->{
            x.getUtil().forEach(System.out::println);
            System.out.println();
        });*/
        //repoPrietenii.findAll().forEach(x-> System.out.println(x.getId().getLeft() +" " +x.getId().getRight()));
        return comunitati.values().stream().collect(Collectors.toList());
    }


    public int getComunitate(){

        /*int size = 0;
        long comId = 0;
        for (Comunitate c: comunitati.values()){
            if(c.getSize()>size){
                size = c.getSize();
                comId = c.getId();
            }
        }
        return comunitati.get(comId).getAll();
        */

        return createComunitati().size();
    }

    private void Bkt(Utilizator x, HashSet<Long> viz, int lung){
        if(lung > l){
            l =lung;
        }
        for(Utilizator y: x.getFriends()){
            if(!viz.contains(y.getId())){
                viz.add(y.getId());
                Bkt(y,viz,lung+1);
                viz.remove(y.getId());
            }
        }

    }

    private int LongestPath(Comunitate x){
        l = -1;
        HashSet<Long> viz = new HashSet<Long>();
        for(Utilizator util:x.getUtil()){
            viz.add(util.getId());
            Bkt(util,viz,0);
            viz.remove(util.getId());
        }

        return l;
    }


    public Iterable<Utilizator> getComunitateLong(){
        List<Comunitate> comunitati = createComunitati();
        int drum=0;
        Comunitate comunitate = null;
        for(Comunitate x : comunitati){
            int l = LongestPath(x);
            if(l > drum){
                drum = l;
                comunitate = x;
            }
        }
        return comunitate.getUtil();
    }

    public Iterable<PrietenDTO> getPrieteni(long id){

            Utilizator x = repoUtilizatori.findOne(id);
            if(x != null){
                return x.getFriends().stream()
                        .map(y -> {
                            LocalDate data;
                            Prietenie pr = repoPrietenii.findOne(new Tuple<>(id,y.getId()));
                            if(pr != null){
                                data = pr.getDate();
                            }
                            else{
                                pr = repoPrietenii.findOne(new Tuple<>(y.getId(),id));
                                data = pr.getDate();
                            }
                            return new PrietenDTO(y.getFirstName(),y.getLastName(),data,pr.getId());
                        }).collect(Collectors.toList());
            }
            else
                return  null;
    }

    public Iterable<PrietenDTO> getPrieteniAfterMonth(long id, int luna){

        Utilizator x = repoUtilizatori.findOne(id);
        if(x != null){
            return x.getFriends().stream()
                    .map(y -> {
                        LocalDate data;
                        Prietenie pr = repoPrietenii.findOne(new Tuple<>(id,y.getId()));
                        if(pr != null){
                            data = pr.getDate();
                        }
                        else{
                            pr = repoPrietenii.findOne(new Tuple<>(y.getId(),id));
                            data = pr.getDate();
                        }
                        return new PrietenDTO(y.getFirstName(),y.getLastName(),data,pr.getId());
                    })
                    .filter(y->y.getData().getMonthValue()==luna)
                    .collect(Collectors.toList());
        }
        else
            return  null;
    }

    public Utilizator getOne(Long id){
        return repoUtilizatori.findOne(id);
    }

    public Iterable<Message> getConversatie(long id1, long id2) {
        List<Message> ans = new ArrayList<>();
        repoMessage.findAll().forEach(x->{
            if(x.getFrom() == id1){
                for(Long y :x.getTo()){
                    if(y==id2){
                        ans.add(x);
                        break;
                    }
                }
            }
            else if(x.getFrom() == id2){
                for(Long y :x.getTo()){
                    if(y==id1){
                        ans.add(x);
                        break;
                    }
                }
            }
        });
        return ans.stream().sorted(Comparator.comparing(Message::getData)).collect(Collectors.toList());
    }

    public Message sendMessage(long from, LocalDateTime data, List<Long> dest, String mesaj){
        Message msg = new Message(from,data,mesaj);
        msg.setTo(dest);
        notifyObservers();
        msg = repoMessage.save(msg);
        notifyObservers();
        return msg;
    }
    public Message replayMessage(long id_msg,long from, LocalDateTime data,String mesaj){
        Message msg = repoMessage.findOne(id_msg);
        for(Long x : msg.getTo()){
            if(x == from){
                List<Long> to = new ArrayList<>();
                to.add(msg.getFrom());
                Message replay = new ReplayMessage(from,data,mesaj,msg);
                replay.setTo(to);
                msg = repoMessage.save(replay);
                notifyObservers();
                return msg;
            }
        }
        return msg;
    }

    public Invitatie sendInvidatie(long id_from,long id_to){
        if(repoPrietenii.findOne(new Tuple<>(id_from,id_to)) == null && repoPrietenii.findOne(new Tuple<>(id_to,id_from)) == null){
            Invitatie invitatie = new Invitatie(id_from,id_to,LocalDate.now(),1);
            if(repoInvitatie.save(invitatie) == null) {
                notifyObservers();
                return invitatie;
            }
            else
                return null;

        }
        else
        {
            return null;
        }
    }

    public Iterable<Invitatie> getInvitatiiPrimite(long id_user){
        return StreamSupport.stream(repoInvitatie.findAll().spliterator(), false)
                .filter(x->x.getId_to()==id_user)
                .collect(Collectors.toList());
    }

    public Iterable<Invitatie> getInvitatiiTrimise(long id_user){
        return StreamSupport.stream(repoInvitatie.findAll().spliterator(), false)
                .filter(x->x.getId_from()==id_user)
                .collect(Collectors.toList());
    }


    public Invitatie acceptInvitatie(long id_user,long id_invitatie){
        Invitatie inv = repoInvitatie.findOne(id_invitatie);
        if(inv != null){
            if(inv.getId_to() == id_user && inv.getStare() == 1){
                inv.setStare(3);
                repoInvitatie.update(inv);
                Prietenie pr = new Prietenie(LocalDate.now());
                pr.setId(new Tuple<>(inv.getId_from(),inv.getId_to()));
                repoPrietenii.save(pr);
                Utilizator u1 = repoUtilizatori.findOne(pr.getId().getLeft());
                Utilizator u2 = repoUtilizatori.findOne(pr.getId().getRight());
                u1.addFriend(u2);
                u2.addFriend(u1);
                notifyObservers();
                return  inv;
            }
            else{
                return null;
            }
        }
        return null;
    }

    public Invitatie rejectInvitatie(long id_user,long id_invitatie){
        Invitatie inv = repoInvitatie.findOne(id_invitatie);
        if(inv != null){
            if(inv.getId_to() == id_user && inv.getStare() == 1){
                inv.setStare(2);
                repoInvitatie.update(inv);
                notifyObservers();
                return  inv;
            }
            else{
                return null;
            }
        }
        return null;
    }

    public boolean isApendingRequest(Long id1, Long id2){
        List<Invitatie> l = StreamSupport.stream(repoInvitatie.findAll().spliterator(), false)
                .filter(x->{
                    return (x.getId_to()==id1 && x.getId_from() == id2) || (x.getId_from() == id1 && x.getId_to() == id2) && x.getStare()!=3;
                }).collect(Collectors.toList());
        if(l.size() == 0 ) return false;
        return true;
    }

    public Invitatie removeInvitatie(Long id){
        Invitatie invitatie = repoInvitatie.delete(id);
        notifyObservers();
        return  invitatie;
    }



    public Account signin(String username, String password){
        return repoAccount.findOne(username,password);
    }

    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach(x->x.update());
    }
}
