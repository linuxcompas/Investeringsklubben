package repositories;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/* CSV I/O
Her fortæller vi java - hvor kommer data fra?
 */

public class BondRepository {
    BufferedReader reader;
    BufferedWriter writer;

    public BondIO (String file) {

    }

    public void getBondTicker(){

    }

    public String read () throws IOException {
        return  reader.readLine();
    }

}
