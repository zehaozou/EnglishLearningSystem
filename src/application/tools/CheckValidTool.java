package application.tools;

/**
 * @author 永世の青空
 * @Description 检查管理员登录信息是否正确
 * @CreateTime 2021-07-11 9:58
 */
public class CheckValidTool {
    final private static String accountAdm ="邹泽昊";
    final private static String passwordAdm = "123456";

    public static boolean checkReturn(String account,String password){
        boolean checkBool = false;

        if(account.equals(accountAdm)&&password.equals(passwordAdm))
            checkBool = true; // 账号密码都正确，登录成功

        return checkBool;
    }
}
