import  java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;


public class Create extends TableInfo{
    
    /*Construtor que irá receber por parâmetro os valores que o usuário quer entrar,
     * lê o último índice que foi usado, que está no cabecalho e escreve no arquivo
     * através do DataOutputStream, usando o modo APPEND
     */
    
    public Create(String[] vet_entradas) throws FileNotFoundException{
        
        RandomAccessFile leitor = new RandomAccessFile("netflix.db", "rw");
        
        FileOutputStream fout= new FileOutputStream("netflix.db", true);
        DataOutputStream dout = new DataOutputStream(fout);

        

        try{
            if(leitor.length() == 0){
                leitor.seek(0);
                leitor.writeInt(0);
            }
            byte[] vet_byte;
            leitor.seek(0);
            
            int id_atual = leitor.readInt() + 1;
            
            leitor.seek(0);
            leitor.writeInt(id_atual);//Atualiza no cabecalho o ID
            String x = Integer.toString(id_atual);
            String[] aux = new String[10];
            aux[0] = x;
            for(int i=1;i<10;i++){
                aux[i] = vet_entradas[i-1];
            }
            
            setALL(aux);
            vet_byte = converte_bytearray();//inserir os elementos dentro do vetor de bytes, como bytes
            dout.writeInt(vet_byte.length);//Escreve o tamanho do registro
            dout.write(vet_byte);//Escreve todo o registro, com indicador de tamanho
            dout.close();
            leitor.close();
            System.out.println("Arquivo criado com sucesso");
            
        }catch(Exception e){
            e.printStackTrace();
        }

        
    }
}



