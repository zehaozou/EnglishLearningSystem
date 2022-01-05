package application.test;

import application.model.Word;
import application.tools.DialogTool;
import application.tools.JDBCTool;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author 永世の青空
 * @Description 测试控制器
 * @CreateTime 2021-07-15 16:10
 */
public class TestViewController {

    private Stage dialogStage;

    public ArrayList<Word> testList;
    private int count = 1;  /* 当前题目号 */

    @FXML
    private TextField testField;
    public Text meaning;
    public Text partOfSpeech;
    public Text index;
    public Text ranking;

    @FXML
    private void initialize(){}

    public TestViewController(){}

    public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage;}

    public void setTestList(ArrayList<Word> testList){ this.testList = testList; }

    /* 获取当前题目的题号以及单词的释义和词性 */
    public void initData(){
        ranking.setText(testList.get(count-1).getRanking());
        index.setText(count + "/" + testList.size());
        meaning.setText(testList.get(count-1).getMeaning());
        partOfSpeech.setText(testList.get(count-1).getPartOfSpeech());
    }

    /* 点击按钮检验单词的正确与否 */
    @FXML
    public void handleTestCheckButtonAction(){
        if(testField.getText()==null)
            DialogTool.informationDialog("未输入单词！","请输入单词进行测试！");
        else{
            Connection connection = JDBCTool.getConnection();
            ResultSet resultSet = null;
            String selectSql = "select word from words where meaning = '" + meaning.getText() +"' and partOfSpeech = '" + partOfSpeech.getText()+"'";
            try(Statement statement = connection.createStatement()){
                resultSet = statement.executeQuery(selectSql);
                if(resultSet.next()){
                    String newWord = resultSet.getString(1);
                    if(newWord.equals(testField.getText()))
                        DialogTool.informationDialog("","恭喜你答对了！");
                    else
                        DialogTool.informationDialog("很遗憾，答错了！","答案是: " + newWord);
                }
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

    /* 点击按钮，回到上一个单词的测试 */
    @FXML
    public void handleBeforeButtonAction(){
        if(count==1) {
            DialogTool.informationDialog("", "已经是第一个单词了！");
            return;
        }
        count--;
        initData(); /* 刷新题目 */
    }


    /* 点击按钮，进行下一个单词的测试 */
    @FXML
    public void handleNextButtonAction(){
        if(count==testList.size()) {
            DialogTool.informationDialog("", "已经是最后一个单词了！");
            return;
        }
        count++;
        initData(); /* 更新题目 */
    }
}
