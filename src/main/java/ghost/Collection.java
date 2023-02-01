package ghost;

public class Collection{
    int positionx;
    int positiony;
    int index;
    String type;
    Boolean contacted;

    public Collection(int positionx,int positiony,int index,String type,Boolean contacted){
        this.positionx=positionx;
        this.positiony=positiony;
        this.index=index;
        this.type=type;
        this.contacted=contacted;
    }
}