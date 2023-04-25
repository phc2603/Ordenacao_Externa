/*Método quickSort para ordenar o vetor que têm os registros*/

public class Quick_Sort {
    public String[][] parametros;
    
    public Quick_Sort(String[][] param, int inicio, int fim){
        parametros = param;
        quick_sort(inicio, fim);
    }

    private String[][] quick_sort(int inicio, int fim){
        if (inicio<fim){
            int particao = partition(inicio, fim);
            quick_sort(inicio, particao-1);
            quick_sort(particao+1, fim);
        }
        return parametros;

    } 

    private int partition(int inicio, int fim){
        int pivot = Integer.parseInt(parametros[fim][0]);
        int i = inicio;
        for (int j=inicio;j<fim;j++){
            if (Integer.parseInt(parametros[j][0])<=pivot){
                String[] aux = parametros[j];
                parametros[j] = parametros[i];
                parametros[i] = aux;
                i++;
            }
        }
        String[] aux2 = parametros[fim];
        parametros[fim] = parametros[i];
        parametros[i] = aux2;
        return i;

    }

}
