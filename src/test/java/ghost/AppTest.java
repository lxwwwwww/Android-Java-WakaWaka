package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test 
    public void constructor() {
        App app = new App();
        assertNotNull(app);
    }
}
