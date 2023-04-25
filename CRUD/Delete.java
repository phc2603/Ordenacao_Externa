import java.io.IOException;
import java.io.RandomAccessFile;

public class Delete extends TableInfo{

    /*Construtor que instancia o RAF que irá efetuar a procura do ID, bem como 
      preencher a lápide e Mostrar o Registro que foi apagado
    */
    public static String[] deleta(int id) throws IOException{
        RandomAccessFile arq = new RandomAccessFile("netflix.db", "rw");
        arq.seek(0);
        arq.readInt();//Pula a leitura do cabecalho
        while (true){
            try{
                int leitor_id;
                int tamanho_registro;
                long ponteiro_atual = arq.getFilePointer();
                tamanho_registro = arq.readInt();
                String Lapide = arq.readUTF();//verifica a lápide, pois as vezes o arquivo ja pode ter sido apagado
                leitor_id = arq.readInt();//lê o ID

                if (Lapide.compareTo("*")!=0){
                    if (leitor_id==id){
                        ponteiro_atual = arq.getFilePointer();
                        arq.seek(ponteiro_atual-7);
                        arq.writeUTF("*");
                        
                        System.out.println("Registro apagado com sucesso!");
                        System.out.println("Regristo apagado: ");
                        System.out.println("ID: "+arq.readInt());
                        String tipo,titulo,diretor,elenco,pais,ano_ad,ano_lancado,avaliacao,duracao;
                        
                        tipo = arq.readUTF();
                        System.out.println("Tipo: "+tipo);
                        titulo = arq.readUTF();
                        System.out.println("Titulo: "+titulo);
                        diretor = arq.readUTF();
                        System.out.println("Diretor: "+diretor);
                        elenco = arq.readUTF();
                        System.out.println("Elenco: "+elenco);
                        pais = arq.readUTF();
                        
                        System.out.println("Pais: "+pais);
                        ano_ad = arq.readUTF();
                        System.out.println("Ano Adicionado: "+ano_ad);
                        ano_lancado = arq.readUTF();
                        System.out.println("Ano lancado: : "+ano_lancado);
                        avaliacao = arq.readUTF();
                        System.out.println("Avaliacao: "+avaliacao);
                        duracao = arq.readUTF();
                        System.out.println("Duracao: "+duracao);
                        String[] aux = {tipo,titulo,diretor,elenco,pais,ano_ad,ano_lancado,avaliacao,duracao};
                        return aux;
                    }
                }
                
                ponteiro_atual = arq.getFilePointer()-7;
                arq.seek(ponteiro_atual);
                arq.seek(ponteiro_atual+tamanho_registro);

            }catch(Exception EOFException){
                arq.close();
                System.out.println("Nao existem registros com esse ID para serem apagados.");
                String[] aux = {"-1"};
                return aux;
            }
        }
    }
}
