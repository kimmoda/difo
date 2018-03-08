<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" bg="white-bg"
                       pageTitle="Promote your favorite influencers and promote yourself">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.campaign.css</src>
            <src>/static/css/zion.common.campaign.css</src>
            <src>/static/css/zion.common.campaignCard.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="header"></stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="zion-campaign-list-header-container">
            <span id="active-campaign-link" class="zion-campaign-status-link">Active</span>&nbsp;<span id="expired-campaign-link" class="zion-campaign-status-link">Expired</span>
            <div class="zion-campaign-list-header-content">
                <p class="text">Campaigns are active only for ${actionBean.campaignLifespan} hours.</p>
                <p class="text">Participate now and earn rewards.</p>
            </div>
        </div>
        <hr class="zion-campaign-list-header-divider" />
        <div id="campaign-list" class="grid">
            <div class="grid-col-sizer"></div>
            <div class="grid-gutter-sizer"></div>
        </div>
        <div id="promote-campaign-modal" class="modal fade zion-campaign-modal" tabindex="-1" role="dialog"></div>
        <div id="expired-campaign-list-modal" class="modal fade zion-campaign-modal" tabindex="-1" role="dialog"></div>
        <div class="zion-loading"></div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/campaignItem.js</src>
            <src>/static/js/template/campaignCard.js</src>
            <src>/static/js/template/participants.js</src>
            <src>/static/js/template/campaignPromoteProcessClaimReward.js</src>
            <src>/static/js/template/errorMessage.js</src>
            <src>/static/js/template/campaignPromoteProcessFinal.js</src>
            <src>/static/js/template/campaignPromoteProcessLoading.js</src>
            <src>/static/js/template/expiredCampaignList.js</src>
            <src>/static/js/template/expiredCampaignModal.js</src>
            <src>/static/js/service/promoteCampaignService.js</src>
            <src>/static/js/service/campaignListService.js</src>
        </pack:script>
        <zion:uiComp name="campaign"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
