import java.util.ArrayList;
import java.util.Scanner;

/** @author : 朱晓隆 2021150174 */
public class Exp20201203 {
    public static boolean check(int sizeA, int sizeB) {
        return sizeA > sizeB;
    }

    public static class Path {
        String name;
        int sizeSelf = 0, sizeSon = 0, sizeOffSpring = 0, sizeSonMax = 0, sizeOffSpringMax = 0;
        boolean isFile = true;
        ArrayList<Path> listPath = new ArrayList<>();

        Path(String name, int sizeSelf) {
            this.name = name;
            this.sizeSelf = sizeSelf;
        }

        Path(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
        }

        void set(int sizeSonMax, int sizeOffSpringMax) {
            this.sizeSonMax = sizeSonMax;
            this.sizeOffSpringMax = sizeOffSpringMax;
        }

        boolean isFilePath(String[] strArray) {
            Path temp = this;
            for (String itemArrsy : strArray) {
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArrsy)) {
                        temp = itemPath;
                        if (temp.isFile && temp.name.equals(strArray[strArray.length - 1])) {
                            return true;
                        }
                        break;
                    }
                }
            }
            return false;
        }

        boolean canDo(String[] strArray, int size) {
            Path temp = this;
            boolean exits = false;
            boolean flag;
            int len = strArray.length;
            String itemArray;
            for (int i = 0; i < len; i++) {
                itemArray = strArray[i];
                exits = false;
                if (i == len - 1) {
                    flag = temp.sizeSon + size > temp.sizeSonMax && temp.sizeSonMax != 0
                            || temp.sizeOffSpring + size > temp.sizeOffSpringMax && temp.sizeOffSpringMax != 0;
                } else {
                    flag = temp.sizeOffSpring + size > temp.sizeOffSpringMax && temp.sizeOffSpringMax != 0;
                }
                if (flag) {
                    return false;
                }
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArray)) {

                        exits = true;
                        if (itemPath.isFile ^ i == len - 1) {
                            return false;
                        }
                        temp = itemPath;
                        break;
                    }
                }
                if (!exits) {
                    return true;
                }
            }
            return !exits;
        }

        void saveFile(String[] strArray, int size) {
            Path temp = this;
            boolean exits = false;
            for (String itemArray : strArray) {
                exits = false;
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArray)) {
                        temp.sizeOffSpring += size;
                        temp = itemPath;
                        exits = true;
                        break;
                    }
                }
                if (!exits && itemArray != strArray[strArray.length - 1]) {
                    temp.listPath.add(new Path(itemArray, false));
                    temp.sizeOffSpring += size;
                    for (Path itemPath : temp.listPath) {
                        if (itemPath.name.equals(itemArray)) {
                            temp = itemPath;
                            break;
                        }
                    }
                }
            }
            temp.listPath.add(new Path(strArray[strArray.length - 1], size));
            temp.sizeOffSpring += size;
            temp.sizeSon += size;
            for (Path itemPath : temp.listPath) {
                if (itemPath.name.equals(strArray[strArray.length - 1])) {
                    itemPath.sizeSelf = size;
                    break;
                }
            }
        }

        boolean findPath(String[] strArray) {
            Path temp = this;
            boolean exits = false;
            for (String itemArrsy : strArray) {
                exits = false;
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArrsy)) {
                        exits = true;
                        temp = itemPath;
                        break;
                    }

                }
                if (!exits) {
                    return false;
                }
            }
            return true;
        }

        int getSize(String[] strArray) {
            Path temp = this;
            for (String itemArrsy : strArray) {
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArrsy)) {
                        temp = itemPath;
                        break;
                    }
                }
            }
            if (temp.isFile) {
                return temp.sizeSelf;
            } else {
                return temp.sizeOffSpring;
            }
        }

        void removePath(String[] strArray, int size) {
            Path temp = this;
            for (String itemArrsy : strArray) {
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArrsy)) {
                        temp.sizeOffSpring -= size;
                        if (itemArrsy.equals(strArray[strArray.length - 1])) {
                            if (itemPath.isFile) {
                                temp.sizeSon -= size;
                            }
                            temp.listPath.remove(itemPath);
                            return;
                        } else {
                            temp = itemPath;
                        }
                        break;
                    }
                }
            }
        }

        boolean canSet(String[] strArray, int sizeSonMax, int sizeOffSpringMax) {
            Path temp = this;
            for (String itemArrsy : strArray) {
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArrsy)) {
                        temp = itemPath;
                        break;
                    }
                }
            }
            if (temp.isFile) {
                return false;
            } else {
                boolean flag = temp.sizeSon > sizeSonMax && sizeSonMax != 0
                        || temp.sizeOffSpring > sizeOffSpringMax && sizeOffSpringMax != 0;
                if (flag) {
                    return false;
                }
                return true;
            }
        }

        void setSize(String[] strArray, int sizeSonMax, int sizeOffSpringMax) {
            Path temp = this;
            for (String itemArrsy : strArray) {
                for (Path itemPath : temp.listPath) {
                    if (itemPath.name.equals(itemArrsy)) {
                        temp = itemPath;
                        break;
                    }
                }
            }
            temp.sizeOffSpringMax = sizeOffSpringMax;
            temp.sizeSonMax = sizeSonMax;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String cmd, filePath;
        int size, sizeSonMax, sizeOffSpringMax;
        Path path = new Path("/", false);
        for (int i = 0; i < n; i++) {
            cmd = sc.next();
            if ("C".equals(cmd)) {
                filePath = sc.next();
                size = sc.nextInt();
                String[] strArray = filePath.split("/");
                String[] strArrayNew = new String[strArray.length - 1];
                for (int j = 0; j < strArray.length - 1; j++) {
                    strArrayNew[j] = strArray[j + 1];
                }
                boolean remove = false;
                int sizeOld = 0;
                if (path.findPath(strArrayNew) && path.isFilePath(strArrayNew)) {
                    sizeOld = path.getSize(strArrayNew);
                    path.removePath(strArrayNew, sizeOld);
                    remove = true;
                }
                if (path.canDo(strArrayNew, size)) {
                    System.out.println("Y");
                    path.saveFile(strArrayNew, size);
                } else {
                    System.out.println("N");
                    if (remove) {
                        path.saveFile(strArrayNew, sizeOld);
                    }
                }

            } else if ("R".equals(cmd)) {
                filePath = sc.next();
                String[] strArray = filePath.split("/");
                String[] strArrayNew = new String[strArray.length - 1];
                for (int j = 0; j < strArray.length - 1; j++) {
                    strArrayNew[j] = strArray[j + 1];
                }
                System.out.println("Y");
                if (path.findPath(strArrayNew)) {
                    path.removePath(strArrayNew, path.getSize(strArrayNew));
                }

            } else if ("Q".equals(cmd)) {
                filePath = sc.next();
                sizeSonMax = sc.nextInt();
                sizeOffSpringMax = sc.nextInt();

                if (!filePath.equals("/")) {
                    String[] strArray = filePath.split("/");
                    String[] strArrayNew = new String[strArray.length - 1];
                    for (int j = 0; j < strArray.length - 1; j++) {
                        strArrayNew[j] = strArray[j + 1];
                    }
                    if (path.canSet(strArrayNew, sizeSonMax, sizeOffSpringMax) && path.findPath(strArrayNew)) {
                        System.out.println("Y");
                        path.setSize(strArray, sizeSonMax, sizeOffSpringMax);
                    } else {
                        System.out.println("N");
                    }
                } else {
                    boolean flag = path.sizeSon > sizeSonMax && sizeSonMax != 0
                            || path.sizeOffSpring > sizeOffSpringMax && sizeOffSpringMax != 0;
                    if (flag) {
                        System.out.println("N");
                    } else {
                        System.out.println("Y");
                        path.sizeSonMax = sizeSonMax;
                        path.sizeOffSpringMax = sizeOffSpringMax;
                    }
                }
            }
        }
        sc.close();
    }

}
// test1 : 2 C /A/A 1024 C /A/a/A 1025
// test2 : 5 C /A 1023 C /A/A 1023 R /A C /A 1023 C /A/A 1023
