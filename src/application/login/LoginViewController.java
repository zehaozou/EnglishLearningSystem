package application.login;

import application.MainApp;
import application.tools.CheckValidTool;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 * @author 永世の青空
 * @Description 登录控制器
 * @CreateTime 2021-07-11 16:57
 */
public class LoginViewController {
    /* 对主控制器的引用 */
    private MainApp mainApp;

    @FXML
    private Label accountLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label errorInfoLabel;
    @FXML
    private Button LoginButton;
    @FXML
    private Button LogoutButton;
    @FXML
    private RadioButton visitorButton;
    @FXML
    private RadioButton administratorButton;
    @FXML
    private ImageView leftImageView;
    @FXML
    private TextField accountField;
    @FXML
    private TextField passwordField;

    private boolean visitor = false;
    private boolean administrator = false;

    public LoginViewController(){} /* 构造方法 */

    /* 获取主控制器的引用 */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize(){
        /* 设置登录界面左侧图片 */
        leftImageView.setImage(new Image("file:images/summerRain.png"));
        leftImageView.setPreserveRatio(true);
        leftImageView.setSmooth(true);
    }

    /* 游客登录选项 */
    public void handleVisitorButtonAction(){  /* 游客选项按钮点击事件 */
        visitor = visitorButton.isSelected();
    }
    /* 管理员登录选项 */
    public void handleAdministratorButton(){ /* 管理员选项按钮点击事件 */
        administrator = administratorButton.isSelected();
    }

    /* 处理登录到主界面的事件 */
    @FXML
    public void handleLoginButtonAction(){  /* 登录按钮点击事件 */
        /*对用户类型进行判断，游客无需账号密码*/
        if(visitor&&!administrator)
            this.mainApp.showStudyView();

        /* 对管理员的账号密码进行判断，正确则跳转到主视图 */
        else if(!visitor&&administrator){
            String account = this.accountField.getText();
            String password = this.passwordField.getText();
            if(CheckValidTool.checkReturn(account,password))
                this.mainApp.showManageView();
            else{
                errorInfoLabel.setText("管理员用户名或密码不正确！请重新登陆！");
                accountField.clear();
                passwordField.clear();
                //登录失败做出渐变渐显的效果
                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.seconds(0.1));
                ft.setNode(mainApp.getScene().getRoot());
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
            }
        }
    }

    /* 退出学习系统 */
    @FXML
    public void handleLogoutButtonAction(){
        Platform.exit();
    }
}
