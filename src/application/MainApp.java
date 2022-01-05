package application;

import application.login.LoginViewController;
import application.manage.EssayEditDialogController;
import application.manage.WordEditDialogController;
import application.model.Essay;
import application.model.Word;
import application.search.SearchViewController;
import application.system.ManageViewController;
import application.system.StudyViewController;
import application.test.RankingChoiceViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author 永世の青空
 * @Description 主控制器
 * @CreateTime 2021-07-10 18:08
 */
public class MainApp extends Application {
    /* 主舞台 */
    private Stage stage;

    /* 主场景 */
    private Scene scene;


    @Override
    public void start(Stage primaryStage) {
        try{
            stage = primaryStage;
            showLoginView();/* 显示登录界面 */
            primaryStage.show();/* 调用show方法显示 */
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /* 视图切换方法，用于显示 */
    private Object replaceSceneContent(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(fxmlFile));

        AnchorPane ap = loader.load();
        scene = new Scene(ap);
        stage.setScene(scene);
        stage.setResizable(false);

        return loader.getController();
    }

    /* 显示登录界面 */
    public void showLoginView(){
        try{
            stage.setTitle("登录");
            stage.getIcons().clear();
            /* 创建登录控制器对象*/
            LoginViewController loginViewController = (LoginViewController)replaceSceneContent("login/LoginView.fxml");
            /* 将主控制器的引用传给登录控制器对象 */
            loginViewController.setMainApp(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* 显示英语学习系统主界面 */
    public void showStudyView(){
        try{
            stage.setTitle("学习界面");
            StudyViewController studyViewController = (StudyViewController)replaceSceneContent("system/StudyView.fxml");
            studyViewController.setMainApp(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* 显示管理员操作界面 */
    public void showManageView(){
        try{
            stage.setTitle("管理员界面");
            ManageViewController manageViewController = (ManageViewController) replaceSceneContent("system/ManageView.fxml");
            manageViewController.setMainApp(this);
            manageViewController.getWordsFromDataBase();
            manageViewController.getEssaysFromDataBase();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 显示检索界面 */
    public  void showSearchView(){
        try{
            stage.setTitle("检索");
            SearchViewController searchViewController = (SearchViewController) replaceSceneContent("search/SearchView.fxml");
            searchViewController.setMainApp(this);
            searchViewController.getEssaysFromDataBase();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /* 显示难度选择界面 */
    public void showRankingChoiceView(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("test/RankingChoiceView.fxml"));
            AnchorPane page =  loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("难度选择");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RankingChoiceViewController rankingChoiceViewController = loader.getController();
            rankingChoiceViewController.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }





    /* 打开一个对话框，用于编辑选中的单词，如果管理员点击“OK”，更改将被保存 */
    public boolean showWordEditDialog(Word word){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("manage/WordEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Word");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            WordEditDialogController wordEditDialogController = loader.getController();
            wordEditDialogController.setDialogStage(dialogStage);
            wordEditDialogController.setWord(word);

            dialogStage.showAndWait();

            return wordEditDialogController.isOkClicked();

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /* 打开一个对话框，用于编辑选中的短文，如果管理员点击“OK”，更改将被保存 */
    public boolean showEssayEditDialog(Essay essay){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("manage/EssayEditDialog.fxml"));
            AnchorPane page =  loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Essay");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EssayEditDialogController essayEditDialogController = loader.getController();
            essayEditDialogController.setDialogStage(dialogStage);
            essayEditDialogController.setEssay(essay);

            dialogStage.showAndWait();

            return essayEditDialogController.isOkClicked();

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public Scene getScene() { return scene; }

    public static void main(String[] args){launch(args);}
}


