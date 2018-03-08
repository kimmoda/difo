<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="FAQ">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.faq.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <c:set var="currencyName" scope="page" value="${actionBean.webConfig.digitalCurrencyShortname}"/>

        <div class="zion-general-page">
            <div class="page-header">
                <h1>Frequent Asked Questions</h1>
            </div>
            <div class="faq-selecter text-center row">
                <h2>
                    <span class="style-seeker-button active col-md-6">
                        For Fans
                    </span>
                    <span class="trendsetter-button col-md-6">
                        For Influencers
                    </span>
                </h2>
            </div>
            <article class="style-seeker">
                <h3>Why should I use STYLEHUB?</h3>
                <p>
                    Our community gathers the best fashion, beauty, and home decor influencers online. If you love
                    lifestyle posts and everything pretty, you are in the right place with us.
                </p>
                <p>
                    Here, you can discover hundreds of fashionistas, lifestyle influencers and thousands of mix and
                    match looks, makeup ideas and inspiration posts. No matter your taste, you will find your daily
                    inspirations on STYLEHUB.
                </p>
                <p>
                    But that is not all, the best part is you can follow your favorite influencers and earn rewards if
                    you support their campaigns. You are already liking, commenting and sharing the posts you love. How
                    cool is that now you can get rewarded for that?
                </p>
                <p>
                    Start participating in influencers campaigns and accumulate <c:out value="${currencyName}"/> coins
                    (our digital currency) and
                    exchange them for product or services on our online store.
                </p>

                <h3>I am a user, how do I join the community?</h3>
                <p>
                    We welcome every like-minded fashion and lifestyle fan to join us. To join our community and see how
                    it works,
                    please <a href="<c:url value="/apply" />" title="Apply Influencers">click here</a>.
                </p>

                <h3>
                    Who are The Influencers?
                </h3>
                <p>
                    Our influencers are professionals and insiders of the fashion, beauty and home decor industry. Some
                    of them are creative people wanting to showcase their unique style and work. Some of them are the
                    best fashion bloggers. Every one of them has joined the community because of their love and passion
                    for fashion and lifestyle topics.
                </p>

                <h3>How Can I Contact The Influencers?</h3>
                <p>
                    You can connect with any influencer simply by browsing their profile pages or connecting with them
                    via their social media pages.
                </p>

                <h3>What is the difference between a STYLEHUB "verified Influencer" and a "Fan" or general user?</h3>
                <p>
                    Being a "Verified Influencer" in STYLEHUB means that you are an expert. You have a great taste and
                    people should follow you. Becoming a Verified Influencer is by application only. STYLEHUB only
                    approve fashion, beauty and home decor influencers with a blog and great looks and professional
                    photography looks.
                </p>
                <p>
                    As a Verified Influencer you will have your own fan page and you will be able to personalise your
                    space
                    and to be followed and contacted. You can promote your brands and products and use your own
                    affiliate marketing links on your photos. Also, you will be able to promote your posts on STYLEHUB
                    to get more likes, comments or shares online.

                </p>
                <p>
                    Being a "Fan" means that you are a fashion and lifestyle lover. You love beautiful things and you
                    want to get inspired by the pros. You do not have a fashion blog but you love everything fashionable
                    and follow trends and the people that make the trends. You can also share your looks for fun to get
                    commented or liked by the community.
                </p>

                <h3>How Can I Follow the influencers I like?</h3>
                <p>
                    You can save your favorite influencers simply by checking their daily news feeds or by "following"
                    their profiles. When you like a photo or look from one of your favorite influencers posts, it will
                    automatically be saved to your personal profile page.
                </p>
                <h3>
                    How can I become a Verified Influencer?
                </h3>
                <p>
                    Are you a fashion, beauty or home decor blogger? If so, after you joined STYLEHUB as a general user
                    or "Fan" by login using Gmail or your Facebook account. You can apply to become a Verified
                    Influencer in your profile page. At the left hand side of the menu bar, you will see Become an
                    Influencer button, click it and give us one of your social media addresses. We will verify your
                    identity and approve your application. Once you are approved, you will see your new title
                    (Influencer) on your <b>Dashboard</b>.
                </p>
                <p>
                    You also can apply by <a href="<c:url value="/apply" />" title="Apply Influencers">click here</a>.
                </p>
                <h3>What are <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    <c:out value="${currencyName}"/> is our own STYLEHUB digital currency, and in the near future,
                    you will be able to exchange it for cash or other digital currencies like ether.
                </p>
                <h3>How can I earn <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    At the moment you can only earn <c:out value="${currencyName}"/> coins if you participate in
                    Influencer campaigns. <a href="<c:url value="/campaign" />" title="participate campaigns">Start
                    participating</a>
                </p>
                <h3>Can I buy <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    For now, <c:out value="${currencyName}"/> coins only can be earned if you participate in Influencer
                    campaigns, but in the near future, you will be able to buy coins with cash money or other digital
                    currencies like ether.
                </p>
                <h3>What can I do with <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    Right now, if you are a verified influencer on STYLEHUB, you only can use your <c:out
                        value="${currencyName}"/> coins to promote your posts by creating campaigns on our platform. If
                    you are a fan, you can accumulate your <c:out value="${currencyName}"/> coins as we are releasing
                    soon products and services you can exchange them for.
                </p>
                <h3>I am a fan, can I participate in campaigns and earn rewards?</h3>
                <p>
                    Yes, you can! the first step to participate is to sign up on STYLEHUB using any of your Facebook or
                    Google Account and then you will be able to start participating in campaigns and earning <c:out
                        value="${currencyName}"/> coins.
                </p>
                <h3>How many campaigns can I participate in?</h3>
                <p>
                    You can participate in as many campaigns as you want, but you only can participate once per
                    campaign.
                </p>
                <h3>Can I transfer <c:out value="${currencyName}"/> coins to other influencer or fan?</h3>
                <p>
                    NO, at this moment you cannot transfer <c:out value="${currencyName}"/> coins to anybody, you can
                    only earn coins by participating in campaigns. In the future this option will be available. Stay
                    tuned for our newsletter updates!
                </p>
                <h3>Can I exchange my <c:out value="${currencyName}"/> coins for cash money?</h3>
                <p>
                    NO, at the moment you cannot exchange your <c:out value="${currencyName}"/> coins for cash money or
                    other digital currencies. In the future this will be possible, we are working hard to make it
                    happen.
                </p>
            </article>
            <article class="trendsetter">
                <span class="trendsetter-title">
                    For Influencers
                </span>
                <h3>Why should I use STYLEHUB?</h3>
                <p>
                    STYLEHUB is a community-based platform for fashion, beauty and lifestyle influencers to connect and
                    help each other to get more engagement, likes and followers on their social media channels while
                    earning rewards.
                </p>
                <p>
                    How it works:
                </p>
                <p>
                    1. Help promote social media posts from influencers in the campaign page.
                </p>
                <p>
                    2. Accumulate and earn <c:out value="${currencyName}"/> coins (our own digital currency) and
                    exchange them for products or
                    services on our upcoming shop.
                </p>
                <p>
                    3. Create campaigns to promote your social media posts, get more likes, comments and shares online.
                </p>

                <h3>I am a lifestyle influencer, where can I find more information about joining?</h3>
                <p>
                    We are always ready to embrace new fashion, beauty and interior design influencers. We want to
                    support the best lifestyle influencers and help them get more exposure online, so joining our
                    community is by application only. To register with us and get approved, please <a
                        href="<c:url value="/apply" />" title="Apply Influencers">click here</a>.
                </p>

                <h3>What is the main benefit to use STYLEHUB?</h3>
                <p>
                    As a verified Influencer, you can use STYLEHUB to share your looks and posts everyday, and use your
                    own affiliate links to earn commission. Get noticed within the community by adding tags, share your
                    looks to Facebook and Instagram, or create short links to share in anywhere else.
                </p>
                <p>
                    STYLEHUB is also a platform to network and collaborate with like-minded people and help promote each
                    other while earning <c:out value="${currencyName}"/> coins (our own digital currency). Help promote,
                    like, comment or share the
                    influencer's post you love and be rewarded for it. Accumulate your rewards towards promoting your
                    own content or exchange your <c:out value="${currencyName}"/> coins on the STYLEHUB store.
                </p>

                <h3>How can I promote my personal brand?</h3>
                <p>
                    <b>Stay connected</b> - Write a bio, keep it updated, and make your personality and experience
                    shine.
                </p>
                <p>
                    <b>Stay visible</b> - Add links to your website and social media channels, and present your business
                    360&#186.
                </p>
                <p>
                    <b>Stay Stylish</b> - A picture is worth a thousand words! Upload your looks to the platform, and
                    showcase your
                    work, collections, and looks 24 / 7 to inspire your fans.
                </p>
                <p>
                    <b>Support fellow influencers</b> - The best way to be promoted is by promoting and supporting
                    others first. Once you have helped others, they will be happy to come and support your work too.
                </p>

                <h3>How to add a look?</h3>
                <p>
                    Within your profile, click +ADD LOOKS from your <b>Dashboard</b> page, follow the 3 simple steps.
                    Edit, add
                    your affiliate links, view or delete images and tag items.
                </p>

                <h3>What are the benefits for adding looks?</h3>
                <p>
                    Keeping your profile up to date will make you discoverable on the platform. By adding your looks to
                    STYLEHUB in a regular basis will keep your fans engaged with your looks and visiting your website
                    and social media profiles. You can tag your looks and use your own affiliate links to earn
                    commissions every time someone purchase from your link.
                </p>
                <p>
                    In addition, adding looks to your profile regularly will increase the chance to be promote by us on
                    our social media channels, campaigns and newsletters.
                </p>

                <h3>How would the fans contact me?</h3>
                <p>
                    If our fans love the looks and posts you created, they may comment under, give you a message via our
                    platform or contact you by email. Don not forget to check your inbox and email regularly.
                </p>
                <h3>What are <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    <c:out value="${currencyName}"/> is our own STYLEHUB digital currency, and in the near future,
                    you will be able to exchange it for cash or other digital currencies like ether.
                </p>
                <h3>How can I earn <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    At the moment you can only earn <c:out value="${currencyName}"/> coins if you participate in
                    Influencer campaigns. <a href="<c:url value="/campaign" />" title="participate campaigns">Start
                    participating</a>
                </p>
                <h3>Can I buy <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    For now, <c:out value="${currencyName}"/> coins only can be earned if you participate in Influencer
                    campaigns, but in the near future, you will be able to buy coins with cash money or other digital
                    currencies like ether.
                </p>
                <h3>What can I do with <c:out value="${currencyName}"/> coins?</h3>
                <p>
                    Right now, if you are a verified influencer on STYLEHUB, you only can use your <c:out
                        value="${currencyName}"/> coins to promote your posts by creating campaigns on our platform. If
                    you are a fan, you can accumulate your <c:out value="${currencyName}"/> coins as we are releasing
                    soon products and services you can exchange them for.
                </p>
                <h3>How can I publish a campaign?</h3>
                <p>
                    If you already have accumulated the <c:out value="${currencyName}"/> coins that cost to publish a
                    campaign, you can publish a campaign now by clicking <a href="javascript:void(0);"
                                                                            title="publish campaign"
                                                                            id="faq-pub-add-campaign">Here</a>.
                </p>
                <p>
                    Follow the three simple steps, choose the post you want to promote, choose your objective and then
                    click on "promote" easy!
                </p>
                <p>
                    Your campaign will be active for 24 hours only! You can publish as many
                    campaigns as you want if you have accumulated enough <c:out value="${currencyName}"/> coins.
                </p>
                <h3>How much <c:out value="${currencyName}"/> coins cost to publish a campaign?</h3>
                <p>
                    To publish a campaign on STYLEHUB costs <span class="create-campaign-cost"></span> <c:out
                        value="${currencyName}"/> coins and it will active for 24 hours. This may change in the future,
                    but no matter how many people participate in your campaign, each participant will randomly earn up
                    to <span class="max-coin-earn"></span> <c:out value="${currencyName}"/> coins. - If you are lucky
                    you
                    can get more. STYLEHUB will pay them for you. Very cool, huh? but no matter how many people
                    participate in your campaign, you will still pay <span class="create-campaign-cost"></span> <c:out
                        value="${currencyName}"/> coins for it.
                </p>
                <h3>How many campaigns can I participate in?</h3>
                <p>
                    You can participate in as many campaigns as you want, but you only can participate once per
                    campaign.
                </p>
                <h3>Can I transfer <c:out value="${currencyName}"/> coins to other influencer or fan?</h3>
                <p>
                    NO, at this moment you cannot transfer <c:out value="${currencyName}"/> coins to anybody, you can
                    only earn coins by participating in campaigns. In the future this option will be available. Stay
                    tuned for our newsletter updates!
                </p>
                <h3>Can I exchange my <c:out value="${currencyName}"/> coins for cash money?</h3>
                <p>
                    NO, at the moment you cannot exchange your <c:out value="${currencyName}"/> coins for cash money or
                    other digital currencies. In the future this will be possible, we are working hard to make it
                    happen.
                </p>
            </article>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <zion:uiComp name="faq"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>