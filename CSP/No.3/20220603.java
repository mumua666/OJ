import java.util.LinkedHashSet;
import java.util.Scanner;

/** @author Linux_Mumu */
public class Exp20220603 {
    static class Role {
        String roleName;
        LinkedHashSet<String> operationName;
        LinkedHashSet<String> typeName;
        LinkedHashSet<String> itemName;
        LinkedHashSet<String> linkedUserGroup;
        LinkedHashSet<String> linkedUserName;
    }

    static boolean check(int ng, String groupName[], String userName, Role role[], String operationName,
            String typeName, String itemName, int n) {
        for (int j = 0; j < n; j++) {
            boolean containUserName = role[j].linkedUserName.contains(userName);
            if (containUserName) {
                boolean containOperationName = role[j].operationName.contains(operationName)
                        || role[j].operationName.contains("*");
                if (containOperationName) {
                    boolean containTypeName = role[j].typeName.contains(typeName) || role[j].typeName.contains("*");
                    if (containTypeName) {
                        if (role[j].itemName.isEmpty() || role[j].itemName.contains(itemName)) {
                            return true;
                        }
                    }
                }
            }
        }
        while (ng-- > 0) {
            for (int j = 0; j < n; j++) {
                boolean containUserGroup = role[j].linkedUserGroup.contains(groupName[ng]);
                if (containUserGroup) {
                    boolean containOperationName = role[j].operationName.contains(operationName)
                            || role[j].operationName.contains("*");
                    if (containOperationName) {
                        boolean containTypeName = role[j].typeName.contains(typeName) || role[j].typeName.contains("*");
                        if (containTypeName) {
                            if (role[j].itemName.isEmpty() || role[j].itemName.contains(itemName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt(), q = sc.nextInt();
        Role[] role = new Role[n];
        for (int i = 0; i < n; i++) {
            role[i] = new Role();
            role[i].operationName = new LinkedHashSet<String>();
            role[i].typeName = new LinkedHashSet<String>();
            role[i].itemName = new LinkedHashSet<String>();
            role[i].linkedUserGroup = new LinkedHashSet<String>();
            role[i].linkedUserName = new LinkedHashSet<String>();
            String roleName = sc.next();
            role[i].roleName = roleName;
            int nv = sc.nextInt();
            while (nv-- > 0) {
                role[i].operationName.add(sc.next());
            }
            int no = sc.nextInt();
            while (no-- > 0) {
                role[i].typeName.add(sc.next());
            }
            int nn = sc.nextInt();
            while (nn-- > 0) {
                role[i].itemName.add(sc.next());
            }
        }
        for (int i = 0; i < m; i++) {
            String roleName = sc.next();
            int index = -1;
            for (int j = 0; j < n; j++) {
                if (role[j].roleName.equals(roleName)) {
                    index = j;
                    break;
                }
            }
            int ns = sc.nextInt();
            while (ns-- > 0) {
                String userOrGroup = sc.next(), userOrGroupName = sc.next();
                if ("u".equals(userOrGroup)) {
                    role[index].linkedUserName.add(userOrGroupName);
                } else {
                    role[index].linkedUserGroup.add(userOrGroupName);
                }
            }
        }
        for (int i = 0; i < q; i++) {

            String userName = sc.next();
            int ng = sc.nextInt();
            String[] groupName = new String[ng];
            for (int j = 0; j < ng; j++) {
                groupName[j] = sc.next();
            }
            String operationName = sc.next(), typeName = sc.next(), itemName = sc.next();
            if (check(ng, groupName, userName, role, operationName, typeName, itemName, n)) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
        sc.close();
    }
}
