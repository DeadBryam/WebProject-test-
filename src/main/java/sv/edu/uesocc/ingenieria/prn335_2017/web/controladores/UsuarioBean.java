/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.UsuarioFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Usuario;

/**
 *
 * @author bryan
 */
@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable{

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    @EJB
    UsuarioFacadeLocal usuario;
    List<Usuario> usuarioData = new ArrayList<>();
    Usuario data = new Usuario();

    public UsuarioFacadeLocal getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioFacadeLocal usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarioData() {
        return usuarioData;
    }

    public void setUsuarioData(List<Usuario> usuarioData) {
        this.usuarioData = usuarioData;
    }

    public Usuario getData() {
        return data;
    }

    public void setData(Usuario data) {
        this.data = data;
    }

    public void crear() {
        System.out.println(data.getComentarios());
        System.out.println(data.getFechaNacimiento());
        
            try {
                usuario.create(data);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
    }
    
    @PostConstruct
    public void init() {
        if (usuarioData != null) {
            this.usuarioData = usuario.findAll();
        } else {
            this.usuarioData = Collections.EMPTY_LIST;
        }
    }
}
