package thedisaster;

/**
 * 武器・回復アイテムの名前・値を設定・返すクラス
 *
 * @author 佐藤瑠星 & ザイダトゥル
 */
public class Item {

    private String name; //名前
    private int value; //値

    /**
     * コンストラクタ
     *
     * @param n 名前
     * @param v 値
     */
    public Item(String n, int v) {
        this.name = n;
        this.value = v;
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
