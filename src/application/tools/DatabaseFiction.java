package application.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 永世の青空
 * @Description 数据备份和恢复工具
 * @CreateTime 2021-07-16 9:27
 */
public class DatabaseFiction {
    /* 数据备份 */
    public static void backUp() {
        String backUpSql = "SET NOCOUNT ON " +
                "BACKUP DATABASE EnglishLearningSystem " +
                "TO DISK = N'E:\\JavaProject\\EnglishLearningSystem\\fiction\\EnglishLearningSystem.bak' " +
                "WITH NOFORMAT, NOINIT, " +
                "NAME = N'EnglishLearningSystem-Full Database Backup', SKIP, NOREWIND, NOUNLOAD,  STATS = 10 ;";

        try (Connection connection = JDBCTool.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(backUpSql)) {
        } catch (SQLException se) {
            se.printStackTrace();
        }

        DialogTool.informationDialog("备份成功","数据已成功备份！！");
    }

    /* 数据恢复 */
    public static void recover() {
        String recoverSql = "USE [master] " +
                "RESTORE DATABASE EnglishLearningSystem " +
                "FROM DISK = N'E:\\JavaProject\\EnglishLearningSystem\\fiction\\EnglishLearningSystem.bak' WITH  FILE = 1,  NOUNLOAD,  STATS = 5 ;";
        try (Connection connection = JDBCTool.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(recoverSql)) {
        } catch (SQLException se) {
            se.printStackTrace();
        }

        DialogTool.informationDialog("恢复成功","数据已成功恢复！！");
    }
}
