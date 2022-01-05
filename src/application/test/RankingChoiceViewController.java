package application.test;

import application.MainApp;
import application.model.Word;
import application.tools.JDBCTool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author 永世の青空
 * @Description  测试难度选择界面
 * @CreateTime 2021-08-07 16:01
 */
public class RankingChoiceViewController {

    private Stage dialogStage;

    @FXML
    private ChoiceBox<String> rankingChoice;

    private String ranking;
    private final ArrayList<Word> wordsList = new ArrayList<>();
    private final ArrayList<Word> testList = new ArrayList<>();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */

    @FXML
    private void initialize(){
        /* 初始化难度选择下拉框 */
        rankingChoice.getItems().add("四级");
        rankingChoice.getItems().add("六级");
        rankingChoice.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> ranking = newValue);
    }

    public RankingChoiceViewController(){}

    /**
     * Sets the stage of this dialog
     */

    public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage;}



    /* 获取对应难度的所有单词 */
    public void selectRanking(String ranking) {

        Connection connection = JDBCTool.getConnection();
        ResultSet resultSet;
        String selectSql = "select * from words where ranking='" + ranking + "';";

        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(selectSql);
            while(resultSet.next()){
                /* 遍历结果集 */
                String newWord = resultSet.getString(1);
                String newMeaning = resultSet.getString(2);
                String newPartOfSpeech = resultSet.getString(3);
                String newSentence = resultSet.getString(4);
                String mewSentenceMeaning = resultSet.getString(5);
                String newRanking = resultSet.getString(6);

                wordsList.add(new Word(newWord,newMeaning,newPartOfSpeech,newSentence,mewSentenceMeaning,newRanking));
            }
            resultSet.close();
        } catch (SQLException se){
            se.printStackTrace();
        }

    }

    /* 获取一定范围内的随机数*/
    public static int[] randomCommon(int min, int max, int n){

        Set<Integer> set = new HashSet<>();
        int[] array = new int[n];
        do {
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max - min)) + min;
            // 将不同的数存入HashSet中
            set.add(num);

            // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
        } while (set.size() < n);
        int i = 0;
        for (int a : set) {
            array[i] = a;
            i++;
        }

        return array;
    }


    /* 从对应难度的单词中选择10个用于测试 */
    public ArrayList<Word> getTextWords(){
        selectRanking(ranking);
        int testListSize = 10;
        int[] indexOfList = randomCommon(0,wordsList.size(),testListSize); /* 用一个长度为10的数组存储10个单词在list中的下标 */
        for(Integer i:indexOfList)
            System.out.print(i+" ");

        for(Integer i:indexOfList)
            testList.add(wordsList.get(i));  /* 获得testList */

        return testList;
    }

    /* 显示测试界面 */
    public void showTestView(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("test/TestView.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("单词测试");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TestViewController testViewController = loader.getController();
            testViewController.setTestList(getTextWords()); /* 将形成的测试单词列表传进测试界面 */
            testViewController.initData();
            testViewController.setDialogStage(dialogStage);
            dialogStage.showAndWait();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* 打开测试界面 */
    @FXML
    public void handleTestButtonAction(){

        showTestView();
        dialogStage.close(); // 关闭难度选择界面
    }
}
