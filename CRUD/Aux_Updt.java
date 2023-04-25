import java.io.IOException;

public class Aux_Updt {
    public static int main(String[] elementos, int id) throws IOException{
        Update x = new Update();
        int z = x.upd(elementos, id);
        if (z==1){
            return 1;
        }
        else{
            return 0;
        }
    }
}
