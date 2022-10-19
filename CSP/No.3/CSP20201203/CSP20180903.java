import java.util.Scanner;

/** @author : Linux_Mumu */
public class Main {
    public static class Tags {
        String tagName, idName = "#";
        int level, row;

        Tags(String tagName, int level, int row) {
            this.tagName = tagName;
            this.level = level;
            this.row = row;
        }
    }

    public static void main(String[] args) {
        int n, m, level;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        String inputStr, checkStr;
        Tags[] css = new Tags[n];
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            inputStr = sc.nextLine();
            String[] strArray = inputStr.split("[.]| ");
            level = (inputStr.split("[.]").length - 1) / 2;
            for (String item : strArray) {
                if (!"".equals(item) && !item.contains("#")) {
                    css[i] = new Tags(item, level, i + 1);
                }
                if (item.contains("#")) {
                    css[i].idName = item;
                }
            }
        }
        int count[] = new int[n];
        for (int i = 0; i < m; i++) {
            checkStr = sc.nextLine();
            String[] strArray = checkStr.split(" ");
            int index = 0, deepLevel = 0, cnt = 0, saveRow = 0, len = strArray.length, oldLevel = 0;
            boolean flag = false;
            String itemStr = strArray[0];
            if (len > 1) {
                for (Tags itemTags : css) {
                    if (itemTags.row < deepLevel) {
                        index = 0;
                        itemStr = strArray[0];
                        deepLevel = 0;
                    }
                    if (itemTags.row == deepLevel) {
                        itemTags.tagName = strArray[index - 1];
                        index--;
                        itemStr = strArray[index];
                    }
                    flag = itemTags.tagName.equalsIgnoreCase(itemStr) || itemTags.idName.equals(itemStr);
                    if (flag) {
                        saveRow = itemTags.row;
                        oldLevel = deepLevel;
                        deepLevel = itemTags.level;
                        if (index < len - 1) {
                            itemStr = strArray[++index];
                        } else {
                            index++;
                        }
                    }
                    if (index == len) {
                        count[cnt++] = saveRow;
                        index--;
                        deepLevel = oldLevel;
                        itemStr = strArray[index];
                    }
                }
            } else {
                for (Tags itemTags : css) {
                    if (itemTags.tagName.equalsIgnoreCase(itemStr) || itemTags.idName.equals(itemStr)) {
                        count[cnt++] = itemTags.row;
                    }
                }
            }
            System.out.print(cnt);
            System.out.print(" ");
            for (int j = 0; j < cnt; j++) {
                System.out.print(count[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        sc.close();
    }
}