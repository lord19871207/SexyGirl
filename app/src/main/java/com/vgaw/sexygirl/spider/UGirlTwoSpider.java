package com.vgaw.sexygirl.spider;

import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.bean.BaseBean;
import com.vgaw.sexygirl.bean.UGirlTwoBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public abstract class UGirlTwoSpider extends DataSpider<BaseBean> {
    @Override
    protected void proPage(String response, DataSpiderListener listener) {
        BufferedReader reader = new BufferedReader(new StringReader(response));
        String temp = null;
        boolean hasMore = false;
        try {
            while ((temp = reader.readLine()) != null){
                if (temp.contains(getTemplete())){
                    UGirlTwoBean bean = new UGirlTwoBean();
                    bean.setUrl(Utils.findValueByKey(temp, "src").get(0));
                    bean.setStatus(UGirlTwoBean.STATUS_NONE);
                    dataList.add(bean);
                    hasMore = true;
                }
            }
            listener.onSuccess(hasMore);
        } catch (IOException e) {
            listener.onFailed();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    protected String getTemplete(){
        return "http://i.lesmao.com/i/les/T/UGirls";
    }

}
