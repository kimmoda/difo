<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Campaign">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.common.campaign.css</src>
            <src>/static/css/zion.common.campaignCard.css</src>
            <src>/static/css/zion.pub.sharedCampaign.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div id="shared-campaign" class="zion-campaign-card-container"></div>
        <div id="shared-campaign-processing-modal" class="modal fade zion-campaign-modal" tabindex="-1" role="dialog">
            <div class="modal-dialog campaign-modal-container" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="top-bar clearfix">
                            <div class="modal-close">
                                <i class="fa fa-lg fa-chevron-left" aria-hidden="true" data-dismiss="modal" aria-label="Close"></i>
                            </div>
                            <div class="mobile-top-bar-title">
                                <h4 class="mobile-top-bar-title-content">Campaign</h4>
                            </div>
                        </div>
                        <button type="button" class="modal-close-btn close" data-dismiss="modal" aria-label="Close">
                            <span class="cancel-text" aria-hidden="true">&times;</span>
                        </button>
                        <div id="zion-campaign-card-container" class="zion-campaign-card-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/sharedCampaign.js</src>
            <src>/static/js/template/participants.js</src>
            <src>/static/js/template/campaignPromoteProcessClaimReward.js</src>
            <src>/static/js/template/errorMessage.js</src>
            <src>/static/js/template/campaignPromoteProcessFinal.js</src>
            <src>/static/js/template/campaignPromoteProcessLoading.js</src>
            <src>/static/js/service/promoteCampaignService.js</src>
        </pack:script>
        <zion:uiComp name="sharedCampaign"></zion:uiComp>
        <script type="text/javascript">
            var campaignId = "${actionBean.campaignId}";
        </script>
    </stripes:layout-component>
</stripes:layout-render>