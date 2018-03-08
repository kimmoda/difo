package com.zion.action.pub;

import com.zion.action.base.PubBaseActionBean;
import com.zion.appswitch.AppSwitch;
import com.zion.common.AppConfig;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding(HomeActionBean.URL)
public class HomeActionBean extends PubBaseActionBean {

    public static final String ACTION = "home";
    public static final String URL = "/"+ ACTION;

    @Override
    public Resolution view() {
        AppSwitch appSwitch = AppConfig.getInstance().getEnabledApp();
        switch (appSwitch) {
            case CAMPAIGN: {
                return new ForwardResolution(getPubView("home"));
            }
            case FEED: {
                return new ForwardResolution(getPubView("homeLegacy"));
            }
            default: {
                return new ForwardResolution(getPubView("error"));
            }
        }
    }
    
    @Override
    public String getAction() {
        return ACTION;
    }

}
