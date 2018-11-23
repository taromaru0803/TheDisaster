package thedisaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * メインのファイル。
 */
public class TheDisaster {

    static String DisasterName;
    static Character Disaster;
    static Grim grimgerde;

    static List<Dungeon> dungeonList = new ArrayList<Dungeon>();

    static Item heal_low;
    static Item heal_medium;
    static Item heal_high;
    static Item heal_veryHigh;

    static Item wepon_knife;
    static Item wepon_toyKnife;
    static Item wepon_FryingPanOfMagic;
    static Item wepon_theElderWand;
    static Item wepon_muskets;
    static Item wepon_waterGun;

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        init(); //初期化
        Story.Openning(); //オープニング
        PutDisasterName(); //名前決定
        DamageOverTime(); //HP減少開始
        
        Story.Tutorial(); //チュートリアル
        Story.TutorialBattleAfter(); //チュートリアル戦後
        
        Story.choice1(); //選択1
        //分岐 810 or ステージ1
        
        Story.stage1(); //ステージ1
        Story.stage1After(); //ステージ1戦後
        //分岐 ステージ2 or choice2
        
        Story.choice3(); //choice3
        
        Story.stage3(); //ステージ3
        Story.stage3After(); //ステージ3戦後
        
        Story.security(); //イージマス前
        EasyMass(); //イージマス
        
        Story.lastBattle(); //ラスト前
        Story.lastBattleAfter(); //ラスト後
        
        Story.epilogue(); //エンディング
    }

    private static void init() {

        heal_low = new Item("小回復", 20);
        heal_medium = new Item("中回復", 40);
        heal_high = new Item("大回復", 60);
        heal_veryHigh = new Item("特大回復", 200);

        grimgerde = new Grim("Grimgerde", 10000);

        wepon_knife = new Item("ナイフ", 10);
        wepon_toyKnife = new Item("おもちゃのナイフ", 10);
        wepon_FryingPanOfMagic = new Item("魔法のフライパン", 10);
        wepon_theElderWand = new Item("ニワトコの杖", 10);
        wepon_muskets = new Item("マスケット銃", 10);
        wepon_waterGun = new Item("水鉄砲", 10);

        dungeonList.add(new Dungeon("チュートリアル", 2, "", "", ""));
        dungeonList.add(new Dungeon("選択1", 0, "", "", wepon_knife.GetName()));
        dungeonList.add(new Dungeon("810", 0, "", "", wepon_toyKnife.GetName()));
        dungeonList.add(new Dungeon("ステージ1", 2, "小", "", ""));
        dungeonList.add(new Dungeon("SP", 0, "", "中", "魔法のフライパン"));
        dungeonList.add(new Dungeon("ステージ2", 2, "中", "", "ニワトコの杖"));
        dungeonList.add(new Dungeon("選択2", 0, "", "大", "マスケット銃"));
        dungeonList.add(new Dungeon("ステージ3", 4, "特大", "", "水鉄砲"));
        dungeonList.add(new Dungeon("イージーマス", 0, "", "", ""));
        dungeonList.add(new Dungeon("ラスボス", 1, "", "", ""));

    }

    /**
     * Disasterの名前を決定するメソッド
     */
    public static void PutDisasterName() {
        boolean roop;
        do {
            roop = false;

            //netbeans環境では入力された日本語は文字化けする？みたいです。cmd上では正常表示されます
            System.out.println("災害(主人公)の名前を入力してください");
            DisasterName = scan.nextLine();

            System.out.println(DisasterName + " でよろしいですか? (y/n)");

            if (TwoChoices("y", "n").equals("n")) {
                roop = true;
            }
        } while (roop);
        Disaster = new Character(DisasterName, 2000, "なし");
    }

    public static void EasyMass() {
        System.out.println(""
                + "※簡単な数学の問題が5問表示されます\n"
                + "※回答を入力してください▼");
        scan.nextLine();

        System.out.println(""
                + "※回答を間違えると体力が減少します\n"
                + "※正解するまで問題が変わることはありません▼");
        scan.nextLine();

        System.out.println("START▼");

        Random rand = new Random();//ランダムメソッド
        int[] num = new int[2];//ランダム値保存用
        int ans = 0;//答え保存用
        int sisoku = 0;//四則種類選択用
        String question = null;//問題内容保存用
        char loop = 0;//繰り返し回数保存用
        char i;//繰り返し回数一時保存用
        do {//do/while文で３問繰り返す
            for (i = 0; i < 2; i++) {//２つの数値を保存
                int q = rand.nextInt(12);
                num[i] = q;
            }
            sisoku = rand.nextInt(3);//ランダムに＋ー×÷を選択
            switch (sisoku) {
                case 0:
                    ans = num[0] + num[1];
                    question = "+";
                    break;
                case 1:
                    ans = num[0] - num[1];
                    question = "-";
                    break;
                case 2:
                    ans = num[0] * num[1];
                    question = "×";
                    break;
            }
            question = "[第" + String.valueOf(loop + 1) + "問]" + String.valueOf(num[0]) + question + String.valueOf(num[1]) + "=";
            System.out.println(question);
            boolean correct = true;
            while (correct) {
                try {
                    String str = scan.nextLine();
                    if (str.equals(String.valueOf(ans))) {
                        System.out.println("正解");
                        correct = false;
                    } else {
                        System.out.println("不正解");
                        Disaster.SetHP(-10);
                    }
                } catch (Exception e) {
                }
            }
            loop++;
        } while (loop < 5);
        System.out.println("セキュリティーチェック完了");
        System.out.println("HP : " + Disaster.getHP());
    }

    /**
     * 武器の装備(確認)を行うメソッド
     *
     * @param wepon 武器
     */
    public static void EquipWeapon(Item wepon) {
        System.out.println(wepon.GetName() + "を装備しますか? (y/n) (現在の装備 : " + Disaster.getWepon() + ")");

        if (TwoChoices("y", "n").equals("y")) {
            System.out.println(wepon.GetName() + "を装備しました▼");
            Disaster.SetWepon(wepon.GetName());
        } else {
            System.out.println(wepon.GetName() + "を装備しませんでした▼");
        }
        scan.nextLine();
    }

    public static void Healer(Item healItem) {
        System.out.println("体力を回復しますか? (y/n) (現在の体力 : " + Disaster.getHP() + ")");

        if (TwoChoices("y", "n").equals("y")) {
            Disaster.SetHP(healItem.GetValue());

            System.out.println("体力を回復しました\n"
                    + "現在の体力 : " + Disaster.getHP() + " ▼");
        } else {
            System.out.println("体力を回復しませんでした▼");
        }
        scan.nextLine();
    }

    /**
     * 二択の選択結果を返すメソッド
     *
     * @param Choice1 選択肢1
     * @param Choice2 選択肢2
     * @return 選択結果
     */
    public static String TwoChoices(String Choice1, String Choice2) {
        boolean roop;
        do {
            roop = false;
            String input = scan.nextLine();

            if (input.toLowerCase().equals(Choice1)) {
                return Choice1;
            } else if (input.toLowerCase().equals(Choice2)) {
                return Choice2;
            } else {
                System.out.println(Choice1 + " か " + Choice2 + "のどちらかを半角で入力して下さい");
                roop = true;
            }
        } while (roop);
        return "0";
    }

    /**
     * 1秒ごとにDisasterのHPを減らすメソッド
     */
    public static void DamageOverTime() {
        Timer timer = new Timer(false);
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Disaster.SetHP(-1);
                //System.out.println("HP: " + Disaster.getHP());

                if (Disaster.getHP() <= 0) {
                    timer.cancel();
                    DisasterDead();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    public static void DisasterDead() {
        //scan.nextline()の処理の途中でscan.close()が入ると、nextlineの処理終了後にcloseするようです。
        //scan.close();

        System.out.println(DisasterName + "は死亡した");
        System.exit(0);
    }
}
