package application.system;
import application.MainApp;

import application.model.Essay;
import application.model.Word;
import application.search.SearchViewController;
import application.tools.DatabaseFiction;
import application.tools.DialogTool;
import application.tools.JDBCTool;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;


/**
 * @author 永世の青空
 * @Description 管理员界面
 * @CreateTime 2021-07-14 11:37
 */
public class ManageViewController {
    private MainApp mainApp;

    @FXML
    public TableView<Word> wordsTableView;
    @FXML
    private TableColumn<Word, String> wordColumn;
    @FXML
    private TableColumn<Word, String> meaningColumn;
    @FXML
    private TableColumn<Word,String> partOfSpeechColumn;
    @FXML
    private TableColumn<Word,String> sentenceColumn;
    @FXML
    private TableColumn<Word,String> sentenceMeaningColumn;
    @FXML
    private TableColumn<Word,String> rankingColumn;

    private javafx.collections.ObservableList<Word> wordsDataList=FXCollections.observableArrayList();

    @FXML
    private TableView<Essay> essayTableView;
    @FXML
    private TableColumn<Essay,Number> idColumn;
    @FXML
    private TableColumn<Essay,String> titleColumn;
    @FXML
    private TextArea EnglishEssay;
    @FXML
    private TextArea ChineseEssay;
    @FXML
    private javafx.collections.ObservableList<Essay> essaysDataList=FXCollections.observableArrayList();

    /**
     * The constructor is called before the initialize() method
     */
    public ManageViewController(){} /* The conductor */

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        // Initialize the words table with the six columns.
        wordColumn.setCellValueFactory(cellData -> cellData.getValue().wordProperty());
        meaningColumn.setCellValueFactory(cellData -> cellData.getValue().meaningProperty());
        partOfSpeechColumn.setCellValueFactory(cellData -> cellData.getValue().partOfSpeechProperty());
        sentenceColumn.setCellValueFactory(cellData -> cellData.getValue().sentenceProperty());
        sentenceMeaningColumn.setCellValueFactory(cellData -> cellData.getValue().sentenceMeaningProperty());
        rankingColumn.setCellValueFactory(cellData -> cellData.getValue().rankingProperty());

        idColumn.setCellValueFactory(cellData->cellData.getValue().idProperty());
        titleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());

        // Clear essay details.
        showEssay(null);
        // Listen for selection changes and show the essay's English and Chinese when changed.
        essayTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEssay(newValue));
    }

    /* 退出登录，返回登录界面 */
    @FXML
    public void handleExitButtonAction(){ this.mainApp.showLoginView(); }

    /* 查询数据库单词并展示 */
    public void getWordsFromDataBase(){
        /* 定义一个单词集合 */
            TreeSet<Word> wordTreeSet = JDBCTool.selectAllWords();
            wordsDataList = FXCollections.observableArrayList(wordTreeSet);

            /* 表的各列添加数据 */
            wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
            meaningColumn.setCellValueFactory(new PropertyValueFactory<>("meaning"));
            partOfSpeechColumn.setCellValueFactory(new PropertyValueFactory<>("partOfSpeech"));
            sentenceColumn.setCellValueFactory(new PropertyValueFactory<>("sentence"));
            sentenceMeaningColumn.setCellValueFactory(new PropertyValueFactory<>("sentenceMeaning"));
            rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));

            wordsTableView.setItems(wordsDataList);
    }

    /* 查询数据库短文并展示 */
    public void getEssaysFromDataBase(){
        /* 定义一个单词集合 */
        TreeSet<Essay> essaysTreeSet = JDBCTool.selectAllEssays();
        essaysDataList = FXCollections.observableArrayList(essaysTreeSet);
        /* 表的各列添加数据 */
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        idColumn.setSortType(TableColumn.SortType.DESCENDING);
        essayTableView.setItems(essaysDataList);
    }

    /* 刷新界面 */
    public void refresh(){
        mainApp.showManageView();  // 重新加载管理员界面
        FadeTransition ft = new FadeTransition();  // 做出渐变渐显的效果
        ft.setDuration(Duration.seconds(0.1));
        ft.setNode(mainApp.getScene().getRoot());
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    /* 录入单词例句 */
    @FXML
    private void addWordButtonAction() {
        Word tempWord = new Word();
        boolean okClicked = mainApp.showWordEditDialog(tempWord);
        if(okClicked){
            wordsDataList.add(tempWord);
        }
        // 刷新界面
        refresh();
    }

    /* 删除单词例句 */
    @FXML
    public void deleteWordButtonAction(){
        Word newWord = wordsTableView.getSelectionModel().getSelectedItem();/* 选中要删除的那一行 */
        if(newWord==null) {
            System.out.println("未选择删除目标，请选择要删除的那一行！");
            return;
        }
        Connection connection = JDBCTool.getConnection();
        String deleteSql = "delete from words" + " where word ='" + newWord.getWord() + "'";

        try{
            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
            System.out.println("删除成功！");
        }catch (Exception e){
            e.printStackTrace();
        }


        DialogTool.informationDialog("删除成功","你已成功删除所选项！");
        // 刷新界面
        refresh();
    }


    /* 修改单词例句 */
    @FXML
    public void alterWordButtonAction(){
        Word selectedWord = wordsTableView.getSelectionModel().getSelectedItem();/* 选中要修改的那一行 */
        if(selectedWord!=null) {
            mainApp.showWordEditDialog(selectedWord);
        }
        else{
            DialogTool.warningDialog("No selection","Please select a row in the table.");
        }
        // 刷新界面
        refresh();
    }

    private void showEssay(Essay essay) {
        if (essay != null) {
            // 实现自动换行
            EnglishEssay.setWrapText(true);
            ChineseEssay.setWrapText(true);

            EnglishEssay.setText(essay.getEssayEnglish());
            ChineseEssay.setText(essay.getEssayChinese());
        } else {
            EnglishEssay.setText("");
            ChineseEssay.setText("");
        }
    }

    /* 添加短文 */
    @FXML
    public void handleAddEssayButtonAction(){
        Essay tempEssay = new Essay();

        boolean okClicked = mainApp.showEssayEditDialog(tempEssay);
        if(okClicked){
            essaysDataList.add(tempEssay);
        }
        // 刷新界面
        refresh();
    }
    /* 修改短文 */
    @FXML
    public void handleEditEssayButtonAction(){
        Essay selectedEssay = essayTableView.getSelectionModel().getSelectedItem();/* 选中要修改的那一行 */
        if(selectedEssay!=null) {
            mainApp.showEssayEditDialog(selectedEssay);
        }
        else{
            DialogTool.warningDialog("No selection","Please select a row in the table.");
        }
        // 刷新界面
        refresh();
    }

    /* 删除短文 */
    @FXML
    public void handleDeleteEssayButtonAction(){

        Essay tempEssay = essayTableView.getSelectionModel().getSelectedItem();/* 选中要删除的那一行 */
        if(tempEssay==null) {
            System.out.println("未选择删除目标，请选择要删除的那一行！");
            return;
        }

        Connection connection = JDBCTool.getConnection();
        String deleteSql = "delete from essays" + " where id ='" + tempEssay.getId() + "'";

        try{
            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
            System.out.println("删除成功！");
        }catch (Exception e){
            e.printStackTrace();
        }

        DialogTool.informationDialog("删除成功","你已成功删除所选项！");
        // 刷新界面
        refresh();
    }


    /**
     * 数据备份与恢复功能
     * */

    /* 数据备份 */
    @FXML
    public void dataBackUpButtonAction() throws IOException, InterruptedException, SQLException {
        DatabaseFiction.backUp();
    }
    /* 数据恢复 */
    @FXML
    public void dataRecoverButtonAction() throws IOException, InterruptedException, SQLException {
        DatabaseFiction.recover();
    }

}
