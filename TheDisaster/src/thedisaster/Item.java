package thedisaster;

/**
 * 武器・回復アイテムの名前・値を設定・返すクラス
 *
 * @author 佐藤瑠星 & ザイダトゥル
 */
public class Item {

    private String name; //名前
    private int value; //値
    private String AA; //AA

    /**
     * コンストラクタ
     *
     * @param n 名前
     * @param v 値
     * @param a AA
     */
    public Item(String n, int v,String a) {
        this.name = n;
        this.value = v;
        this.AA = a;
    }
    /**アスキーアートを返すメソッド
    */
    public String GetAA(){
        return AA;
    }
    /**
     * アイテムの名前を返すメソッド
     *
     * @return 名前
     */
    public String GetName() {
        return name;
    }

    /**
     * アイテムが保有する値を返すメソッド
     *
     * @return 値
     */
    public int GetValue() {
        return value;
    }

}
