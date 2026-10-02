public class Pelicula {
    private int ID;
    private String Titulo;
    private String Director;
    private int Año;
    private String Genero;

    public Pelicula (int ID, String Titulo, String Director, int Año, String Genero){
        this.ID=ID;
        this.Titulo=Titulo;
        this.Director=Director;
        this.Año=Año;
        this.Genero=Genero;
    }

    public int getId(){
        return this.ID;
    }
    public String getTitulo(){
        return this.Titulo;
    }
    public String getDirector(){
        return this.Director;
    }
    public int getAño(){
        return this.Año;
    }
    public String getGenero(){
        return this.Genero;
    }
    @Override
    public String toString(){
        return "ID: " + this.ID + 
            "| Titulo: " + this.Titulo +
            "| Director: " + this.Director +
            "| Año: " + this.Año +
            "| Genero: " + this.Genero;
    }
}