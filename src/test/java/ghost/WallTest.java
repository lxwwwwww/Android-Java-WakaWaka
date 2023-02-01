package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WallTest {
    @Test 
    public void constructor() {
        Wall wall=new Wall(200,300);
        assertNotNull(wall);
    }
}