package socialnetwork.domain;

import java.time.LocalDate;

public class PrietenDTO {

    private String nume;
    private String prenume;
    private LocalDate data;
    private Tuple<Long,Long> id;


    public PrietenDTO(String nume, String prenume, LocalDate data, Tuple<Long, Long> id) {
        this.nume = nume;
        this.prenume = prenume;
        this.data = data;
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.nume + " | " + this.prenume + " | " + this.data.toString();
    }

    public Tuple<Long, Long> getId() {
        return id;
    }

    public void setId(Tuple<Long, Long> id) {
        this.id = id;
    }
}
