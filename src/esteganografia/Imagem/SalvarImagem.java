package esteganografia.Imagem;

import java.awt.Color;
import java.awt.image.*;  
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
  
public class SalvarImagem {  

    public SalvarImagem(){
        
    } 
public void createJPG (int lar,int alt,ArrayList<String> newImage) {
	    try{
                //crio uma imagem com as medidas e do tipo int RGB
	        BufferedImage bi = new BufferedImage(lar, alt, BufferedImage.TYPE_INT_RGB);
	                       //cord que sera usada para percorrer o ARRAY
                                int cord=0;
                                //aqui comeco a desenhar a imagem
                for (int i = 0; i < lar; ++i) {
	        	for (int j = 0; j < alt; ++j) {
                            
                            int r=0,g=0,b=0;                                
                            
                            //converto de binario para decimal os valores do RGB armazenado no indice (cord) do ARRAY
                            r=Integer.parseInt(newImage.get(cord).substring(0, 8), 2);
                            g=Integer.parseInt(newImage.get(cord).substring(8, 16), 2);
                            b=Integer.parseInt(newImage.get(cord).substring(16, 24), 2);
                                //crio um Color com os valores do RGB para setar na nova imagem
                                Color c = new Color(r, g, b); 
                                //seto na nova imagem na referente coordenada x/y
	        		bi.setRGB(i, j, c.getRGB());                               
                                cord++;
                        
	        	}
	        }
                //aqui a imagem está pronta agora especifico o caminho 
	        File outputfile = new File("D:\\Users\\Allexandre\\Desktop\\Esteganografada.jpg");
                //Aqui a imagem será salva no formato e no local especificado em cima
                ImageIO.write(bi, "jpg", outputfile);
	    } catch (IOException e) {
	        System.out.println("Erro ao gravar imagem: "+e);
	    }
	}    
}  