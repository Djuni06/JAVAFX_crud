// import javafx.beans.property.SimpleStringProperty;

public class Buku{

    private int Idbuku;
    private String Judul= null;
    private String Jenis = null;

    public Buku(int Idbuku, String Judul, String Jenis) {
        this.Idbuku = Idbuku;
        this.Judul = Judul;
        this.Jenis = Jenis;
    }

    public int getIdbuku() {
        return Idbuku;
    }

    public String getJudul() {

        return Judul;
    }

    public String getJenis() {
        return Jenis;
    }
}