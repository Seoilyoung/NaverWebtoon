package gabriel.com.nwebtoon_android.model;

import com.orm.SugarRecord;

/**
 * Created by seoil on 2016-02-25.
 */
public class LocalData_CurrentWebtoon extends SugarRecord {
    public String TOON_ID ;
    public String TOON_TP ;
    public String TOON_NAME;
    public String TOON_WTR;
    public int TOON_STAT ;
    public String DT_THM ;
    public long UPDATE_DT;
    public String DT_ID;

    public LocalData_CurrentWebtoon() {
    }

    public LocalData_CurrentWebtoon(String TOON_ID, String DT_ID, String TOON_TP, String TOON_NAME, String TOON_WTR, int TOON_STAT, String DT_THM, long UPDATE_DT) {
        this.TOON_ID = TOON_ID ;
        this.DT_ID = DT_ID;
        this.TOON_TP = TOON_TP ;
        this.TOON_NAME = TOON_NAME;
        this.TOON_WTR = TOON_WTR;
        this.TOON_STAT = TOON_STAT;
        this.DT_THM = DT_THM ;
        this.UPDATE_DT = UPDATE_DT;
    }

    public void setDT_ID(String DT_ID) {
        this.DT_ID = DT_ID ;
    }
}