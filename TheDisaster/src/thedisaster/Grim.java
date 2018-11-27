package thedisaster;

/**
 *
 * @author 山口雄也
 */
public class Grim {

    //フィールド
    private String nameg; //グリムゲルデの名前
    private int HPg;      //グリムゲルデの体力

    //コンストラクタ
    public Grim(String ng, int hg) {
        this.nameg = ng;
        this.HPg = hg;
    }

//フィールドnameを返すメソッド
    public String getNameg() {
        return nameg;
    }

    //フィールドHPを返すメソッド
    public int getHPg() {
        return HPg;
    }

    //HPをセットするメソッド
    public void SetHPg(int HPg) {
        this.HPg += HPg;
    }
}
