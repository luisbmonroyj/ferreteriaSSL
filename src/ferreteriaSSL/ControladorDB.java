/*
* @authors 
* Sandra Milena Piña Soto
* Santiago Martínez Londoño
* Luis Bernardo Monroy Jaramillo
*/

package ferreteriaSSL;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ControladorDB {
    private static Connection connection;
    static boolean conectado;
    static boolean echoON;
    
    private static Statement st;
    
    public ControladorDB(boolean echoON){
        this.echoON = echoON;
    }
    
    public static boolean conectar() {
        conectado = false;
        try {
            Class.forName(Properties.DRIVER);
            if(echoON)System.out.println("JDBC driver found.");
        }
        catch (ClassNotFoundException e) {
            if(echoON)System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(Properties.dbURL, Properties.USERNAME, Properties.PASSWORD);
            if(echoON)System.out.println("JDBC driver connected.");
            st = connection.createStatement();
            conectado = true;
        } 
        catch (SQLException e) {
            if(echoON)System.out.println("Connection to MySQL db failed");
            e.printStackTrace();
        }
        return conectado;
    }
    public static boolean desconectar() {
        try {
            if (!connection.isClosed()){
                connection.close();
                conectado = false;
                if(echoON)System.out.println("Connection to MySQL finished");
            }
        }
        catch(SQLException e) {
            if(echoON)System.out.println("Connection to MySQL db failed");
            e.printStackTrace();
        }
        return conectado;
    }
    
    public boolean insertValues(String tabla,String campos,String csv){
        boolean exitoso = false;
        try {
            st.execute("insert into "+tabla+" ("+campos+") values ("+csv+");");
            exitoso = true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return exitoso;
    }
    
    
    public boolean updateValues(String dbTable,String campo,String valor,String pk, String id){
        boolean exitoso = false;
        try {
            if(echoON)System.out.println("updateValues: update "+dbTable+" set "+ campo +"= "+valor+" where " + pk +" = "+id+";");
            st.execute("update "+dbTable+" set "+ campo +"= "+valor+" where " + pk +" = "+id+";");
            exitoso = true;
        } 
        catch (SQLException e) {
            if(echoON)System.out.println("Error actualizando datos");
            e.printStackTrace();
        }
        return exitoso;
    }
    
    
    public boolean deleteValues(String tabla, String llave, String id, boolean isString){
        boolean exitoso = false;
        try {
            if(echoON)System.out.println("deleteValues: delete from "+tabla+" where "+llave+" = '"+id+"'");
            if (isString)
                st.execute("delete from "+tabla+" where "+llave+" = '"+id+"'");
            else
                st.execute("delete from "+tabla+" where "+llave+" = "+id);
            exitoso = true;
        } 
        catch (SQLException e) {
            if(echoON)System.out.println("error en deleteValues");
            e.printStackTrace();
        }
        return exitoso;
    }
    public boolean unusedPrimaryKey(String dbTable, String fk, String pk){
        int contador = 0;
        try {
            if(echoON)System.out.println("unusedPrimaryKey:SELECT * from " + dbTable + " where " + fk + " = '" + pk + "'");
            ResultSet rs = st.executeQuery("SELECT * from " + dbTable + " where " + fk + " = '" + pk + "'");
            while (rs.next())
                contador++;
            //if(echoON)System.out.println(contador);
        }
        catch (SQLException e) {
            if(echoON)System.out.println("Error en el metodo unusedPrimaryKey");
            e.printStackTrace();
        }
        if (contador > 0){
            return false;
        } else{
            return true;
        }
    }
    
    public int getCount (String dbTable,String column){
        //obtiene la cantidad de datos en la tabla
        int contador = 0;
        try {
            if(echoON)System.out.println("getCount: SELECT "+column+" FROM "+dbTable);
            ResultSet rs = st.executeQuery("SELECT "+column+" FROM "+dbTable);
            while (rs.next())
                contador++;
            //if(echoON)System.out.println(contador);
        }
        catch (SQLException e) {
            if(echoON)System.out.println("Error en el metodo getCount");
            e.printStackTrace();
        }
        return contador;
    }
    
    public String[] getStringValues(String dbTable,String column,String primaryKey){
        //Obtiene los datos de un campo String, ordenados por llave primaria
        String[] valores = new String[getCount(dbTable,column)];
        try{
            ResultSet rs = st.executeQuery("SELECT "+column+" FROM "+dbTable+" ORDER BY "+primaryKey);
            if(echoON)System.out.println("getStringValues: SELECT "+column+" FROM "+dbTable+" ORDER BY "+primaryKey);
            int i=0;//contador
            while ( rs.next() ){
                valores[i] = rs.getString(column);
                i++;
            }
        }
        catch (Exception e) { JOptionPane.showMessageDialog(null,e.getLocalizedMessage() );}
        return valores;
    }
    
    public String[][] getData(String dbTable,String campo){
        String[][] tabla;
        int contador = 0;
        try {
            String sqlStr = "select "+campo+" from "+dbTable;
            ResultSet rs = st.executeQuery(sqlStr);
            if(echoON)System.out.println(rs.getString(campo));
            while (rs.next()) {
                contador++;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        tabla = new String[contador][5];
        contador=0;
        try {
            String sqlStr = "select * from Producto";
            ResultSet rs = st.executeQuery(sqlStr);
            if(echoON)System.out.println(rs.toString());
            String[] fila = rs.getString("*").split(",");
            for (int i=0;i<fila.length;i++)
                tabla[contador][i] = fila[i];
            while (rs.next()) {
                contador++;
                if(echoON)System.out.println(rs.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return tabla; 
    }
    
    
    public ResultSet queryConsulta(String Consulta){
        ResultSet rs;
        rs = null;
        try {
            rs = st.executeQuery(Consulta);
        }
        catch (SQLException e){
            if(echoON)System.out.println("\nerror 5 " + e);
        }
        return rs;
    }
    public String[][] resultadoQuery(ResultSet rs)    {
        String [][] elementos;
        elementos = null;
        int i;
        try {
            ResultSetMetaData rsmd;
            rsmd = (ResultSetMetaData) rs.getMetaData();
            elementos = new String [100][rsmd.getColumnCount()];
            i=0;
            while(rs.next()){
                for(int j = 0 ;j<rsmd.getColumnCount();j++){
                    elementos[i][j]=rs.getString(j+1);
                }
                i++;
            }
        }
        catch (SQLException e){
            System.out.print("\nError" + e);
        }
        return elementos;
    }

    public String [][] getMetaData(String campos,String dbTable,String order){
        //intento de leer los datos de toda una tabla
        String RR[][];
        try{
        RR=resultadoQuery(queryConsulta("SELECT "+campos+" FROM "+dbTable+" ORDER BY "+order+";"));
        return RR;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Problema al cargar la lista","Lista",JOptionPane.ERROR_MESSAGE);
            System.out.print(e);
            return null;
        }    
    }
    public String [] getSingleRow(String dbTable,String field, String value){
        String salida[] = null;
        try{
            String RR[][];
            RR=resultadoQuery(queryConsulta("SELECT * FROM "+dbTable+" WHERE "+field+"= '"+value+"';"));
            salida = RR[0];
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Problema al cargar los datos","Lista",JOptionPane.ERROR_MESSAGE);
            System.out.print(e);
        }
        return salida;
        
    }
    public String getSingleData(String data, String dbTable,String field, String value){
        String salida = null;
        try{
            String RR[][];
            RR=resultadoQuery(queryConsulta("SELECT "+data+" FROM "+dbTable+" WHERE "+field+"= '"+value+"';"));
            salida = RR[0][0];
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Problema al cargar los datos","Lista",JOptionPane.ERROR_MESSAGE);
            System.out.print(e);
        }
        return salida;
    }
}
