package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

/**
 * @author 永世の青空
 * @Description 短文模型
 * @CreateTime 2021-07-20 8:57
 */
public class Essay implements Comparable,Cloneable{
    /* 数据域 */
    private final IntegerProperty id;      //短文编号
    private final StringProperty title;    //短文标题
    private String essayEnglish;             //短文英文文本
    private String essayChinese;             //短文中文翻译

    /* 构造方法 */
    public Essay(){ this(0,null,null,null);}

    public Essay(int id,String title){
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
    }

    public Essay(int id,String title,String essayEnglish,String essayChinese){
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.essayEnglish = essayEnglish;
        this.essayChinese = essayChinese;
    }


    /* 设置访问器与修改器 */
    public void setId(int id){ this.id.set(id);}

    public int getId() { return this.id.get(); }

    public void setTitle(String title) { this.title.set(title); }

    public String getTitle() { return this.title.get(); }

    public void setEssayEnglish(String essayEnglish) { this.essayEnglish = essayEnglish; }

    public String  getEssayEnglish() { return essayEnglish; }

    public void setEssayChinese(String essayChinese) { this.essayChinese = essayChinese; }

    public String getEssayChinese() { return essayChinese; }

    public IntegerProperty idProperty(){return id;}

    public StringProperty titleProperty(){return title;}

    @Override
    public String toString(){
        return "essay{" +
                "id=" + id.get() +
                ", title='" + title.get() + '\'' +
                ", essay='" + essayEnglish + '\'' +
                ", essay_chinese='" + essayChinese + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Essay temp = (Essay) o;
        return this.getTitle().compareToIgnoreCase(temp.getTitle());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
