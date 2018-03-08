package com.zion.action.pub;

import com.zion.action.base.PubBaseActionBean;
import com.zion.action.model.ShareMeta;
import com.zion.common.UrlPreviewField;
import com.zion.mongo.db.repository.CommonConfigRepository;
import com.zion.task.PromotionTask;
import com.zion.task.service.PromotionTaskService;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

@UrlBinding(CampaignActionBean.URL)
public class CampaignActionBean extends PubBaseActionBean {
    private static final String ACTION = "campaign";
    private static final String SHARED_VIEW = "sharedCampaign";
    public static final String URL = "/" + ACTION;
    private String campaignId;

    @Inject
    private CommonConfigRepository configRepo;

    @Inject
    private PromotionTaskService promotionTaskService;

    @Override
    public String getAction() {
        return ACTION;
    }

    public int getCampaignLifespan() {
        String lifeSpanValue = this.configRepo.getEnabledConfigByKey("promotion.task.lifespan").getValue();
        return Integer.valueOf(lifeSpanValue);
    }

    @Override
    public Resolution view() {
        if (StringUtils.isBlank(campaignId)) {
            return super.view();
        } else {
            initCampaignShareMeta();
            return new ForwardResolution(getPubView(SHARED_VIEW));
        }
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    private void initCampaignShareMeta() {
        PromotionTask promotionTask = this.promotionTaskService.getById(this.campaignId, null);
        if (promotionTask != null && promotionTask.isEnabled()) {
            String previewImageStr = promotionTask.getPostUrlPreview().get(UrlPreviewField.IMAGE.getFieldName());
            String faviconStr = promotionTask.getPostUrlPreview().get(UrlPreviewField.FAVICON.getFieldName());
            String campaignImage = StringUtils.isNotBlank(previewImageStr) ? previewImageStr : faviconStr;
            String url = promotionTask.getShortUrl();
            String title = promotionTask.getTitle();
            String description = promotionTask.getDescription();
            ShareMeta shareMeta = new ShareMeta(title, description, url, "campaign", campaignImage);
            this.setShareMeta(shareMeta);
        }
    }
}
