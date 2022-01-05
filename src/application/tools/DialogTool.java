package application.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author 永世の青空
 * @Description 各种提示对话框
 * @CreateTime 2021-07-14 11:55
 */
public class DialogTool {

    /* 消息提示对话框 */
    public static void informationDialog(String header,String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(new ImageView(new Image("file:images/message.png")));
        alert.setTitle("消息");
        alert.setHeaderText(header);
        alert.setContentText(content);
        //显示弹窗，同时后续代码等挂起
        alert.showAndWait();
    }

    /* 警告消息对话框 */
    public static void warningDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setGraphic(new ImageView(new Image("file:images/warn.png")));
        alert.setTitle("警告");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /* 错误消息对话框 */
    public static void errorDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setGraphic(new ImageView(new Image("file:images/error.png")));
        alert.setTitle("错误");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
