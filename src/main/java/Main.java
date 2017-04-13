/**
 * Created by michaelberber on 4/11/17.
 */

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get('/hello', (req, res) -> 'Hello world');
    }
}





