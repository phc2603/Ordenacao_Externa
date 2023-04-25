import java.io.IOException;
import java.io.RandomAccessFile;

public class Read extends TableInfo{
    /*Procedimento que irá Procurar o ID, pulando de registro em registro, com base
     * em seu tamanho
     */
    public static String[] leitura(int id) throws IOException{
        RandomAccessFile arq;
        
        arq = new RandomAccessFile("netflix.db", "r");
        arq.seek(0);
        arq.readInt();//Pula a leitura do cabecalho
        
        String lapide, tipo, titulo, diretor, elenco,
        pais, data_ad, ano_lancado, avaliacao, duracao;
        while(true){
            int leitor_id;
            int tamanho_registro;
            long ponteiro_atual = arq.getFilePointer();
            

            try{
                tamanho_registro = arq.readInt();
                lapide = arq.readUTF();//lê a lápide
                leitor_id = arq.readInt();//lê o ID

                if (lapide.compareTo("*")!=0){

                    if (leitor_id==id){
                        System.out.println("ID: "+id);
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
                        data_ad = arq.readUTF();
                        System.out.println("Ano Adicionado: "+data_ad);
                        
                        ano_lancado = arq.readUTF();
                        System.out.println("Ano lancado: : "+ano_lancado);
                        avaliacao = arq.readUTF();
                        System.out.println("Avaliacao: "+avaliacao);
                        duracao = arq.readUTF();
                        System.out.println("Duracao: "+duracao);
                        arq.close();
                        String[] aux = {tipo,titulo,diretor,elenco,pais,
                            data_ad,ano_lancado,avaliacao,duracao};
                        return aux;
                    }
                }
                ponteiro_atual = arq.getFilePointer()-7;
                
                arq.seek(ponteiro_atual);//reseta a posicao do ponteiro para o primeiro indice do registro(logo apos o indicador de tamanho)
                arq.seek(ponteiro_atual+tamanho_registro);//coloca o ponteiro exatamente no indicador de tamanho do proximo registro

            }catch(Exception EOFException){
                arq.close();
                System.out.println("ID não encontrado");
                String[] aux = {"-1"};
                return aux;
            }
        }
    }

        
}

