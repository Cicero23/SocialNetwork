package socialnetwork.ui;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.ReplayMessage;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.UtilizatorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UiSimplu {
    UtilizatorService uService;
    Scanner in = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

    public UiSimplu(UtilizatorService uService) {
        this.uService = uService;
    }

    public void run(){

        while (true){
            System.out.print(">>");
            String s = in.nextLine();
            String ceva = "aaa";
            if(s.equals("exit")){
                in.close();
                break;
            }
            else if(s.compareTo("add Utilizator") == 0){
                addUtilizator();
            }
            else if(s.compareTo("show Utilizatori") == 0){
                showUtilizatori();
            }
            else if(s.compareTo("remove Utilizator") ==0){
                removeUtilizator();
            }
            else if(s.compareTo("add Prietenie") == 0){
                addPrietenie();
            }
            else if(s.compareTo("remove Prietenie") == 0){
                removePrietenie();
            }
            else if(s.compareTo("count Comunitati") == 0){
                countComunitati();
            }
            else if(s.compareTo("show Comunitate") == 0){
                showComunitate();
            }
            else if(s.compareTo("show Prietenii") == 0){
                showPrietenii();
            }
            else if(s.compareTo("show Prietenii M") == 0){
                showPrieteniiM();
            }
            else if(s.compareTo("show Conversatie") == 0){
                showConversatie();
            }
            else if(s.compareTo("send Message") == 0){
                sendMessage();
            }
            else if(s.compareTo("replay Message") == 0){
                replayMessage();
            }
            else if(s.compareTo("send Invitatie") == 0){
                sendInvitatie();
            }
            else if(s.compareTo("show Invitatii Primite") == 0){
                showInvitatiiPrimite();
            }
            else if(s.compareTo("accept Invitatie") == 0){
                accpetInvitatie();
            }
            else if(s.compareTo("reject Invitatie") == 0){
                rejectInvitatie();
            }
            else {
                System.out.println("Comanda invalida");
            }
            System.out.println("Ceva mesaj");
        }
    }


    private void showUtilizatori(){
        System.out.println("Utizatori sunt:");
        uService.getAll().forEach(System.out::println);
    }

    private void showPrietenii(){
        String s = in.nextLine();
        try {
            List<String> attr = Arrays.asList(s.split(" "));
            long id =Long.parseLong(attr.get(0));
            uService.getPrieteni(id).forEach(System.out::println);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 1 intregi");
        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 1 intregi");
        }
        catch (NullPointerException e){
            System.out.println("Nu exista");
        }
    }

    private void showPrieteniiM(){
        String s = in.nextLine();
        try {
            List<String> attr = Arrays.asList(s.split(" "));
            long id =Long.parseLong(attr.get(0));
            int m = Integer.parseInt(attr.get(1));
            uService.getPrieteniAfterMonth(id,m).forEach(System.out::println);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 1 intregi");
        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 1 intregi");
        }
        catch (NullPointerException e){
            System.out.println("Nu exista");
        }
    }


    private void addUtilizator(){
        String s = in.nextLine();
        try {
            if(uService.addUtilizator(s) == null)
                System.out.println("Adaugare cu succes");
            else
                System.out.println("Utilizator existent");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void removeUtilizator(){
        String s = in.nextLine();
        try {
            List<String> attr = Arrays.asList(s.split(" "));
            Utilizator x = uService.removeUtil(Long.parseLong(attr.get(0)));
            if(x == null)
                System.out.println("Nu exista utilizatorul cu id-ul dat");
            else
                System.out.println("S-a eliminat utilizator:\n" + x);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Nu sunt suficeiente argumente");
        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdus un intreg");
        }
    }


    private void addPrietenie() {
        String s = in.nextLine();
        s = s+" " + LocalDate.now().toString();
        System.out.println(s);
        try {
            if(uService.addPrietenie(s) == null)
                System.out.println("Adaugare cu succes");
            else
                System.out.println("Prietenie existenta");
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void removePrietenie(){
        String s = in.nextLine();
        try {

            List<String> attr = Arrays.asList(s.split(" "));
            Tuple<Long,Long> x = new Tuple<Long, Long>(Long.parseLong(attr.get(0)), Long.parseLong(attr.get(1)));
            Prietenie pr=uService.removePrietenie(x);
            if(pr == null){
                System.out.println("Nu exista prietenia");
            }
            else {
                System.out.println("S-a eliminat prietenia" );
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
    }
    private void countComunitati(){
        System.out.println("Sunt " + uService.getComunitate() + " comunitati");
    }

    private void showComunitate(){
        System.out.println("Comunitatea cu cel mai lung drum este alcatuita din:");
        uService.getComunitateLong().forEach(System.out::println);
    }

    private void showConversatie() {
        String s = in.nextLine();
        try {

            List<String> attr = Arrays.asList(s.split(" "));
            long id1 = Long.parseLong(attr.get(0));
            long id2 = Long.parseLong(attr.get(1));
            uService.getConversatie(id1,id2).forEach(x-> {
                String str = "Id: " + x.getId().toString() + " From: " + x.getFrom() + " Mesaj: " + x.getMesaj() + " Data: " + x.getData().toString();

                if(x.getClass() == ReplayMessage.class){
                    str = str +" Replay: " + ((ReplayMessage) x).getMsg().getId();
                }
                System.out.println(str);
            });

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }

    }

    private void sendMessage(){
        try {
            String idS = in.nextLine();
            String toS = in.nextLine();
            String msg = in.nextLine();
            Long id = Long.parseLong(idS);
            List<String> attr = Arrays.asList(toS.split(" "));
            List<Long> to = new ArrayList<>();
            attr.forEach(x->{
                to.add(Long.parseLong(x));
            });

            uService.sendMessage(id, LocalDateTime.now(), to, msg);
        }
        catch (NumberFormatException e){
            System.out.println("prima si a 2-a linie trebuie sa contina numere");
        }
    }


    private void replayMessage(){
        try {
            String idS = in.nextLine();
            String msg = in.nextLine();
            List<String> attr = Arrays.asList(idS.split(" "));
            Long id_msg = Long.parseLong(attr.get(0));
            Long id_from = Long.parseLong(attr.get(1));
            uService.replayMessage(id_msg,id_from,LocalDateTime.now(),msg);
        }
        catch (NumberFormatException e){
            System.out.println("prima si a 2-a linie trebuie sa contina numere");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }

    }

    private void sendInvitatie(){
        try {
            String idS = in.nextLine();

            List<String> attr = Arrays.asList(idS.split(" "));
            Long id_from= Long.parseLong(attr.get(0));
            Long id_to = Long.parseLong(attr.get(1));
            if(uService.sendInvidatie(id_from,id_to) == null){
                System.out.println("Exista deja prietenia");
            }
        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
    }
    private void showInvitatiiPrimite(){
        try {
            String idS = in.nextLine();
            Long id_to= Long.parseLong(idS);
            uService.getInvitatiiPrimite(id_to).forEach(x->{
                System.out.print(x.getId().toString() + " ");
                System.out.println(x);
            });

        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 1 intregi");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 1 intregi");
        }
    }

    private void accpetInvitatie(){
        try {
            String idS = in.nextLine();

            List<String> attr = Arrays.asList(idS.split(" "));
            Long id_to= Long.parseLong(attr.get(0));
            Long id_mesaj = Long.parseLong(attr.get(1));
            if(uService.acceptInvitatie(id_to,id_mesaj) == null){
                System.out.println("Nu s-a putut updata");
            }

        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
    }
    private void rejectInvitatie(){
        try {
            String idS = in.nextLine();

            List<String> attr = Arrays.asList(idS.split(" "));
            Long id_to= Long.parseLong(attr.get(0));
            Long id_mesaj = Long.parseLong(attr.get(1));
            if(uService.rejectInvitatie(id_to,id_mesaj) == null){
                System.out.println("Nu s-a putut updata");
            }

        }
        catch (NumberFormatException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Trebuie introdusi 2 intregi");
        }
    }

}
