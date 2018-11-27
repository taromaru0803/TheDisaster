package thedisaster;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * メインのファイル
 *
 * @author チーム16全員 (主に立野が担当)
 */
public class TheDisaster {

    static String DisasterName;
    static Character Disaster;
    static Grim grimgerde;

    static Grim enemy_tutorial_1;
    static Grim enemy_tutorial_2;
    static Grim[] enemies_tutorial = new Grim[2];
    static Grim enemy_stage1_1;
    static Grim enemy_stage1_2;
    static Grim[] enemies_stage1 = new Grim[2];
    static Grim enemy_stage2_1;
    static Grim enemy_stage2_2;
    static Grim[] enemies_stage2 = new Grim[2];
    static Grim enemy_stage3_1;
    static Grim enemy_stage3_2;
    static Grim enemy_stage3_3;
    static Grim enemy_stage3_4;
    static Grim[] enemies_stage3 = new Grim[4];

    static Item heal_low;
    static Item heal_medium;
    static Item heal_high;
    static Item heal_veryHigh;

    static Item weapon_bareHands;
    static Item weapon_knife;
    static Item weapon_toyKnife;
    static Item weapon_fryingPanOfMagic;
    static Item weapon_theElderWand;
    static Item weapon_muskets;
    static Item weapon_waterGun;

    static boolean hasGotWeapon_toyKnife = false;
    static boolean hasGotWeapon_fryingPanOfMagic = false;
    static boolean hasGotWeapon_waterGun = false;
    static boolean hasGotWeapon_killWeapon = false;

    static Scanner scan = new Scanner(System.in);

    static boolean isGameEnd = false;

    /**
     * メインメソッド。全体の流れはここに記述されています。
     *
     * @param args
     */
    public static void main(String[] args) {
        init(); //初期化

        Story.Openning(); //オープニング
        PutDisasterName(); //名前決定
        DamageOverTime(); //HP減少開始

        Story.Tutorial(); //チュートリアル
        Battle.BattleOfMessiah(enemies_tutorial); //チュートリアル戦
        Story.TutorialBattleAfter(); //チュートリアル戦後

        Story.choice1(); //選択1

        //分岐 810 or ステージ1
        if (TwoChoices("1", "2").equals("1")) {
            EquipWeapon(weapon_knife);
        } else {
            Healer(heal_low);
            Story.specialenpai();
        }

        Story.stage1(); //ステージ1
        Battle.BattleOfMessiah(enemies_stage1); //ステージ1戦
        Story.stage1After(); //ステージ1戦後

        //分岐 ステージ2 or choice2
        if (TwoChoices("1", "2").equals("1")) {
            Story.stage2();
            Battle.BattleOfMessiah(enemies_stage2);
            Story.stage2After();
        } else {
            Story.choice2();
        }

        Story.choice3(); //choice3

        Story.stage3(); //ステージ3
        Battle.BattleOfMessiah(enemies_stage3); //ステージ3戦
        Story.stage3After(); //ステージ3戦後

        Story.security(); //イージマス前
        EasyMass(); //イージマス

        Story.lastBattle(); //ラスト前
        if (hasGotWeapon_toyKnife
                && hasGotWeapon_fryingPanOfMagic
                && hasGotWeapon_waterGun
                && !hasGotWeapon_killWeapon) {
            System.out.println("1.戦う , 2. 自分の命を絶つ");
            if (TwoChoices("1", "2").equals("2")) {
                Story.specialVictory();
                isGameEnd = true;
            }
        }
        Battle.BattleOfGrimgerde();
        Story.lastBattleAfter(); //ラスト後

        Story.epilogue(); //エンディング

        isGameEnd = true;
    }

    /**
     * 初期化
     */
    private static void init() {
        heal_low = new Item("小回復", 20);
        heal_medium = new Item("中回復", 40);
        heal_high = new Item("大回復", 60);
        heal_veryHigh = new Item("特大回復", 200);

        weapon_bareHands = new Item("素手", 0);
        weapon_knife = new Item("ナイフ", 10);
        weapon_toyKnife = new Item("おもちゃのナイフ", 10);
        weapon_fryingPanOfMagic = new Item("魔法のフライパン", 10);
        weapon_theElderWand = new Item("ニワトコの杖", 10);
        weapon_muskets = new Item("マスケット銃", 10);
        weapon_waterGun = new Item("水鉄砲", 10);

        Disaster = new Character("", 1500, weapon_bareHands);
        grimgerde = new Grim("Grimgerde", 10000);

        enemy_tutorial_1 = new Grim("メサイア一般兵A", 100);
        enemy_tutorial_2 = new Grim("メサイア一般兵B", 100);
        enemies_tutorial[0] = enemy_tutorial_1;
        enemies_tutorial[1] = enemy_tutorial_2;
        enemy_stage1_1 = new Grim("エクスシア", 250);
        enemy_stage1_2 = new Grim("スローネ", 250);
        enemies_stage1[0] = enemy_stage1_1;
        enemies_stage1[1] = enemy_stage1_2;
        enemy_stage2_1 = new Grim("マールート", 500);
        enemy_stage2_2 = new Grim("ハールート", 500);
        enemies_stage2[0] = enemy_stage2_1;
        enemies_stage2[1] = enemy_stage2_2;
        enemy_stage3_1 = new Grim("ラジエル", 1000);
        enemy_stage3_2 = new Grim("ヴァーチュース", 1000);
        enemy_stage3_3 = new Grim("ガルム", 1000);
        enemy_stage3_4 = new Grim("アストライアー", 2000);
        enemies_stage3[0] = enemy_stage3_1;
        enemies_stage3[1] = enemy_stage3_2;
        enemies_stage3[2] = enemy_stage3_3;
        enemies_stage3[3] = enemy_stage3_4;
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
        Disaster.SetName(DisasterName);
    }

    /**
     * イージーマス
     */
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
        do {//do/while文で5問繰り返す
            for (int i = 0; i < 2; i++) {//２つの数値を保存
                int q = rand.nextInt(13);
                num[i] = q;
            }
            sisoku = rand.nextInt(3);//ランダムに＋ー×を選択
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
        String weponName = wepon.GetName();

        System.out.println(weponName + "を装備しますか? (y/n) (現在の装備 : " + Disaster.getWepon().GetName() + ")");

        if (TwoChoices("y", "n").equals("y")) {
            System.out.println(weponName + "を装備しました▼");
            Disaster.SetWepon(wepon);

            if (!hasGotWeapon_killWeapon) {
                if (weponName.equals(weapon_toyKnife.GetName())) {
                    hasGotWeapon_toyKnife = true;
                } else if (weponName.equals(weapon_fryingPanOfMagic.GetName())) {
                    hasGotWeapon_fryingPanOfMagic = true;
                } else if (weponName.equals(weapon_waterGun.GetName())) {
                    hasGotWeapon_waterGun = true;
                } else {
                    hasGotWeapon_killWeapon = true;
                }
            }
            
        } else {
            System.out.println(wepon.GetName() + "を装備しませんでした▼");
        }
        
        scan.nextLine();
    }

    /**
     * 回復の実行(確認)をするメソッド
     *
     * @param healItem 回復アイテム
     */
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

                if (isGameEnd) {
                    timer.cancel();
                    System.exit(0);
                }

                if (Disaster.getHP() <= 0) {
                    timer.cancel();
                    DisasterDead();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    /**
     * Disasterの死亡処理を行うメソッド
     */
    public static void DisasterDead() {
        //scan.nextline()の処理の途中でscan.close()が入ると、nextlineの処理終了後にcloseするようです。
        //scan.close();

        System.out.println(DisasterName + "は死亡した");
        System.exit(0);
    }
}
