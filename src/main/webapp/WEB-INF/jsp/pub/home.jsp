<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" bg="white-bg" pageTitle="For Fashion | Lifestyle influencers. We help to get more exposure and monetize your work">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.home.css</src>
            <src>/static/css/zion.pub.stylehub.css</src>
            <src>/static/css/zion.common.campaign.css</src>
            <src>/static/css/zion.common.campaignCard.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="zion-home-container row">
            <section class="campaign-introduce">
                <h3 class="introduce-title">
                    About <c:out value="${appBrandName}"/>
                </h3>
                <p class="introduce-text">
                    <c:out value="${appBrandName}"/> is an ecosystem for <span class="zion-highlight">#like4like</span> and <span class="zion-highlight">#follow4follow</span>, to generate real engagement and organic growth.
                </p>
                <div class="intro-step-container">
                    <span class="intro-step-mark">1</span>
                    <img class="intro-icon intro-get-involved-icon" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/home_participant.png" alt="<c:out value="${appBrandName}"/> participant campaign">
                    <h4 class="intro-title">Participate in Campaigns</h4>
                    <p>Helping others helps you grow.</p>
                </div>
                <div class="intro-step-container">
                    <div class="intro-step-mark">2</div>
                    <img class="intro-icon intro-get-rewarded-icon" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/home_claim.png" alt="<c:out value="${appBrandName}"/> get rewarded">
                    <h4 class="intro-title">Claim Your Rewards</h4>
                    <p>Accumulate digital coins to create your own campaigns.</p>
                </div>
                <div class="intro-step-container">
                    <div class="intro-step-mark">3</div>
                    <img class="intro-icon intro-Promote-icon" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/home_campaign.png" alt="<c:out value="${appBrandName}"/> promote content">
                    <h4 class="intro-title">Create Your Campaign</h4>
                    <p>Other influencers will love to promote your content just like you love to give a hand!</p>
                </div>
                <a class="btn zion-btn-black" href="<c:url value="/apply" />">Join now</a>
            </section>

            <section class="latest-campaign">
                <h2 class="title">
                    Latest Campaigns
                </h2>
                <div id="campaign-list" class="grid">
                    <div class="grid-col-sizer"></div>
                    <div class="grid-gutter-sizer"></div>
                </div>
                <div class="see-more-btn">
                    <a href="<c:url value="/campaign" />" class="btn zion-btn-white">See more</a>
                </div>
            </section>
        </div>
        <div id="promote-campaign-modal" class="modal fade zion-campaign-modal" tabindex="-1" role="dialog"></div>
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
            <src>/static/js/service/promoteCampaignService.js</src>
            <src>/static/js/service/campaignListService.js</src>
        </pack:script>
        <zion:uiComp name="home"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>