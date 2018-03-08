<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="About Us">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.aboutUs.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="zion-about-us-container">
            <section class="header">
                <div class="header-content row">
                    <div class="col-xs-12 title">
                        What we do
                    </div>
                    <div class="col-xs-12 text top-banner-content">
                        <span class="quote"><i class="fa fa-2x fa-quote-left" aria-hidden="true"></i></span>
                        <span class="text">STYLEHUB connects style seekers with the hottest trendsetters around the world in one community, celebrating every style and taste.</span>
                        <span class="quote"><i class="fa fa-2x fa-quote-right quote-right"
                                               aria-hidden="true"></i></span>
                    </div>
                    <button class="btn zion-btn-black" id="join-us-btn">Join us today</button>
                </div>
            </section>
            <section class="main-content">
                <section class="background-pattern"></section>
                <section class="body container">
                    <section class="introduce row">
                        <div class="col-md-7">
                            <img class="banner-image img-responsive"
                                 src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/aboutus/2rd-banner.jpg"
                                 alt="2rd-banner-STYLEHUB">
                        </div>
                        <div class="col-md-5 content">
                            <h4 class="title">Why STYLEHUB</h4>
                            <p class="text">STYLEHUB is an integrated platform designed for
                                fashion stylists, influencers, and designers to showcase their ready-to-shop looks and
                                collections while inspiring and empowering "style seekers" to look their best every
                                day.</p>
                        </div>
                    </section>
                    <hr class="section-break">
                    <section class="become-trendsetter row">
                        <h4 class="col-md-12 title">Become a trendsetter</h4>
                        <div class="video-container col-md-12">
                            <iframe src="https://www.youtube.com/embed/ocpAK6fLAOI?control=0&rel=0&showinfo=0"
                                    allowfullscreen></iframe>
                        </div>
                        <div class="col-md-12 trendsetter-apply-content no-padding">
                            <p class="col-md-8 text">
                                Apply now for FREE to become a fashion-forward influencer, designer or stylist in our
                                community. You will have the opportunity to showcase your best looks, styles, and
                                collections, and connect with style seekers!
                            </p>
                            <div class="col-md-4">
                                <a href="<c:url value="/apply"/>" class="btn zion-btn-black">Become a trendsetter</a>
                            </div>
                        </div>
                    </section>
                    <hr class="section-break">
                    <section class="story row">
                        <h4 class="col-md-12 title">Our Story</h4>
                        <div class="col-md-12 image">
                            <img class="img-responsive"
                                 src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/aboutus/story-banner.jpg"
                                 alt="story-banner-STYLEHUB">
                        </div>
                        <div class="col-md-12 story-content">
                            <p class="text">
                                STYLEHUB was founded by co-founder and CEO, Tatiana Ceballos, and a small team of
                                investors
                                and developers. The team assembled to help Tatiana bring her vision and love for fashion
                                to
                                life.
                            </p>
                            <p class="text">
                                Tatiana decided to leave Colombia, her native country to start a new life in New
                                Zealand.
                                Her love for fashion and blogging as well as her prime marketing expertise drove her to
                                build the STYLEHUB platform in 2017.
                            </p>
                        </div>
                        <div class="col-md-12 hr-introduce no-padding">
                            <div class="col-md-6 hr-content">
                                <p class="text sub-title">About Tatiana</p>
                                <p class="text">
                                    Tatiana was born and raised in Medellin, Colombia-the fashion capital of Latin
                                    America.
                                    In addition to her love for fashion, Tatiana studied Advertising and Marketing.
                                    Later,
                                    she started her own PR and Advertising agency in Colombia, producing product
                                    launches
                                    and events for large brands.
                                </p>
                                <p class="text">
                                    Five years ago, Tatiana sold the company to her business partners and relocated to
                                    New
                                    Zealand with her husband to study English. Because English wasn't her native
                                    language,
                                    and she didn't have any friends or family in New Zealand, she started her own
                                    fashion
                                    and beauty blog to help her practice her English skills.
                                </p>
                                <p class="text">
                                    Tatiana's blog showcased looks, and fashion and beauty tips for any occasion.
                                    Through
                                    her research, she began learning a great deal about the blogging business and
                                    uncovered
                                    many different fashion icons, such as Olivia Palermo, Alexa Chung, and many highly
                                    talented independent designers and fashion bloggers who produced beautiful content
                                    and
                                    had a great sense of style.
                                </p>
                            </div>
                            <div class="col-md-6">
                                <img class="img-responsive"
                                     src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/aboutus/hr.jpg"
                                     alt="hr-STYLEHUB">
                            </div>
                        </div>
                        <div class="col-md-12 hr-idea">
                            <div class="idea-container">
                                <span class="quote"><i class="fa fa-2x fa-quote-left" aria-hidden="true"></i></span>
                                <span class="text">The vision behind STYLEHUB was to create a space where fashion professionals can collaborate and showcase their best work while also increasing exposure, build their fan and customer bases, and build inspiration surrounding beautiful clothing.</span>
                                <span class="quote"><i class="fa fa-2x fa-quote-right quote-right"
                                                       aria-hidden="true"></i></span>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <p class="text">
                                Thanks to her experience with blogging, Tatiana was contacted by a beauty company to
                                apply for a Digital Marketing position, marking the beginning of her career in digital
                                marketing. After blogging for approximately a year, and collaborating with some PR
                                agencies and brands, she decided to continue working as a digital marketer helping to
                                increase exposure for brands on social media, and to also get more conversions for their
                                e-Commerce stores using digital advertising and influencers.
                            </p>
                            <p class="text">
                                Then, taking her career a step further, Tatiana began working for one of the most
                                recognized
                                fashion designers in New Zealand, Vicky Taylor. Tatiana was responsible for managing the
                                digital marketing strategy and social media campaigns for the e-Commerce store. It was
                                then
                                that Tatiana realized that fashion and marketing was the perfect combination. That's how
                                STYLEHUB started.
                            </p>
                            <p class="text">
                                The vision behind STYLEHUB was to create a space where fashion professionals can
                                collaborate
                                and showcase their best work while also increasing exposure, build their fan and
                                customer
                                bases, and build inspiration surrounding beautiful clothing.
                            </p>
                        </div>
                    </section>
                </section>
                <section class="footer container-fluid">
                    <div class="footer-container row">
                        <div class="col-md-5 col-xs-12 no-padding">
                            <h4 class="title">Send us a message</h4>
                            <div class="zion-form">
                                <div class="form-group">
                                    <textarea type="text" rows="10" class="contact-message form-control"
                                              id="contact-message"></textarea>
                                    <div id="email-error-indicator" class="zion-input-error-text zion-hide"></div>
                                </div>
                            </div>
                            <div>
                                <button class="btn zion-btn-black pull-right contact-message-btn"
                                        id="contact-message-btn">
                                    <span class="message-btn-text" id="message-btn-text">Send</span>
                                    <i class="fa fa-check success-icon zion-hide" id="success-icon"
                                       aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>
                        <div class="col-md-2 col-xs-12 break">
                            <div class="line-break"></div>
                        </div>
                        <hr class="section-break">
                        <div class="col-md-5 col-xs-12 no-padding">
                            <div class="col-md-12 social-media-header no-padding">
                                <h4 class="title">Instagram</h4>
                                <a href="https://www.instagram.com/stylehubapp/?hl=en" target="_blank"
                                   class="btn zion-btn-white pull-right follow-instagram-btn" id="follow-instagram-btn">Follow</a>
                            </div>
                            <div class="col-md-12 social-media-photo no-padding">
                                <img class="img-responsive"
                                     src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/aboutus/instgram-bg.jpg"
                                     alt="instgram-bg-STYLEHUB">
                            </div>
                        </div>
                    </div>
                </section>
            </section>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <zion:uiComp name="aboutUs" includeFile="ui"></zion:uiComp>
        <script src="https://www.google.com/recaptcha/api.js?onload=recaptchaInit&render=explicit" async defer></script>
    </stripes:layout-component>
</stripes:layout-render>
