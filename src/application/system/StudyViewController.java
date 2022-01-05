package application.system;

import application.MainApp;
import javafx.fxml.FXML;


/**
 * @author 永世の青空
 * @Description  学习界面主控制器
 * @CreateTime 2021-07-14 11:35
 */
public class StudyViewController {
    private MainApp mainApp;

    public StudyViewController(){} /* 构造方法 */

    /* 获取对主控制器的引用 */
    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }

    /* 进入检索界面 */
    @FXML
    public void handleSearchButtonAction(){this.mainApp.showSearchView();}
    /* 进入测试界面 */
    @FXML
    public void handleTestButtonAction(){this.mainApp.showRankingChoiceView();}
    /* 退出登录，返回登录界面 */
    @FXML
    public void handleExitButtonAction(){ this.mainApp.showLoginView(); }
}
