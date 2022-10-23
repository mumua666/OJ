import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/** @author: Linux_Mumu */
public class Main {
    private static final Logger logger = Logger.getLogger("2022_10_23.Main");

    public static class Role {
        int attack, health;

        Role(int health, int attack) {
            this.health = health;
            this.attack = attack;
        }
    }

    public static void main(String[] args) throws Exception {
        logger.setUseParentHandlers(false);
        SimpleFormatter sf = new SimpleFormatter();
        logger.setLevel(Level.ALL);
        FileHandler fh = new FileHandler("./Log/jul.log");
        fh.setFormatter(sf);
        logger.addHandler(fh);
        String cmd;
        int n, attacker, defender, position, attack, health;
        boolean turnA = true;
        ArrayList<Role> listA = new ArrayList<>();
        ArrayList<Role> listB = new ArrayList<>();
        listA.add(new Role(30, 0));
        listB.add(new Role(30, 0));
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            cmd = sc.next();
            if ("summon".equals(cmd)) {
                position = sc.nextInt();
                attack = sc.nextInt();
                health = sc.nextInt();
                if (turnA) {
                    listA.add(position, new Role(health, attack));
                } else {
                    listB.add(position, new Role(health, attack));
                }
                logger.log(Level.INFO, "在第{0}次添加元素操作中实例化Role对象,health = {1}, attack = {2}",
                        new Object[] { i, health, attack });
            } else if ("attack".equals(cmd)) {
                attacker = sc.nextInt();
                defender = sc.nextInt();
                if (turnA) {
                    listA.get(attacker).health -= listB.get(defender).attack;
                    listB.get(defender).health -= listA.get(attacker).attack;
                    if (listA.get(attacker).health <= 0) {
                        listA.remove(attacker);
                    }
                    if (defender != 0 && listB.get(defender).health <= 0) {
                        listB.remove(defender);
                    }
                } else {
                    listB.get(attacker).health -= listA.get(defender).attack;
                    listA.get(defender).health -= listB.get(attacker).attack;
                    if (listB.get(attacker).health <= 0) {
                        listB.remove(attacker);
                    }
                    if (defender != 0 && listA.get(defender).health <= 0) {
                        listA.remove(defender);
                    }
                }
            } else {
                turnA = !turnA;
            }
        }
        if (listA.get(0).health > 0 && listB.get(0).health > 0) {
            System.out.println("0");
        } else {
            if (listA.get(0).health > 0) {
                System.out.println("1");
            } else {
                System.out.println("-1");
            }
        }
        System.out.println(listA.get(0).health);
        System.out.print(listA.size() - 1);
        for (int i = 1; i < listA.size(); i++) {
            System.out.print(" ");
            System.out.print(listA.get(i).health);
        }
        System.out.println();
        System.out.println(listB.get(0).health);
        System.out.print(listB.size() - 1);
        for (int i = 1; i < listB.size(); i++) {
            System.out.print(" ");
            System.out.print(listB.get(i).health);
        }
        System.out.println();
        sc.close();
    }
}