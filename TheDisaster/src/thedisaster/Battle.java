package thedisaster;

import java.util.Random;
import static thedisaster.TheDisaster.*;

/**
 * バトルに関する処理を行うクラス
 *
 * @author 立野和紀 & 佐藤瑠星
 */
public class Battle {

    private static int turn = 1; //ターン
    private static int physicalInvalidConunt = 0;

    public static void BattleOfMessiah(Grim[] enemies) {
        int damage;

        for (Grim enemy : enemies) {
            while (enemy.getHPg() > 0) {
                System.out.println(enemy.getNameg() + "が現れた▼");
                scan.nextLine();

                System.out.println(enemy.getNameg() + "の攻撃");
                damage = Damage("ENEMY");
                Disaster.SetHP(-damage);

                System.out.println(Disaster.getName() + "に " + damage + " のダメージ▼ (残り : " + Disaster.getHP() + ")");
                scan.nextLine();

                /*Disasterの死亡確認が1秒ごとに行われる関係で、
                下のif文を実行すると2回DisasterDead()が呼ばれることがあります。
                 */
 /*
                if (Disaster.getHP() < 0) {
                    DisasterDead();
                }
                 */
                System.out.println(Disaster.getName() + "の行動");
                damage = Damage("DISASTER");
                enemy.SetHPg(-damage);

                System.out.println(enemy.getNameg() + "に " + damage + " のダメージ▼");
                scan.nextLine();
            }
            System.out.println(enemy.getNameg() + "は死亡した▼");
            scan.nextLine();
        }
    }

    public static void BattleOfGrimgerde() {
        String GrimAi;
        String disasterName = Disaster.getName();
        String grimgerdeName = grimgerde.getNameg();
        int damage = 0;

        while (grimgerde.getHPg() > 0) {
            System.out.println("ターン : " + turn);

            GrimAi = GRIMGERDEAI();
            System.out.println(grimgerdeName + "は " + GrimAi + " を行った▼");
            scan.nextLine();

            if (GrimAi.equals("物理無効")) {
                System.out.println(disasterName + "の攻撃");
                System.out.println("しかし、" + grimgerdeName + "は攻撃を弾いた▼");
                scan.nextLine();

                turn++;
                continue;
            }

            switch (GrimAi) {
                case "物理攻撃":
                    damage = Damage("GRIMGERDEPhysical");
                    break;
                case "魔法攻撃":
                    damage = Damage("GRIMGERDEMagic");
                    break;
            }
            
            Disaster.SetHP(-damage);
            
            System.out.println(disasterName + "に " + damage + " のダメージ▼  (残り : " + Disaster.getHP() + ")");
            scan.nextLine();

            System.out.println(disasterName + "の攻撃");
            damage = Damage("DISASTER");
            grimgerde.SetHPg(-damage);

            System.out.println(grimgerdeName + "に " + damage + " のダメージ▼");
            scan.nextLine();

            turn++;
        }
    }

    private static int Damage(String move) {
        Random rand = new Random();

        switch (move) {
            case "ENEMY":
                int ENEMYAttack = rand.nextInt(100);
                return ENEMYAttack;

            case "GRIMGERDEPhysical":
                int GRIMGERDEPhysical = rand.nextInt(100) + 200;
                return GRIMGERDEPhysical;

            case "GRIMGERDEMagic":
                int GRIMGERDEMagic = rand.nextInt(100) + 100;
                return GRIMGERDEMagic;

            case "DISASTER":
                int DISASTERAttack = rand.nextInt(7999) + 2000 + Disaster.getWepon().GetValue();
                return DISASTERAttack;
        }
        return -1;
    }

    public static String GRIMGERDEAI() {
        Random rand = new Random();
        int GrimRandom = rand.nextInt(99) + 1;

        if (turn == 1) {
            physicalInvalidConunt++;
            return "シールド";
        }

        switch (physicalInvalidConunt) {
            case 1:
                physicalInvalidConunt = 0;
                if (GrimRandom <= 50) {
                    return "物理攻撃";
                } else {
                    return "魔法攻撃";
                }
            default:
                if (GrimRandom <= 33) {
                    return "物理攻撃";
                } else if (GrimRandom >= 67) {
                    physicalInvalidConunt++;
                    return "";
                } else {
                    return "魔法攻撃";
                }
        }
    }
}
