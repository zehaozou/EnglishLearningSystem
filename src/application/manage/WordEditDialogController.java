package application.manage;

import application.model.Word;
import application.system.ManageViewController;
import application.tools.DialogTool;
import application.tools.JDBCTool;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author 永世の青空
 * @Description Dialog to edit words.
 * @CreateTime 2021-07-29 16:03
 */
public class WordEditDialogController {

    @FXML
    private TextField wordField;
    @FXML
    private TextField meaningField;
    @FXML
    private TextField partOfSpeechField;
    @FXML
    private TextField sentenceField;
    @FXML
    private TextField sentenceMeaningField;
    @FXML
    private TextField rankingField;

    private Stage dialogStage;
    private Word word;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){}

    /**
     * Sets the stage of this dialog
     */

    public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage;}

    /**
     * Sets the person to be edited in the dialog.
     * @param word
     */
    public void setWord(Word word){
        this.word = word;
        wordField.setText(word.getWord());
        meaningField.setText(word.getMeaning());
        partOfSpeechField.setText(word.getPartOfSpeech());
        sentenceField.setText(word.getSentence());
        sentenceMeaningField.setText(word.getSentenceMeaning());
        rankingField.setText(word.getRanking());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
    public boolean isOkClicked() { return okClicked;}

    @FXML
    private void handleOkButtonAction(){
        if(isInputValid()){
            Connection connection = JDBCTool.getConnection();
            String insertSql =
                    "insert into words (word,meaning,partOfSpeech,sentence,sentenceMeaning,ranking) values('" +
                    wordField.getText() + "','" +
                    meaningField.getText() + "','" +
                    partOfSpeechField.getText() + "','" +
                    sentenceField.getText() + "','" +
                    sentenceMeaningField.getText() + "','" +
                    rankingField.getText() + "');";

            String updateSql = "UPDATE words SET " +
                    "word = '" + wordField.getText() + "'," +
                    "meaning = '" + meaningField.getText() + "'," +
                    "partOfSpeech = '" + partOfSpeechField.getText() + "'," +
                    "sentence = '" + sentenceField.getText() + "'," +
                    "sentenceMeaning = '" + sentenceMeaningField.getText() + "'," +
                    "ranking = '" + rankingField.getText() + "' " +
                    " WHERE word = '" + this.word.getWord() + "';";

            try{
                Statement statement = connection.createStatement();
                if(this.word.getWord()==null)
                    statement.execute(insertSql);  /* 插入新单词 */
                else
                    statement.execute(updateSql); /* 对已有单词进行编辑 */
            }catch (Exception e){
                e.printStackTrace();
            }
            okClicked = true;
            dialogStage.close();
        }
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancelButtonAction(){ dialogStage.close();}

    /**
     * Validates the user input in the text fields.
     * @return true if the input is valid
     */
    private boolean isInputValid(){
        String errorMessage = "";
        if(wordField.getText()==null||wordField.getText().length()==0)
            errorMessage += "No valid word!\n";
        if(meaningField.getText()==null||meaningField.getText().length()==0)
            errorMessage += "No valid meaning!\n";
        if(partOfSpeechField.getText()==null||partOfSpeechField.getText().length()==0)
            errorMessage += "No valid partOfSpeech!\n";
        if(sentenceField.getText()==null||sentenceField.getText().length()==0)
            errorMessage += "No valid sentence!\n";
        if(sentenceMeaningField.getText()==null||sentenceMeaningField.getText().length()==0)
            errorMessage += "No valid sentenceMeaning!\n";
        if(rankingField.getText()==null||rankingField.getText().length()==0)
            errorMessage += "No valid ranking!\n";
        if(errorMessage.length()==0)
            return true;
        else {
            DialogTool.errorDialog(errorMessage);
            return false;
        }
    }
}
