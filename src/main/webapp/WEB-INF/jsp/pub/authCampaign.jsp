<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.auth.campaign.css</src>
            <src>/static/css/zion.common.campaign.css</src>
            <src>/static/css/zion.common.campaignHistory.css</src>
            <src>/static/css/zion.common.campaignCard.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <div id="my-coin-account"></div>
        <div class="my-active-campaign">
            <div id="campaign-list" class="grid">
                <div class="grid-col-sizer"></div>
                <div class="grid-gutter-sizer"></div>
            </div>
            <div class="zion-loading"></div>
        </div>
        <div class="my-finished-task-container">
            <div class="title">
                Campaigns I have participated in
            </div>
            <div class="finished-list row">

            </div>

        </div>
        <div id="simple-campaign-modal" class="modal fade zion-campaign-modal" tabindex="-1" role="dialog"></div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/service/campaignListService.js</src>
            <src>/static/js/service/taskFinishedListService.js</src>
            <src>/static/js/service/simplecampaignservice.js</src>
            <src>/static/js/template/campaignItem.js</src>
            <src>/static/js/template/finishedCampaignItem.js</src>
            <src>/static/js/template/participants.js</src>
            <src>/static/js/template/errorMessage.js</src>
            <src>/static/js/template/simpleCampaignCard.js</src>
            <src>/static/js/template/userCoinAccount.js</src>
        </pack:script>
        <zion:uiComp name="authCampaign"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>