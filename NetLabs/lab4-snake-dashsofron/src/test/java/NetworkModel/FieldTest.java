package NetworkModel;

import GameModel.MyField;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {
    @Test
    public void correctFieldClassWorkTest(){
        MyField field=new MyField(5,5);
        Assert.assertNotEquals(java.util.Optional.ofNullable(field.getCell(-1, 0)),-1);
        Assert.assertNotEquals(java.util.Optional.ofNullable(field.getCell(5, 5)),-1);
        Assert.assertNotEquals(java.util.Optional.ofNullable(field.getCell(5, -1)),-1);

    }
}
