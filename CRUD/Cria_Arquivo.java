import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Cria_Arquivo{
	/*Método publico para poder ler os arquivos da 
	 * Base de dados e criar o arquivo .db, com base nas informações extraidas
	 */
  	public static void cria_db() throws FileNotFoundException {
		BufferedReader br;
		FileOutputStream fil;
		DataOutputStream dos;
		RandomAccessFile rnd;
		String linha = "";
		byte[] vet_byte;
		TableInfo tb = new TableInfo();
		
		try {
			/*BufferedReader->Lê o Csv 
			 *FileOutputStream->Auxiliar para criar o DataOutputStream
			  DataOutputStream->Escrita no arquivo (MODO DE CRIACAO)
			  RandomAcessFile-> seta o ultimo ID no cabecalho*/

			br = new BufferedReader(new FileReader("netflix_titles_2021.csv")); 
			fil = new FileOutputStream("netflix.db");
			dos = new DataOutputStream(fil);
			rnd = new RandomAccessFile("netflix.db", "rw");

			dos.writeInt(0);
			br.readLine();
			
			while ((linha = br.readLine()) != null){
				tb.setALL(linha.split(";"));
				vet_byte = tb.converte_bytearray();
				dos.writeInt(vet_byte.length);
				dos.write(vet_byte);
						
			}
			rnd.seek(0);
			rnd.writeInt(tb.ID);

			br.close();
			fil.close();
			dos.close();
			rnd.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
    
  	}
  
}