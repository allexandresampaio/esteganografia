/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esteganografia.Imagem;

import esteganografia.Texto.Texto;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Redes
 */
public class Imagem {
     
    private int contadorTexto=0;
    private int parada=0;
    private ArrayList<String> bitTextoImg = new ArrayList<>();
    private String textoBinario;
    public Imagem(){
        
    }
    
    public void abrirImagem(String url,String texto ) throws FileNotFoundException, IOException{
      //trata a url pois o FileChooser traz com file:/ e nao precisa
        String urlTratada=tratarURL(url);
      textoBinario=texto;
        BufferedImage imagem;             
        try {
            //abro a imagem no url especificado 
            imagem = ImageIO.read(new File(urlTratada));
            //agora vou pegar os pixels da imagem
           pegarBits(imagem);  //  esta transformando e pegando o rgb em decimal de cada       
        } catch (IOException ex) {
            System.out.println("nao pegou a imagem "+ex);
        }    
    }
    
    public String tratarURL(String url){
        String saida="";
        for (int i = 6; i < url.length(); i++) {
            saida+=url.charAt(i);
        }       
        return saida;
    }
    
    public void pegarBits(BufferedImage imagem) throws IOException{
         
       
        Texto t = new Texto();       
        //primeiro pego a altura e largura
        int lar = imagem.getWidth();
        int alt = imagem.getHeight();   
        //se o texto excede o tamanho da imagem nao pode esconder
        if((lar*alt*3)<textoBinario.length()){
            JOptionPane.showMessageDialog(null, "Texto muito grande\n Imagem não Suporta a quantidade de texto!");
        }else{
            
         ColorModel model = ColorModel.getRGBdefault(); 
                
           for (int i = 0; i < lar; i++) {
               for (int j = 0; j <alt ; j++) {
                   //pego o RGB do pixel da coordenada x=i /y=j
                   int pixels = imagem.getRGB(i, j); 
            //retiro o R o G e o B do pixel
            int r = model.getRed(pixels);  
            int g = model.getGreen(pixels);  
            int b = model.getBlue(pixels);       
                 //converto para binario o pixel
            String redBinario=t.decToBin(r);
            String greenBinario=t.decToBin(g);
            String blueBinario=t.decToBin(b);

            //agora vou esconder mando o RGB em binario e o texto em binario
            esconderTexto(redBinario,greenBinario,blueBinario,textoBinario);
                   
               }           
        }
           //agora vou salvar a imagem nova
           SalvarImagem nova = new SalvarImagem();
           nova.createJPG(lar, alt, bitTextoImg);
           JOptionPane.showMessageDialog(null, "Texto escondido!");
        }
}        
     
    public void esconderTexto(String red, String green, String blue,String texto){
        
        String r="";
        String g="";
        String b="";       
        parada+=3; 
      
        //se parada < que o tamanho do texto é porque ainda nao sobra
        //nenhum RGB todo RGB do pixel pode ser preenchido por um bit do texto
        if(parada<texto.length()){
          int i = contadorTexto;
                         
                //pego os 7 bit e deixo o menos significante
            r+=red.substring(0,7);
            g+=green.substring(0, 7);
            b+=blue.substring(0, 7);
                    
            //adiciono os tres primeiros bits do texto respectivamente no RGB com 7 bit
            r+=texto.charAt(i);
            g+=texto.charAt(i+1);
            b+=texto.charAt(i+2);
            
            //adiciono em um ARRAY pra melhor tratar depois
            bitTextoImg.add(r+g+b);     
              
        contadorTexto+=3;
      }
            // Se for == 1 ele tem dois bits sobrando/ == 2 ele tem um bit sobrando
        else if ((parada-texto.length()) == 1) {            
            
            
                r="";
                g="";
            
                r+=red.substring(0, 7);
                g+=green.substring(0, 7);
                           
                r+=texto.charAt(texto.length()-2);
                g+=texto.charAt(texto.length()-1);
            
                bitTextoImg.add(r+g+blue);
                
            }
         // Se for == 1 ele tem dois bits sobrando/ == 2 ele tem um bit sobrando
           else if((parada-texto.length()) == 2 ){
                r="";
              
                r+=green.substring(0, 7);
                
                r+=texto.charAt(texto.length()-1);
            
            bitTextoImg.add(r+green+blue);
            }
           //se não for nenhum deles apenas adiciono o restante de RGB da propria imagem
           else{
               bitTextoImg.add(red+green+blue);
          }         
    }    
}
