package application.tools;

import application.model.Essay;
import application.model.Word;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 永世の青空
 * @Description 数据库连接工具
 * @CreateTime 2021-07-11 10:01
 */
public class JDBCTool {

    /* 建立数据连接 */
    final static String dbURL = "jdbc:sqlserver://localhost:1433;database = EnglishLearningSystem;"
            +"user = EnglishLearn;" + "password = 123456;";
    static Connection dbConn = null;
    /* 静态代码块（将加载驱动、连接数据库放入静态块中） */
    static {
        try {
        dbConn = DriverManager.getConnection(dbURL); /* 获取数据库连接 */
        System.out.println("成功连接到数据库！");
        } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("连接失败！");
        }
    }
    /* 对外提供一个方法来获取数据库连接 */
    public static Connection getConnection(){ return dbConn; }

    /**
     * 单词与例句部分
     */

    /* 判断输入是否包含中文 */
    public static boolean checkChinese(String getWordField) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(getWordField);
        return m.find();
    }

    /* 选择单词 */
    public static Word selectWord(String getWordField){
        Word select_word = null;
        ArrayList<String> meaningList = new ArrayList<>();
        Connection connection = getConnection();
        ResultSet resultSet;
        ResultSet meaningResult;

        // 输入为英文单词
        String selectWordSql = "select word,meaning,partOfSpeech,sentence,sentenceMeaning,ranking from words where word = '"+ getWordField +"'";
        // 输入为英文例句
        String selectSentenceSql = "select word,meaning,partOfSpeech,sentence,sentenceMeaning,ranking from words where sentence = '"+ getWordField +"'";
        // 输入为英文单词释义
        String selectTempSql = "select meaning from words";

        String selectMeaningSql = "select word,meaning,partOfSpeech,sentence,sentenceMeaning,ranking from words where meaning = ";


        try(Statement statement = connection.createStatement()){

            if(checkChinese(getWordField)) {
                boolean isContain = false;
                String meaning="";
                meaningResult = statement.executeQuery(selectTempSql);    // 获取所有单词的释义

                while(meaningResult.next())
                    meaningList.add(meaningResult.getString(1));

                // 检验输入是否属于某个单词释义的一部分
                for(String i:meaningList){
                    if (i.contains(getWordField)) {
                        isContain = true;
                        meaning = i;
                        break;
                    }
                }
                if(isContain) {
                    selectMeaningSql += "'" + meaning + "'";
                    resultSet = statement.executeQuery(selectMeaningSql);   // 根据释义搜索
                }
                else
                    resultSet = null;
            }
            else if(getWordField.contains(" "))
                resultSet = statement.executeQuery(selectSentenceSql);  // 根据例句搜索
            else
                resultSet = statement.executeQuery(selectWordSql);     // 根据单词搜索

            if(resultSet.next()){
                /* 遍历结果集 */
                String newWord = resultSet.getString(1);
                String newMeaning = resultSet.getString(2);
                String newPartOfSpeech = resultSet.getString(3);
                String newSentence = resultSet.getString(4);
                String mewSentenceMeaning = resultSet.getString(5);
                String newRanking = resultSet.getString(6);

                select_word = new Word(newWord,newMeaning,newPartOfSpeech,newSentence,mewSentenceMeaning,newRanking);
            }
            resultSet.close();
        } catch (SQLException se){
            se.printStackTrace();
        }
        return select_word;
    }

    /* 选择数据库内的全部单词 */
    public static TreeSet<Word> selectAllWords(){
        TreeSet<Word> wordTreeSet = new TreeSet<>();
        Connection connection = getConnection();
        ResultSet resultSet = null;
        String selectAllWordsSql = "select * from words ";
        /* 预编译SQL语句 */
        try(Statement statement = connection.createStatement()){
            /* 先对应SQL语句，给SQL语句传递参数*/
            resultSet = statement.executeQuery(selectAllWordsSql);
            while(resultSet.next()){
                /* 遍历结果集 */
                String newWord = resultSet.getString(1);
                String newMeaning = resultSet.getString(2);
                String newPartOfSpeech = resultSet.getString(3);
                String newSentence = resultSet.getString(4);
                String mewSentenceMeaning = resultSet.getString(5);
                String newRanking = resultSet.getString(6);
                wordTreeSet.add(new Word(newWord,newMeaning,newPartOfSpeech,newSentence,mewSentenceMeaning,newRanking));
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return wordTreeSet;
    }

    /**
     *  短文部分
     */

    /* 选择数据库内的全部短文 */
    public static TreeSet<Essay> selectAllEssays(){
        TreeSet<Essay> essayTreeSet = new TreeSet<>();
        Connection connection = getConnection();
        ResultSet resultSet = null;
        String selectAllEssaysSql = "select * from essays ";

        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(selectAllEssaysSql);
            while(resultSet.next()){
                Integer newId = resultSet.getInt(1);
                String newTitle = resultSet.getString(2);
                String newEssayEnglish = resultSet.getString(3);
                String newEssayChinese = resultSet.getString(4);
                essayTreeSet.add(new Essay(newId,newTitle,newEssayEnglish,newEssayChinese));
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        return essayTreeSet;
    }
}
