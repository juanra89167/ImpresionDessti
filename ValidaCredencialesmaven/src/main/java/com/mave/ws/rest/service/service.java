package com.mave.ws.rest.service;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.jespxml.JespXML;

import org.jespxml.modelo.Tag;


import com.mave.ws.rest.Conexion;
import com.mave.ws.rest.vo.Pet_Correo;
import com.mave.ws.rest.vo.Usuario;
import com.mave.ws.rest.vo.funciones;
import com.mave.ws.time.Temporizador;


import com.mave.ws.utils.*;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;



@Path("/mavenservice")
public class service {

	@POST
	@Path("/validatoken")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	
	public Usuario validausuario (Usuario vo) {
		String subcad1="";
		String subcad2;
		Parametros var = new Parametros();
		vo.setBandera(false);
		Conexion baseDatos = new Conexion();		
		baseDatos.conectar();
		String pakete="{call PKG_IMCONSULTAS.P_IMPARAMETROS(?,?)}";
		String resp=baseDatos.packagess(pakete,1);
		System.out.println("hola"+resp);
		if ( vo.getToken().equals(resp)){
		  char a=(char)35;
		  String[] arraycadena = vo.getCadena().split(String.valueOf(a)) ;
			 // En este momento extraemos la bandera y la cadena.
			//bandimp=0&report=OIRC_Certificado&p_unieco=1000,&p_ramo=1&p_estado=M&p_poliza=1178&p_suplement=9999999999999999999
				for (int i = 0; i < arraycadena.length; i++) 
					{
						System.out.println(arraycadena[i]);
						var.setBanimp(arraycadena[0]) ;
						var.setCadresp(arraycadena[1]);
					}
				//SE INSERTA LA CADENA SIN LA BANDERA DE IMPRESION A INGSOLICITUD
				String pakete2 = "{call PKG_IMACTUALIZACIONES.P_INGSOLICITUD(?,?,?)}";
				String resp2 = baseDatos.packagess2(pakete2, 1, 1,var.getCadresp());		
				// MANDAS RESP2 A CLIENTE WEBSERVICE ACABA
				vo.setRespuesta(resp2);
			
		}
		else {
			vo.setBandera(false);	
		}
		return vo;
	}

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	  public static boolean validateEmail(String email) {
		  
	        // Compiles the given regular expression into a pattern.
	        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
	 
	        // Match the given input against this pattern
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	 
	    }
	
	
	@POST
	@Path("/xml")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	//web service busca archivo, lee xml, llena plantilla y genera pdf que envia a correo.
	public Pet_Correo generapfd (Pet_Correo vo){

		funciones f =new  funciones();
		Parametros p =new  Parametros();
		 String nombre =vo.getNombre().toString();
		 char a=(char)38;
		 String[] arraycadena = nombre.split(String.valueOf(a)) ;
		 
		 
		
		if (validateEmail( vo.getCorreo().toString()) == true ) { //valida que sea correo el string que se recibe
			File miDir = new File("C:\\PKG_"+arraycadena[0].toString()); // directorio actual, cambiarlo si se quiere
			System.out.println("miDir"+miDir);
			System.out.println("file:"+arraycadena[1].toString());
			
	        f.verContenidoFolder(miDir, arraycadena[1].toString(),arraycadena[0].toString(),vo.getCorreo());
	        
	       
			//File folder = new File("c:\\"+nombre+"\\"+nombre+"\\java\\"); 
			//if (folder.isDirectory()) {//valida que el directorio exita deacuerdo a la cadena recibida en el webservice
			
		  // funciones.envia_correo(, arraycadena[1].toString()+".pdf"); // Se asigna el correo al que sera enviado el pdf
			//}
	        
			vo.setBandera(validateEmail( vo.getCorreo().toString()));
		}else
		{
			vo.setBandera(validateEmail( vo.getCorreo().toString()));
		}
		//vo.setBandera(true);
		return vo;
	}
	

	
	public  static void  main(String [] args) {
		//consulta();
		
		Conexion baseDatos = new Conexion();
		baseDatos.conectar();
		String nombre =  "{call PKG_IMCONSULTAS.P_SOLICXML(?,?)}";
		ArrayList<SoliciEstado> resp22=  baseDatos.packagess01RC(nombre, 2);	
		String nombrepkg= null;
		
			for (int i=0; i<resp22.size(); i++ ) {
				String cadenapkg =resp22.get(i).getCADENA();				
				char a=(char)38;
				int j=0;
				
				String[] arraycadena = cadenapkg.split(String.valueOf(a)) ;
				for ( j = 0; j < arraycadena.length; j++) 
				{
					
					String subcad=arraycadena[j].substring(arraycadena[j].indexOf("=")+1,arraycadena[j].toString().length());
					arraycadena[j]= subcad;
				
				}
			  
				if (6==arraycadena.length) {
				
				nombrepkg ="{call "+ resp22.get(i).getPKGNAME() +".REPORTE(?,?,?,?,?,?)}";
			
				List<ArrayList<String>> resp33 = baseDatos.packageReporte(nombrepkg,arraycadena,resp22.get(i).getIDSOLICITUD(),resp22.get(i).getCAMPOS(),resp22.get(i).getVERSION(),resp22.get(i).getPLANTILLA(),  i,resp22.get(i).getPKGNAME());
				//String nombrepkgnom= "{call PKG_IMCONSULTAS.P_NOMCAMPOREPORTE(?,?)";
				//ArrayList<String> nomcamprepo= baseDatos.packageNomcamp(nombrepkgnom,resp22.get(i).getIDPLANTILLA());
				if (!resp33.isEmpty()) {
					
					
					llenaxml(resp33);
					String actualiza  = "{call PKG_IMACTUALIZACIONES.P_ESTSOLICITUD(?,?,?)}";
					baseDatos.packagessactualizaxml(actualiza,resp22.get(i).getIDSOLICITUD(),3);
				}


				
				
				}
			
		
				
			}
		
			baseDatos.desconectar();
			 consulta( "Poliza", "Sucursal",  "Endoso",  "Ramo");
			
	}
	
	
public static void hilo()	{
	Date horaejecutar = new Date(System.currentTimeMillis());
	Calendar c = Calendar.getInstance();
	c.setTime(horaejecutar);
	if (c.get(Calendar.HOUR_OF_DAY) >= 22) {
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
	}
	c.set(Calendar.HOUR_OF_DAY, 18);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	horaejecutar = c.getTime();
	int tiempoRepeticion = 86400000; 
	Timer temporizador = new Timer();
	temporizador.schedule(new Temporizador(), horaejecutar, tiempoRepeticion);
	
	
	}
	
	public static void llenaxml(List<ArrayList<String>> resp33) {
		ArrayList<String> reporte = new ArrayList<String>();
        ArrayList<String> plantilla = new ArrayList<String>();
		 reporte= resp33.get(0);
		 plantilla= resp33.get(1);
		 
		
		 System.out.println("deroga1: "+reporte.get(2));
		 System.out.println("deroga2: "+reporte.get(3));
		 
		 
		 
		File folder = new File("c:\\"+plantilla.get(26).toString()+"\\"+plantilla.get(plantilla.size()-3).toString()+"\\java\\");
		folder.mkdirs();
		
        //creo el objeto JespXML con el archivo que quiero crear
		String nom=plantilla.get(plantilla.size()-3).toString() +"_"+plantilla.get(25).toString();
		//File archivo = new File ("C:\\archivo.txt");
        JespXML archivo = new JespXML(folder+"\\"+nom+".xml");
     //   JespXML nuevo = new JespXML(archivo);
        
        //declaro el Tag raiz, que en esta caso se llama bd
        Tag bd = new Tag("Solicitudes");
       // //le agrego un atributo a ese Tag (clientes="3")
        
        Tag Solicitud = new Tag("solicitud");
        	Tag KEY, VALUE;
        	KEY = new Tag ("Key");
        	VALUE = new Tag ("Value");
        	KEY.addContenido("1");
        	VALUE.addContenido("DATO");
        	Solicitud.addTagHijo(KEY);
        	Solicitud.addTagHijo(VALUE);
        	bd.addTagHijo(Solicitud);
        	
        //creo el Tag cliente, que va a tener un nombre y un apellido
        Tag Campos = new Tag("Campos");
        Tag nombre2;
        //construyo los Tags nombre y apellido y le agrego contenido
        for(int p=2; p<reporte.size()-4; p++)
        {
        	
        	System.out.println("campo:"+plantilla.get(p).toString());
        	//nombre2 = new Tag();
        	nombre2 = new Tag(plantilla.get(p).toString());
        	Tag apellido;
           apellido = new Tag("Value");
      
        //nombre2.addContenido(nomcamprepo.get(p));
           System.out.println("value:"+reporte.get(p));
           //if (reporte.get(p).toString().isEmpty()) {
        	  // apellido.addContenido(" p ");
          // }else {
        	  apellido.addContenido(reporte.get(p));
        	  
         //  }
     
    //   System.out.println("atrib:"+reporte.get(p).toString());
        //agrego el Tag nombre y apellido al Tag cliente
       nombre2.addTagHijo(apellido);
        Campos.addTagHijo(nombre2);
     //   
        }
        //finalmente agrego al Tag bd, el tag cliente
        //for(int i=0; i<resp33.size()-2; i++) {
        bd.addTagHijo(Campos);
        
        
        //bd.addTagHijo(Campos);
        //y escribo el archivo XML
        Tag Reporte = new Tag("Reporte");
    	Tag KEYR, VALUER;
    	KEYR = new Tag ("Key");
    	VALUER = new Tag ("Value");
    	KEYR.addContenido("VERSION");
    	VALUER.addContenido(reporte.get(reporte.size()-3));
    	Reporte.addTagHijo(KEYR);
    	Reporte.addTagHijo(VALUER);
    	bd.addTagHijo(Reporte);
        
        
        try {
			archivo.escribirXML(bd);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	 public static  Integer consulta(String Poliza, String Sucursal, String Endoso, String Ramo) {
		 String dato= null;
		 Conexion baseDatos = new Conexion();
		 baseDatos.conectar();
		 dato= baseDatos.ejecutarConsulta("Select IDSOLICITUD, IDESTADO from IMSOLICITUDES 	Where   POLIZA =  '607' AND RAMO = '2' AND SUCURSAL= '1000' AND ENDOSO = '0'");
		 Integer b= Integer.parseInt(dato);
		 System.out.println("consulta"+dato);
		 baseDatos.desconectar();
		 return b;
	   }
	 
	 public static  void Pausado(String Poliza, String Sucursal, String Endoso, String Ramo) {
	Integer dato=  consulta(Poliza, Sucursal,  Endoso,  Ramo) ;
		 if (dato ==3) {
			 Conexion baseDatos = new Conexion();
			 baseDatos.conectar();
			 //pon lo en 4
			// baseDatos.Actualizar("Select IDESTADO from IMSOLICITUDES 	Where   poliza =  'Poliza' AND RAMO = 'Ramo' AND SUCURSAL= 'Sucursal' AND ENDOSO = 'Endoso'");
			// Integer b= Integer.parseInt(dato);
			// System.out.println("consulta"+dato);
			 baseDatos.desconectar();
		 }
	   }
	 public static  void Reanudar(String Poliza, String Sucursal, String Endoso, String Ramo, String bandera ) {
			Integer dato=  consulta(Poliza, Sucursal,  Endoso,  Ramo) ;
			 if (dato ==4 && bandera == "web") {
				 
				 Conexion baseDatos = new Conexion();
				 baseDatos.conectar();
				 //pon lo en 3
				// baseDatos.Actualizar("Select IDESTADO from IMSOLICITUDES 	Where   poliza =  'Poliza' AND RAMO = 'Ramo' AND SUCURSAL= 'Sucursal' AND ENDOSO = 'Endoso'");
				// Integer b= Integer.parseInt(dato);
				// System.out.println("consulta"+dato);
				 baseDatos.desconectar();
			 } 
		   }
	 public static  void cancelar(String Poliza, String Sucursal, String Endoso, String Ramo) {
			Integer dato=  consulta(Poliza, Sucursal,  Endoso,  Ramo) ;
			 if (dato ==3) {
				 Conexion baseDatos = new Conexion();
				 baseDatos.conectar();
				 //pon lo en 7
				// baseDatos.Actualizar("Select IDESTADO from IMSOLICITUDES 	Where   poliza =  'Poliza' AND RAMO = 'Ramo' AND SUCURSAL= 'Sucursal' AND ENDOSO = 'Endoso'");
				// Integer b= Integer.parseInt(dato);
				// System.out.println("consulta"+dato);
				 baseDatos.desconectar();
			 }
		   }
	 public static  void Imprimri(String Poliza, String Sucursal, String Endoso, String Ramo) {
			Integer dato=  consulta(Poliza, Sucursal,  Endoso,  Ramo) ;
			 if (dato ==3) {
				 Conexion baseDatos = new Conexion();
				 baseDatos.conectar();
				 //pon lo en 5
				// baseDatos.Actualizar("Select IDESTADO from IMSOLICITUDES 	Where   poliza =  'Poliza' AND RAMO = 'Ramo' AND SUCURSAL= 'Sucursal' AND ENDOSO = 'Endoso'");
				// Integer b= Integer.parseInt(dato);
				// System.out.println("consulta"+dato);
				 baseDatos.desconectar();
			 }
		   }
	 public static  void Generar_pendiente(String Poliza, String Sucursal, String Endoso, String Ramo) {
			Integer dato=  consulta(Poliza, Sucursal,  Endoso,  Ramo) ;
			
				 Conexion baseDatos = new Conexion();
				 baseDatos.conectar();
				 //pon lo en 2
				// baseDatos.Actualizar("Select IDESTADO from IMSOLICITUDES 	Where   poliza =  'Poliza' AND RAMO = 'Ramo' AND SUCURSAL= 'Sucursal' AND ENDOSO = 'Endoso'");
				// Integer b= Integer.parseInt(dato);
				// System.out.println("consulta"+dato);
				 baseDatos.desconectar();
			 
		   }
	 
	 public static void consulta_poliza() {
		 Conexion baseDatos = new Conexion();
		 baseDatos.conectar();
		 baseDatos.ejecutarConsulta("Select S.POLIZA,S.IDESTADO, Ps.DSNOMBRE\r\n" + 
		 		"\r\n" + 
		 		"From IMSolicitudes S, MPoliper Pp, MPersona Ps\r\n" + 
		 		"\r\n" + 
		 		"Where Pp.NMPOLIZA = S.Poliza \r\n" + 
		 		"\r\n" + 
		 		"AND Pp.CDRAMO = S.RAMO\r\n" + 
		 		"\r\n" + 
		 		"AND Pp.CDUNIECO = S.SUCURSAL\r\n" + 
		 		"\r\n" + 
		 		"AND Pp.Estado = 'M'\r\n" + 
		 		"\r\n" + 
		 		"AND Pp.STATUS = 'V'\r\n" + 
		 		"\r\n" + 
		 		"AND Pp.CDROL = 1\r\n" + 
		 		"\r\n" + 
		 		"AND Pp.CDPERSON = Ps.CDPERSON\r\n" + 
		 		"\r\n" + 
		 		"order by S.POLIZA");
		 baseDatos.desconectar();
	 }

		 public static void consulta_detalle() {
			 Conexion baseDatos = new Conexion();
			 baseDatos.conectar();
			 baseDatos.ejecutarConsulta("Select Pp.NMPOLIZA, Pp.CDRAMO,Pp.CDROL, Ps.DSNOMBRE || ' ' || Ps.DSAPELLIDO\r\n" + 
			 		"|| ' ' || Ps.DSAPELLIDO1\r\n" + 
			 		"\r\n" + 
			 		"From IMSolicitudes S,  MPoliper Pp, MPersona Ps\r\n" + 
			 		"\r\n" + 
			 		"WHERE CDUNIECO = S.SUCURSAL \r\n" + 
			 		"\r\n" + 
			 		"AND Pp.CDRAMO = S.RAMO \r\n" + 
			 		"\r\n" + 
			 		"AND pP.NMPOLIZA = 30092\r\n" + 
			 		"\r\n" + 
			 		"AND Pp.ESTADO = 'M' \r\n" + 
			 		"\r\n" + 
			 		"AND Pp.CDROL = 2\r\n" + 
			 		"\r\n" + 
			 		"AND Pp.CDPERSON = Ps.CDPERSON");
			 baseDatos.desconectar();
	 }
	 
	 
}
