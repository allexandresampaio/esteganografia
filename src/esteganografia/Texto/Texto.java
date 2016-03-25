/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esteganografia.Texto;

/**
 *
 * @author Redes
 */
public class Texto {
    
    public Texto(){
        
    }
    //converte o texto em bits
    public String bitsTexto(String texto){
         byte[] bytes = texto.getBytes();  
        String[] binarios = new String[bytes.length];  
        String saida=""; 
        int cont=0;
        String zero="";        
        // jogando para um array de string  
        for (int i=0; i < bytes.length; i++) {  
            binarios[i] = Integer.toBinaryString(bytes[i]); 
          
            cont=binarios[i].length();
            if (cont<8) {
             while(cont<8){
              zero+="0";
              cont++;
              }
             binarios[i]=zero+""+binarios[i];
             zero="";
        }  
            saida+=binarios[i];
        }     
        
        
        
        return saida;
}

     //converte de decimal para binario
      public String decToBin(int num){
          String binario;
          String zero="";
          int cont;
     
          String binaria = Integer.toBinaryString(num);
          cont=binaria.length();
          if (cont<8) {
              while(cont<8){
              zero+="0";
              cont++;
              }               
            binario=zero+""+binaria;
          }
          else
              binario=binaria;
          
          return binario;
  }  

} 

