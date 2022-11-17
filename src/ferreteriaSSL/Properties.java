/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ferreteriaSSL;

/**
 *
 * @author Shaitan
 */
public interface Properties {
       final String nombreDataBase = "reto5";
       final String USERNAME= "root";
       final String PASSWORD= "santy98";
       final String dbURL = "jdbc:mysql://localhost:3306/"+nombreDataBase+"?serverTimezone=UTC";
       final String DRIVER ="com.mysql.cj.jdbc.Driver";
    
}
