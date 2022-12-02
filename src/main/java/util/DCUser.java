package util;

public class DCUser {
    public String id;
    public String name;
    public String nickname;
    public int currency;

    public DCUser(String id, String effectiveName, String nickname, int i) {
        this.id = id;
        this.name = effectiveName;
        this.nickname = nickname;
        this.currency = i;
    }
}
