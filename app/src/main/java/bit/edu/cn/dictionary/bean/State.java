package bit.edu.cn.dictionary.bean;

public enum State {

    NOTSAVE(0),SAVED(1);
    public final int intValue;

    State(int Value){
        this.intValue=Value;
    }
}
