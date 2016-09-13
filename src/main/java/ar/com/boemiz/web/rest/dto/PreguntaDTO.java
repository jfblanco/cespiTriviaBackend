package ar.com.boemiz.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Pregunta entity.
 */
public class PreguntaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 140)
    private String descripcion;
    
    private ImagenDTO imagen;

    @NotNull
    @Size(max = 140)
    private String opcion1;

    @NotNull
    @Size(max = 140)
    private String opcion2;

    @NotNull
    @Size(max = 140)
    private String opcion3;

    @NotNull
    @Size(max = 140)
    private String correcta;
    
    private UserDTO user;
    
    private Set<NivelDTO> nivels = new HashSet<>();
    
    private Set<DificultadDTO> dificultades = new HashSet<>();
    
    private Set<CategoriaDTO> categorias = new HashSet<>();
    
    private Set<PreguntaDTO> preguntas = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }


    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }


    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getCorrecta() {
        return correcta;
    }

    public void setCorrecta(String correcta) {
        this.correcta = correcta;
    }

    public Set<NivelDTO> getNivels() {
        return nivels;
    }

    public void setNivels(Set<NivelDTO> nivels) {
        this.nivels = nivels;
    }     

    public Set<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public Set<PreguntaDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Set<PreguntaDTO> preguntas) {
        this.preguntas = preguntas;
    }

    public Set<DificultadDTO> getDificultades() {
        return dificultades;
    }

    public void setDificultades(Set<DificultadDTO> dificultades) {
        this.dificultades = dificultades;
    }

    public ImagenDTO getImagen() {
        return imagen;
    }

    public void setImagen(ImagenDTO imagen) {
        this.imagen = imagen;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PreguntaDTO preguntaDTO = (PreguntaDTO) o;

        Boolean resultado = true;
        
        resultado &= this.getDescripcion().equals(preguntaDTO.getDescripcion());
        resultado &= this.getOpcion1().equals(preguntaDTO.getOpcion1());
        resultado &= this.getOpcion2().equals(preguntaDTO.getOpcion2());
        resultado &= this.getOpcion3().equals(preguntaDTO.getOpcion3());

        return resultado;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PreguntaDTO{" +
                "id=" + id +
                ", descripcion='" + descripcion + "'" +
                ", opcion1='" + opcion1 + "'" +
                ", opcion2='" + opcion2 + "'" +
                ", opcion3='" + opcion3 + "'" +
                ", correcta='" + correcta + "'" +
                '}';
    }
}
