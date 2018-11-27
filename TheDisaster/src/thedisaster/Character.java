package thedisaster;

/**
 * キャラクターの名前、体力、使用している武器を設定・返すクラス
 *
 * @author 山口雄也
 */
public class Character {

    //フィールド
    private String name;    //キャラクター名
    private int HP;      //体力
    private Item Wepon; //武器の名前

    //コンストラクタ
    public Character(String n, int h, Item w) {
        this.name = n;
        this.HP = h;
        this.Wepon = w;
    }

//フィールドnameを返すメソッド
    public String getName() {
        return name;
    }

    //フィールドHPを返すメソッド
    public int getHP() {
        return HP;
    }

    //フィールドweponを返すメソッド
    public Item getWepon() {
        return Wepon;
    }

    public void SetName(String n) {
        this.name = n;
    }

    //HPをセットするメソッド
    public void SetHP(int H) {
        this.HP += H;
    }

    //Weponをセットするメソッド
    public void SetWepon(Item W) {
        this.Wepon = W;
    }
}
