package hanz.com.mymemo;

import java.io.Serializable;
import java.util.Date;

public class MainMemo implements Serializable {

    private String dataTitle;
    private String dataContent;
    private Date dataCreateTime;

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public Date getDataCreateTime() {
        return dataCreateTime;
    }

    public void setDataCreateTime(Date dataCreateTime) {
        this.dataCreateTime = dataCreateTime;
    }
}
