import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.io.DataOutputStream;

public class Update extends TableInfo{
    private RandomAccessFile arq;
    private long ponteiro_atual; 
    private byte[] novo_registro;

    /*Construtor que irá fazer a procura do registro que tem o ID que o usuario quer mudar
     *Os novos campos do registro serão armazenados no atributo NOVO_REGISTRO, em forma de vetor de bytes 
     */
    /*public Update(String[] vet_entradas, int id) throws IOException{
        upd(vet_entradas, id);
    }*/

    public int upd(String[] vet_entradas, int id) throws IOException{
        arq = new RandomAccessFile("netflix.db", "rw");
        arq.seek(0);
        arq.readInt();
        String lapide;
        int leitor_id;
        int tamanho_registro;
        while(true){
            ponteiro_atual = arq.getFilePointer();
            try{
                tamanho_registro = arq.readInt();
                lapide = arq.readUTF();
                leitor_id = arq.readInt();
                if (lapide.compareTo("*")!=0){
                    if (leitor_id==id){
                        //CHAMADA O METODO PARA ATUALIZAR O REGISTRO
                        setALL(vet_entradas);
                        atualiza_registro(tamanho_registro, leitor_id);
                        return 1;
                    }
                }
                
                /*Reseta a posicao do ponteiro para logo após o tamanho do registro */
                ponteiro_atual = arq.getFilePointer()-7;
                arq.seek(ponteiro_atual);
                arq.seek(ponteiro_atual+tamanho_registro);


            }catch(Exception eOFException){
                System.out.println("Id pesquisado não foi encontrado");
                return 0;
            }
        }
    }

    private void atualiza_registro(int tamanho_registro, int leitor_id) throws IOException{
        
        FileOutputStream fout = new FileOutputStream("netflix.db", true);
        DataOutputStream dout = new DataOutputStream(fout);
        //registra os novos dados no vetor de bytes
        novo_registro = converte_bytearray();
        //Verifica e compara o tamanho desse registro que será substituido com o substituto
        if (novo_registro.length<=tamanho_registro){
            ponteiro_atual = arq.getFilePointer()-7;//-7, pois ele para logo após ler o ID, voltando 7 casas, volta para logo apos o tamanho do registro
            arq.seek(ponteiro_atual);
            arq.write(novo_registro);
        }
        else{

            //move o ponteiro para a posicao logo apos o indicador de tamanho do registro e escreve a lapide
            arq.seek(arq.getFilePointer()-7);
            arq.writeUTF("*");

            //escreve no arquivo, como append, sem precisar manipular o ponteiro, pois estamos usando DataOutputStream
            dout.writeInt(novo_registro.length);
            dout.write(novo_registro);
            
                
        }
        fout.close();
        dout.close();

    }
    
}
