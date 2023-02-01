package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CollectionTest {
    @Test 
    public void constructor() {
        Collection normalfruit=new Collection(284,284,3,"normalfruit",false);
        assertNotNull(normalfruit);
    }
}