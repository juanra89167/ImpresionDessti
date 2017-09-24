package com.mave.ws.rest.vo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.mave.ws.utils.Parametros;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class funciones {
	
	  public void verContenidoFolder(File dir, String namefile,String nameplantilla,String Correo) {
		
		  Parametros p= new Parametros();
	        try {
	            File[] files = dir.listFiles();
	            for (File file : files) {
	                if (file.isDirectory()  ) {
	                    System.out.println("directorio:" + file.getCanonicalPath());
	                    verContenidoFolder(file,namefile, nameplantilla, Correo);
	                } else {
	                //	 System.out.println("directorio:" + file.getName()+namefile);
	                    if (file.getName().equals(namefile+".xml")) {   
	                 //   p.setPathfile(file.getName().toString());
	                //    p.setNombrefile(file.getParent().toString());/// te da ell directorio si en nombe del archivo
	                   System.out.println("perro"+file.getParent());
	                   	genera_pdf (file.getCanonicalPath(),namefile, nameplantilla, Correo, file.getParent());
	                   	break;
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	  
	    }	
	  
	  public static void genera_pdf (String path, String nombre_pdf, String nombre_plantilla, String Correo, String phatlocpdf) {
			try {
				 List<ArrayList<String>> a = new ArrayList<>();
				  ArrayList<String> a1 = new ArrayList<String>();
			        ArrayList<String> a2 = new ArrayList<String>();
			        a=leerxml(phatlocpdf, nombre_pdf);
					a1 =a.get(0);
					a2= a.get(1);
					
			JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\DessTI\\JaspersoftWorkspace\\MyReports\\"+nombre_plantilla+".jasper");
			Map parametros  = new HashMap();
			for(int i=0; i<a1.size(); i++) {
				parametros.put(a1.get(i),a2.get(i));
				System.out.println(i+a1.get(i));
			}
			JasperPrint JasperPrint = JasperFillManager.fillReport(reporte,parametros, new JREmptyDataSource());
			JRExporter exporter = new JRPdfExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, JasperPrint);
	        System.out.println("antes de generar pdf");
	        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, phatlocpdf+"\\"+nombre_pdf+".pdf");
	        exporter.exportReport();
	        System.out.println("genero pdf");
	       
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			envia_correo(Correo, nombre_pdf+".pdf", phatlocpdf);
			
		}  
	
	
	  public static List<ArrayList<String>> leerxml(String pathxml, String nombrexml) {
		
			 //Se crea un SAXBuilder para poder parsear el archivo
		  List<ArrayList<String>> a = new ArrayList<>();
		  ArrayList<String> a1 = new ArrayList<String>();
	        ArrayList<String> a2 = new ArrayList<String>();
		    SAXBuilder builder = new SAXBuilder();
		    File xmlFile = new File(pathxml+"\\"+nombrexml+".xml");
		
		    try
		    {
		        //Se crea el documento a traves del archivo
		        Document document = (Document) builder.build( xmlFile );
		 
		        //Se obtiene la raiz 'tables'
		        Element rootNode = document.getRootElement();
		 
		        //Se obtiene la lista de hijos de la raiz 'tables'
		        List list = rootNode.getChildren("Campos");
		 
		        //Se recorre la lista de hijos de 'tables'
		        for ( int i = 0; i < list.size(); i++ )
		        {
		            //Se obtiene el elemento 'tabla'
		            Element tabla = (Element) list.get(i);
		 
		            //Se obtiene el atributo 'nombre' que esta en el tag 'tabla'
		            String nombreTabla = tabla.getAttributeValue("name");
		 
		            System.out.println( "Tabla: " + nombreTabla );
		 
		            //Se obtiene la lista de hijos del tag 'tabla'
		            List lista_campos = tabla.getChildren();
		 
		           // System.out.println( "\t\tTipo\t\tValor" );
		 
		            //Se recorre la lista de campos
		            
		            for ( int j = 0; j < lista_campos.size(); j++ )
		            {
		                //Se obtiene el elemento 'campo'
		                Element campo = (Element)lista_campos.get( j );
		         
		                //Se obtienen los valores que estan entre los tags '<campo></campo>'
		                //Se obtiene el valor que esta entre los tags '<nombre></nombre>'
		              
		 
		                //Se obtiene el valor que esta entre los tags '<tipo></tipo>'
		                String tipo = campo.getName();
		                a1.add(campo.getName());
		                a2.add(campo.getChildTextTrim("Value"));
		                a.add(a1);
		                a.add(a2);
		                //Se obtiene el valor que esta entre los tags '<valor></valor>'
		                String valor = campo.getChildTextTrim("Value");
		 
		              //  System.out.println( "\t\t"+tipo+"\t\t"+valor);
		            }
		        }
		       
		    }catch ( IOException io ) {
		        System.out.println( io.getMessage() );
		    }catch ( JDOMException jdomex ) {
		        System.out.println( jdomex.getMessage() );
		    }
			return a;
		}
		
	
	
	
	  public static void envia_correo(String Correo, String nombre_pdf, String locpdf)
	    {
	        try
	        {
	          // se obtiene el objeto Session. La configuración es para
	          // una cuenta de gmail.
	            Properties props = new Properties();
	            props.put("mail.smtp.host", "smtp.gmail.com");
	            props.setProperty("mail.smtp.starttls.enable", "true");
	            props.setProperty("mail.smtp.port", "587");
	            props.setProperty("mail.smtp.user", "juanra.89167@gmail.com");
	            props.setProperty("mail.smtp.auth", "true");

	            Session session = Session.getDefaultInstance(props, null);
	            // session.setDebug(true);
	            	
	            // Se compone la parte del texto
	            BodyPart texto = new MimeBodyPart();
	            texto.setText("Texto del mensaje");
	            System.out.println("send to mail"+nombre_pdf);
	            // Se compone el adjunto con la imagen
	            BodyPart adjunto = new MimeBodyPart();
	            adjunto.setDataHandler(
	                new DataHandler(new FileDataSource(locpdf+"\\"+nombre_pdf)));
	            adjunto.setFileName(nombre_pdf);

	            // Una MultiParte para agrupar texto e imagen.
	            MimeMultipart multiParte = new MimeMultipart();
	            multiParte.addBodyPart(texto);
	            multiParte.addBodyPart(adjunto);

	            // Se compone el correo, dando to, from, subject y el
	            // contenido.
	            MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("juanr.89167@gmail.com"));
	            message.addRecipient(
	                Message.RecipientType.TO,
	                new InternetAddress(Correo)); //correo al que sera enviado el archivo
	            message.setSubject("Hola");
	            message.setContent(multiParte);

	            // Se envia el correo.
	            Transport t = session.getTransport("smtp");
	            t.connect("juanra.89167@gmail.com", "for411bin398");
	            t.sendMessage(message, message.getAllRecipients());
	            t.close();
	        } 
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	  
}
