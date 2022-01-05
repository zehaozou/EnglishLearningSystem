package application.search;

import application.MainApp;
import application.model.Essay;
import application.model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import application.tools.JDBCTool;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.TreeSet;

/**
 * @author 永世の青空
 * @Description  检索控制器
 * @CreateTime 2021-07-14 16:59
 */
public class SearchViewController {
    /* 构造方法 */
    public SearchViewController(){}
    /* 对主控制器的引用 */
    private MainApp mainApp;

    @FXML
    public Text sentence;
    public Text word;
    public Text partOfSpeech;
    public TextField getWord;
    public Text meaning;
    public Text sentenceMeaning;
    public TabPane tabPane;
    public Text ranking;
    public TabPane essayPane;

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


    /* 获取主控制器的引用 */
    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }

    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(cellData->cellData.getValue().idProperty());
        titleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());

        // Clear essay details.
        showEssay(null);

        // Listen for selection changes and show the essay's English and Chinese when changed.
        essayTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEssay(newValue));
    }

    private void showEssay(Essay essay){
        if(essay!=null){
            // 实现自动换行
            EnglishEssay.setWrapText(true);
            ChineseEssay.setWrapText(true);

            EnglishEssay.setText(essay.getEssayEnglish());
            ChineseEssay.setText(essay.getEssayChinese());
        }
        else{
            EnglishEssay.setText("");
            ChineseEssay.setText("");
        }
    }

    /* 查询数据库短文并展示 */
    public void getEssaysFromDataBase(){
        /* 定义一个单词集合 */
        TreeSet<Essay> essaysTreeSet = JDBCTool.selectAllEssays();
        essaysDataList = FXCollections.observableArrayList(essaysTreeSet);
        /* 表的各列添加数据 */
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        essayTableView.setItems(essaysDataList);
    }



    /* 查询单词 */
    @FXML
    public void searchTheWordButtonAction(){
        Word newWord = JDBCTool.selectWord(getWord.getText().trim());
        if(newWord==null) {
            word.setText("不存在该单词！");
            meaning.setText("");
            partOfSpeech.setText("");
            sentence.setText("");
            ranking.setText("");
            sentenceMeaning.setText("");
        }
        else{
            word.setText(newWord.getWord());
            meaning.setText(newWord.getMeaning());
            partOfSpeech.setText(newWord.getPartOfSpeech());
            sentence.setText(newWord.getSentence());
            ranking.setText(newWord.getRanking());
            sentenceMeaning.setText(newWord.getSentenceMeaning());
        }
    }

    /* 退出检索界面，返回学习系统的主界面 */
    @FXML
    public void handleReturnButton(){this.mainApp.showStudyView();}
}
