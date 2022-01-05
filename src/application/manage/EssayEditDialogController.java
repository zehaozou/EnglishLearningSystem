package application.manage;

import application.model.Essay;
import application.tools.DialogTool;
import application.tools.JDBCTool;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.Connection;
import java.sql.Statement;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

/**
 * @author 永世の青空
 * @Description
 * @CreateTime 2021-08-05 15:37
 */
public class EssayEditDialogController {

    @FXML
    private TextField idField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea essayEnglishArea;
    @FXML
    private TextArea essayChineseArea;

    private Stage dialogStage;
    private Essay essay;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){}

    /**
     * Sets the stage of this dialog.
     */
    public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage; }

    /**
     * Sets the person to be edited in the dialog.
     */
    public void setEssay(Essay essay) {
        this.essay = essay;

        idField.setText(Integer.toString(essay.getId()));
        titleField.setText(essay.getTitle());
        essayChineseArea.setText(essay.getEssayChinese());
        essayEnglishArea.setText(essay.getEssayEnglish());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOkButtonAction() {
        if (isInputValid()) {
            Connection connection = JDBCTool.getConnection();
            String insertSql =
                    "insert into essays (id,title,essayEnglish,essayChinese) values('" +
                            idField.getText() + "','" +
                            titleField.getText() + "','" +
                            essayEnglishArea.getText() + "','" +
                            essayChineseArea.getText() + "');";

            String updateSql = "UPDATE essays SET " +
                    "id = " + Integer.parseInt(idField.getText()) + "," +
                    "title = '" + titleField.getText() + "'," +
                    "essayEnglish = '" + essayEnglishArea.getText() + "'," +
                    "essayChinese = '" + essayChineseArea.getText() + "' " +
                    " WHERE id = " + this.essay.getId() + ";";

            try{
                Statement statement = connection.createStatement();
                if(this.essay.getId()<=0)
                    statement.execute(insertSql);  /* 插入新短文 */
                else
                    statement.execute(updateSql); /* 对已有短文进行编辑 */
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
    private void handleCancelButtonAction() {
        dialogStage.close();
    }


    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (idField.getText() == null || idField.getText().length() == 0)
            errorMessage += "No valid id!\n";

        if (titleField.getText() == null || titleField.getText().length() == 0)
            errorMessage += "No valid title!\n";

        if (essayEnglishArea.getText() == null || essayEnglishArea.getText().length() == 0)
            errorMessage += "No valid English Area!\n";

        if(essayChineseArea.getText()==null||essayChineseArea.getText().length()==0){
            errorMessage += "No valid Chinese Area!\n";
        }
        if (errorMessage.length() == 0)
            return true;
        else {
            // Show the error message.
            DialogTool.errorDialog(errorMessage);
            return false;
        }
    }
}
