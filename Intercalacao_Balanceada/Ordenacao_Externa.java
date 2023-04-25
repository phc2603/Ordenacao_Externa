import java.io.RandomAccessFile;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ordenacao_Externa{
    /*Auxiliar indicador é um atributo que serve para saber quando iremos intercalar nos arquivos (1 ou 2) ou (3 ou 4), em relação aos blocos de registros */
    private int auxiliar_indicador=0;
    private int i;
    /*Atributos arq sao os caminhos; o RAF serve apenas para a intercalação incial, quando iremos dispor a base de dados em 2 arquivos iniciais */
    private RandomAccessFile arq1 = new RandomAccessFile("arq1.db", "rw");
    private RandomAccessFile arq2 = new RandomAccessFile("arq2.db", "rw");
    private RandomAccessFile arq3 = new RandomAccessFile("arq3.db", "rw");
    private RandomAccessFile arq4 = new RandomAccessFile("arq4.db", "rw");
    private RandomAccessFile raf = new RandomAccessFile("netflix.db", "r");
    /*Atributos com nome auxiliar ou aux servem para percorrer os arquivos e fazer as comaprações dos registros */
    private TableInfo auxiliar1;
    private TableInfo auxiliar2;
    private TableInfo aux3;
    private TableInfo aux4;
    /*Os atributos contadores servem para ter um parâmetro de quando um bloco de um registro chegou ao fim */
    private int contador_arq1;
    private int contador_arq2;
    private int contador_arq3;
    private int contador_arq4;
    private int cabecalho;

    
    public Ordenacao_Externa() throws IOException{

        raf.seek(0);
        cabecalho = raf.readInt();//pula o cabecalho
        TableInfo tb = new TableInfo();
        String[][] array_registro = new String[10][];//Matriz que conterá os registros (iremos salvar os registros como se fosse um vetor, dentro de outro vetor)
        while (true){
            try{
                for(i=1;i<11;i++){
                    tb.readall(raf);
                    if (tb.Lapide.compareTo("*")!=0){
                        array_registro[i-1] = tb.array_registros();
                    }
                    else{
                        i--;
                    }
                }

                /*Ordena e chama o metodo para escrever no arquivo */
                new Quick_Sort(array_registro, 0, 9);
                i--;
                escreve_ordenacao_inical(tb, array_registro);
                
                
            }catch(Exception e){
                i--;
                String[][] array_registro_auxiliar = new String[i][];//vetor auxiliar para ajustar o tamanho da ordenacao, quando acabar de ler o arquivo
                for(int j=0;j<i;j++){
                    array_registro_auxiliar[j] = array_registro[j];
                }
                
                new Quick_Sort(array_registro_auxiliar, 0, array_registro_auxiliar.length-1);
                
                //escrita final->será feita no arquivo 1 ou 2, dependendendo da ultima vez que foi feita a escrita
                escreve_ordenacao_inical(tb, array_registro_auxiliar);
                break;
            }
        }
        auxiliar_indicador = 0;
        if (arq1.length() == 0 || arq2.length() == 0){//caso um dos arquivos esteja vazio -> significa que a ordenação acabou
            System.out.println("Fim da intercalacao um dos arquivos esta vazio");
        }
        else{
            arq1.seek(0);
            arq2.seek(0);
            auxiliar1 = new TableInfo();
            auxiliar1.readall(arq1);
            auxiliar2 = new TableInfo();
            auxiliar2.readall(arq2);
            intercalacao(20);
        }
    }

    /*Método que fará a separação dos 2 arquivos iniciais e intercalar os registros iniciais de forma ordenada */
    private void escreve_ordenacao_inical(TableInfo tb, String[][] array_registro) throws IOException{
        byte[] novo_registro;
        /*Escreve em cada arquivo, de forma alternada, os registros, em forma de bytes, que foram preenchidos, anteriormente no array */
        if(auxiliar_indicador==1){
            for(int j=0;j<i;j++){
                tb.setALL(array_registro[j]);
                novo_registro = tb.converte_bytearray();
                arq2.writeInt(novo_registro.length);
                arq2.write(novo_registro);
            }
            auxiliar_indicador = 0;
            
        }
        else{
            for(int j=0;j<i;j++){
                tb.setALL(array_registro[j]);
                novo_registro = tb.converte_bytearray();
                arq1.writeInt(novo_registro.length);
                arq1.write(novo_registro);
            }
            auxiliar_indicador = 1;
        }
        
    }

    /*Faz a intercalacao (inicial e após finalizar a intercalação nos arquivos 3 e 4) para efetuar a ordenacao */
    private void intercalacao(int j) throws IOException{

        contador_arq1 = 0;
        contador_arq2 = 0;
        byte[] registro=null;
        try{
            for(i=0;i<j-1;i++){
                if (auxiliar1.ID<auxiliar2.ID){
                    registro = auxiliar1.converte_bytearray();
                    escreve_arq(registro);
                    auxiliar1.readall(arq1);
                    contador_arq1++;
                }
                else{
                    registro = auxiliar2.converte_bytearray();
                    escreve_arq(registro);
                    auxiliar2.readall(arq2);
                    contador_arq2++;
                }
                if (contador_arq1 == (j/2) || contador_arq2 == (j/2)){
                    escrita_final_arq(j, 1);
                    i=j;
                }
            }
            swap_aux_indicador();
            intercalacao(j);
        }catch(Exception e){
                
            termina_intercalacao(registro, 0);
            auxiliar_indicador = 0;
                
            arq1.close();
            arq2.close();   
            delete_arquivo(1);
                
            if (arq3.length() == 0 || arq4.length() == 0){
                System.out.println("Ordenado!");
                transcreve_arq(arq3);
            }
            else{
                arq3.seek(0);
                arq4.seek(0);
                    
                aux3 = new TableInfo();
                aux4 = new TableInfo();
                    
                arq1 = new RandomAccessFile("arq1.db", "rw");
                arq2 = new RandomAccessFile("arq2.db", "rw");

                aux3.readall(arq3);
                aux4.readall(arq4);
                intercala_arq3_arq4(j*2);//dobrando o tamanho total de cada segmento
            }
                
        }
    }

    /*Intercala com os arquivos 3 e 4 e escreve no arquivo 2 agora*/
    private void intercala_arq3_arq4(int j) throws IOException{
 
        contador_arq3 = 0;
        contador_arq4 = 0;
        byte[] registro=null;
        try{
            for (int i=0;i<j-1;i++){
                if (aux3.ID<aux4.ID){
                    registro = aux3.converte_bytearray();
                    escreve_arq_3_arq_4(registro);
                    aux3.readall(arq3);
                    contador_arq3++;
                }
                else{
                    registro = aux4.converte_bytearray();
                    escreve_arq_3_arq_4(registro);
                    aux4.readall(arq4);
                    contador_arq4++;
                }
                if (contador_arq3 == (j/2) || contador_arq4 == (j/2)){
                    escrita_final_arq(j, 0);
                    i=j;
                }
            }
            swap_aux_indicador();
            intercala_arq3_arq4(j);
        }catch(Exception e){
            termina_intercalacao(registro, 1);
            auxiliar_indicador = 0;
            arq3.close(); 
            arq4.close();
            delete_arquivo(-1);
            if(arq1.length() == 0 || arq2.length() == 0){
                System.out.println("Ordenado!");
                transcreve_arq(arq1);
            }
            else{
                arq1.seek(0);
                arq2.seek(0);
                    
                auxiliar1 = new TableInfo();
                auxiliar2 = new TableInfo();
                    
                arq3 = new RandomAccessFile("arq3.db", "rw");
                arq4 = new RandomAccessFile("arq4.db", "rw");

                auxiliar1.readall(arq1);
                auxiliar2.readall(arq2);
                intercalacao(j*2);//dobrando o tamanho total de cada segmento
            }      
        }
    }

    private void delete_arquivo(int aux){
        if (aux==1){
            File f1 = new File("arq1.db");
            File f2 = new File("arq2.db");
            if(f1.delete() && f2.delete()){
                System.out.println("DELETADO OS ARQUIVOS 1 E 2");
            }
            else{
                System.out.println("Deu ruim ao deletar isso");
            }
            
        }
        else{
            File f1 = new File("arq3.db");
            File f2 = new File("arq4.db");
            if(f1.delete() && f2.delete()){
                System.out.println("DELETADO OS ARQUIVOS 3 E 4");
            }
            else{
                System.out.println("Deu ruim ao deletar isso");
            }
        }
        
    }

    /*Quando um arquivo chega no EOF, precisamos terminar de preencher com o registro do outro arquivo 
     * Para isso, o método termina_intercalação é usado. Quando vef é 1, significa que estamos intercalando 
     * o arquivo 3 e 4, quando vef é 0 ou qualquer outro valor, significa que estamos intercalando nos arquivos 1 e 2
    Como o EOF irá ocorrer em ambas intercalações, o método acaba sendo bastante útil*/
    private void termina_intercalacao(byte[] registro, int vef) throws IOException{

        if(vef==1){
            if (contador_arq3 == contador_arq4){
                swap_aux_indicador();
            }
            long ponteiro1 = arq3.getFilePointer();
            long ponteiro2 = arq4.getFilePointer();
            /*ideia: verifica qual arquivo  chegou no EOF, usando o readline.
             * Depois, voltamos com o ponteiro e realizamos a operação
            */
            if (arq3.readLine() == null){
                registro = aux4.converte_bytearray();
                escreve_arq_3_arq_4(registro);
                while(arq4.readLine() != null){
                    arq4.seek(ponteiro2);
                    aux4.readall(arq4);
                    registro = aux4.converte_bytearray();
                    escreve_arq_3_arq_4(registro);
                    ponteiro2 = arq4.getFilePointer();
                }
            }
            else if(arq4.readLine() == null){
                arq4.seek(ponteiro1);
                registro = aux3.converte_bytearray();
                escreve_arq_3_arq_4(registro);
                while(arq3.readLine() != null){
                    arq3.seek(ponteiro1);
                    aux3.readall(arq3);
                    registro = aux3.converte_bytearray();
                    escreve_arq_3_arq_4(registro);
                    ponteiro1 = arq3.getFilePointer();
                }
            }
        }
        else{
            if (contador_arq1 == contador_arq2){
                swap_aux_indicador();
            }
            long ponteiro1 = arq1.getFilePointer();
            long ponteiro2 = arq2.getFilePointer();
            /*Verifica qual dos arquivos chegou ao fim e termina de fazer a intercalação, com os elementos que faltaram */
            if (arq1.readLine() == null){
                registro = auxiliar2.converte_bytearray();
                escreve_arq(registro);
                while(arq2.readLine() != null){
                    arq2.seek(ponteiro2);
                    auxiliar2.readall(arq2);
                    registro = auxiliar2.converte_bytearray();
                    escreve_arq(registro);
                    ponteiro2 = arq2.getFilePointer();
                }
            }
            else if(arq2.readLine() == null){
                arq1.seek(ponteiro1);
                registro = auxiliar1.converte_bytearray();
                escreve_arq(registro);
                while(arq1.readLine() != null){
                    arq1.seek(ponteiro1);
                    auxiliar1.readall(arq1);
                    registro = auxiliar1.converte_bytearray();
                    escreve_arq(registro);
                    ponteiro1 = arq1.getFilePointer();
                }
            }
        }
        
    }

    /*Método que serve para trocar o valor do indicador, quando finalizamos a 
    intercalação de 2 registros, em 1 arquivo */
    private void swap_aux_indicador(){
        if (auxiliar_indicador==1){
            auxiliar_indicador = 0;
        }
        else{
            auxiliar_indicador = 1;
        }
    }

    /*Método que escreve no arquivo 3 e 4, quando estamos fazendo a 
    intercalação no arquivo 1 e 2*/
    private void escreve_arq(byte[] registro) throws IOException{
        if(auxiliar_indicador == 0){
            arq3.writeInt(registro.length);
            arq3.write(registro);
        }
        else{
            arq4.writeInt(registro.length);
            arq4.write(registro);
        }
    }

    /*Método que escreve no arquivo 1 e 2, quando estamos 
    intercalando entre os arquivos 3 e 4 */
    private void escreve_arq_3_arq_4(byte[] registro) throws IOException{
        if (auxiliar_indicador == 0){
            arq1.writeInt(registro.length);
            arq1.write(registro);
        }
        else{
            arq2.writeInt(registro.length);
            arq2.write(registro);
        }
    }

    /*Método que termina de escrever o outro registro, quando chegamos no segmento final de 
    um registro */
    private void escrita_final_arq(int j, int verificador_arq) throws IOException{
        byte[] registro;
        if (verificador_arq == 1){
            if (contador_arq1>contador_arq2){
                while(contador_arq2!=(j/2)){
                    contador_arq2++;
                    registro = auxiliar2.converte_bytearray();
                    escreve_arq(registro);
                    auxiliar2.readall(arq2);
                }
            }
            else{
                while(contador_arq1!=(j/2)){
                    contador_arq1++;
                    registro = auxiliar1.converte_bytearray();
                    escreve_arq(registro);
                    auxiliar1.readall(arq1);
                }
            }
        }
        else{
            if (contador_arq3>contador_arq4){
                while(contador_arq4!=(j/2)){
                    contador_arq4++;
                    registro = aux4.converte_bytearray();
                    escreve_arq_3_arq_4(registro);
                    aux4.readall(arq4);
                }
            }
            else{
                while(contador_arq3!=(j/2)){
                    contador_arq3++;
                    registro = aux3.converte_bytearray();
                    escreve_arq_3_arq_4(registro);
                    aux3.readall(arq3);
                }
            }

        }
    }

    /*Método para reescrever o arquivo original, com base no caminho resultante */
    private void transcreve_arq(RandomAccessFile raf) throws IOException{
        FileOutputStream fil = new FileOutputStream("netflix.db");
        DataOutputStream dos = new DataOutputStream(fil);
        TableInfo tb = new TableInfo();
        byte[] registro;
        try{
            dos.writeInt(cabecalho);
            raf.seek(0);
            long ponteiro = raf.getFilePointer();
            while(raf.readLine() != null){
                raf.seek(ponteiro);
                tb.readall(raf);
                registro = tb.converte_bytearray();
                dos.writeInt(registro.length);
                dos.write(registro);
                ponteiro = raf.getFilePointer();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        fil.close();
        dos.close();
        raf.close();
    }
}

    



