package com.mave.ws.rest;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mave.ws.utils.Reporte;
import com.mave.ws.utils.SoliciEstado;

import oracle.jdbc.internal.OracleTypes;



public class Conexion {
	private static Connection conexion;
	private Statement stmt;
	private CallableStatement cstmt;
	
	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	public Conexion conectar() {
		try {

			Class.forName("oracle.jdbc.OracleDriver");
			String BaseDeDatos = "jdbc:oracle:thin:@10.1.1.133:6521:GSEGUROS";
			conexion = DriverManager.getConnection(BaseDeDatos, "Ice", "ice");			
			stmt=conexion.createStatement();
			
			//callOracleStoredProcCURSORParameter();
			
			if (conexion != null) {
				System.out.println("Conectando a la BD 1 \n");	
			} else {
				System.out.println("Conexión fallida a la BD 1");
			}

		} catch (Exception e) {
			System.out.println("Conexión fallida a la BD");
			e.printStackTrace();
		}
		return this;
	}
	
    public String packagess (String pakete, Integer paramer1){
    try{
        CallableStatement cst = conexion.prepareCall(pakete);
        //V_CADENA = ABCDEF123456P
        cst.registerOutParameter(1, Types.VARCHAR);
        cst.setInt(2,paramer1);        
        cst.execute();
        String cadena = cst.getString(1);
        System.out.println("conn"+cadena);
        return cadena;
    }
	catch (Exception SQLException) {
		System.out.println("Saltó la escepción\n"+SQLException);
	}
	return "error";
    }
    
    public  void packagessactualizaxml (String pakete, Integer idsolic, Integer paramer1){
        try{
            CallableStatement cst = conexion.prepareCall(pakete);
            //V_CADENA = ABCDEF123456P
            cst.registerOutParameter(1, Types.VARCHAR);
            cst.setInt(2,idsolic);
            cst.setInt(3,paramer1);
            cst.execute();
           
            
        }
    	catch (Exception SQLException) {
    		System.out.println("Saltó la escepción\n"+SQLException);
    	}
    	
        }
    
    public String packagess2 (String pakete2, Integer paramer0, Integer paramer1, String cadenasend){
        try{
            CallableStatement cst = conexion.prepareCall(pakete2);
            cst.registerOutParameter(1, Types.VARCHAR);  
            cst.registerOutParameter(2, Types.VARCHAR);  
            cst.setString(3,cadenasend);        
            cst.execute();
            String cadena = cst.getString(1);
            String cadena2 = cst.getString(2);
            return cadena+","+cadena2;
        }
    	catch (Exception SQLException) {
    		System.out.println("Saltó la escepción\n"+SQLException);
    	}
    	return "error";
        }
    
    
    
    public ArrayList<SoliciEstado> packagess01RC (String pakete3, Integer P_estado){
    	ArrayList<SoliciEstado> datos = new ArrayList<SoliciEstado>();
    	try{

            CallableStatement cst = conexion.prepareCall(pakete3);
            cst.setInt(1,P_estado);      	
            cst.registerOutParameter(2, OracleTypes.CURSOR);  
            cst.execute();
            ResultSet  rs = (ResultSet) cst.getObject(2);
            while (rs.next()) {
            	datos.add(new SoliciEstado(rs.getInt("IDSOLICITUD"),rs.getString("PKGNAME"),rs.getString("CADENA"),rs.getString("PLANTILLA"),rs.getInt("VERSION"),rs.getInt("CAMPOS"),rs.getInt("IDPLANTILLA")));
            }
            return datos;
        }
    	catch (Exception SQLException) {
    		System.out.println("Saltó la escepción\n"+SQLException);
    	}
    	return datos;
        }
    
    public List<ArrayList<String>> packageReporte (String nombrepakete4, String[]  arreglodatos, Integer ID, Integer CAMPOS, Integer VERSION, String PLANTILLA, Integer No_registro, String namepkg){
    	 List<ArrayList<String>> conjunto = new ArrayList<>();
    	ArrayList<String> datosREPORTE = new ArrayList<String>();
    	ArrayList<String> datosplantilla = new ArrayList<String>();
    	try{
    	int i=1, j=1;
    	System.out.println("entra1");
            CallableStatement cst = conexion.prepareCall(nombrepakete4);
           for ( i=1; i<arreglodatos.length; i++ ) {
        	   cst.setString(i,arreglodatos[j].toString());
        	   j++;
    	}   	 
          
            cst.registerOutParameter(arreglodatos.length, OracleTypes.CURSOR);  //No de atributos
            cst.execute();
           
            ResultSet  rs = (ResultSet) cst.getObject(arreglodatos.length); //No atributos
            System.out.println("entra2"+arreglodatos.length);
            ResultSetMetaData al = rs.getMetaData();
            System.out.println("entra3");
            		while (rs.next()) {	
            			  int h = al.getColumnCount();
            			  System.out.println("resulcolumn"+h);
            			  
            for (int k=1; k<= 23; k++)
            {
            	   
              datosplantilla.add(al.getColumnLabel(k));
              datosREPORTE.add(rs.getString(k));
              
          //  System.out.println(al.getColumnLabel(k));
         //   System.out.println(rs.getString(k));
            }
            
            }
            datosREPORTE.add(String.valueOf(VERSION));
            	datosplantilla.add(String.valueOf(VERSION));
            datosREPORTE.add(String.valueOf(ID));
            	datosplantilla.add(String.valueOf(ID));
            datosREPORTE.add(String.valueOf(No_registro));
            	datosplantilla.add(String.valueOf(No_registro));
            datosREPORTE.add(namepkg);
            	datosplantilla.add(namepkg);
            		conjunto.add(datosREPORTE);
            		conjunto.add(datosplantilla);
            return conjunto;
         
        }
    	catch (Exception SQLException) {
    		return conjunto;
    		//System.out.println("Saltó la escepción gato\n"+SQLException);
    	}
    
        }
    
    
    public ArrayList<String> packageNomcamp (String nombrepakete4, Integer PLANTILLA){
    	ArrayList<String> datosplantilla = new ArrayList<String>();
    	try{

            CallableStatement cst = conexion.prepareCall(nombrepakete4);       	
      	
           	cst.setInt(1,1);       //	cst.setString(2,dato2);        	//cst.setString(3,dato3);         	//cst.setString(4,dato4);            	//cst.setString(5,dato5);
            cst.registerOutParameter(2, OracleTypes.CURSOR);  //No de atributos
            cst.execute();
            ResultSet  rs = (ResultSet) cst.getObject(2); //No atributos
            Integer i=0;
            while (rs.next()) {
             datosplantilla.add(rs.getString(1));
             
           //  System.out.println("campo"+rs.getString(1));
             i= i+1;
            }
        
            return datosplantilla;  
        }
    	catch (Exception SQLException) {
    		System.out.println("Saltó la escepción\n"+SQLException);
    	}
    	return datosplantilla;
        }
    
    public String[] ejecutarConsulta1(String QUERY) { 
    	String  devolver[] = null;
    	try {
 
			ResultSet rs=stmt.executeQuery(QUERY); 
			
		
			while(rs.next()) {
				for(int i=1; i<=rs.getFetchSize(); i++) {
					devolver[i]=rs.getString(i)+" ";
				}
				//devolver+="\n";
			}
			return devolver;
		}
		catch (Exception SQLException) {
			System.out.println("Saltó la escepción\n"+SQLException);
		}
		return devolver;
	}
    
    
	public String ejecutarConsulta(String QUERY) { 
		try {
 
			ResultSet rs=stmt.executeQuery(QUERY); 
			
			String devolver="";
			while(rs.next()) {
				for(int i=1; i<=rs.getFetchSize(); i++) {
					devolver+=rs.getString(i)+" ";
				}
				devolver+="\n";
			}
			return devolver;
		}
		catch (Exception SQLException) {
			System.out.println("Saltó la escepción\n"+SQLException);
		}
		return "error";
	}
	
	
	
    public boolean desconectar() {
    	try {
    		conexion.close();
    		return true;
    	}
    	catch (Exception SQLException) {
    		return false;
    	}
    }
}
