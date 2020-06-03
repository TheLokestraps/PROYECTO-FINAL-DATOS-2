/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author TheLokestraps
 */
public class Jugador {
    
    
    public String nombre;
    public File rutaAvatar;
    
    public Jugador(String Nombre, File rutaAvatar){
        this.nombre = Nombre;
        this.rutaAvatar = rutaAvatar;
    }
    

}
