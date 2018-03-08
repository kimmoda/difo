<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>


<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.common.feed.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="row">
            <div class="col-md-6">
                <h1>h1. Header 1</h1>
                <h2>h2. Header 2</h2>
                <h3>h3. Header 3</h3>
                <h4>h4. Header 4</h4>
                <h5>h5. Header 5</h5>
                <h6>h6. Header 6</h6>
            </div>
            <div class="col-md-6">
                <h1>Text</h1>
                <p>Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis
                    parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula.</p>
                <p>Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis
                    parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula.</p>
                <blockquote>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.
                    <small>Hello</small>
                </blockquote>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <h1 class="col-md-12">Color</h1>
                <div class="col-md-3">
                    <label class="h6">Dark white</label>
                    <div class="color-block dark-white" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Grey extra lightest</label>
                    <div class="color-block grey-extra-lightest" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Grey extra lighter</label>
                    <div class="color-block grey-extra-lighter" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Grey extra light</label>
                    <div class="color-block grey-extra-light" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Grey light</label>
                    <div class="color-block grey-light" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Grey dark</label>
                    <div class="color-block grey-dark" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Light black</label>
                    <div class="color-block light-black" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Black</label>
                    <div class="color-block black" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Red</label>
                    <div class="color-block red" style="width:20px; height: 20px;"></div>
                </div>
                <div class="col-md-3">
                    <label class="h6">Blue</label>
                    <div class="color-block blue" style="width:20px; height: 20px;"></div>
                </div>
            </div>
            <div class="col-md-6">
                <h1>Link(unfinished, don't use)</h1>
                <div class="col-md-12 margin-bottom-10">
                    <a class="zion-link-ex" href="#">Ex-Small text Link</a>
                </div>
                <div class="col-md-12 margin-bottom-10">
                    <a class="zion-link-sm" href="#">Small text Link</a>
                </div>
                <div class="col-md-12">
                    <a class="zion-link-lg" href="#">Large text Link</a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <h1>Form</h1>
                <form class="zion-form">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Email address</label>
                        <input type="text" class="form-control" id="exampleInputEmail1" placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Name <span class="zion-form-compulsory">*</span></label>
                        <input type="text" class="form-control zion-input-error-indicator" id="exampleInputPassword1"
                               placeholder="Name">
                        <div class="zion-input-error-text">Required</div>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Disable</label>
                        <input type="text" class="form-control" id="exampleDisable" placeholder="Text" disabled>
                    </div>
                </form>
            </div>
            <div class="col-md-6">
                <h1>Button(unfinished, don't use)</h1>
                <div class="col-md-12 no-padding" style="margin-bottom: 30px; padding-bottom: 20px; border-bottom: 1px solid #ccc">
                    <div class="col-md-12 margin-bottom-10">
                        <div class="col-md-4">
                            <h4>White</h4>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-small zion-btn-white">Small</button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-large zion-btn-white">Large</button>
                        </div>
                    </div>
                    <div class="col-md-12 margin-bottom-10">
                        <div class="col-md-4">
                            <h4>Black</h4>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-small zion-btn-black">Send</button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-large zion-btn-black">Send</button>
                        </div>
                    </div>
                    <div class="col-md-12 margin-bottom-10">
                        <div class="col-md-4">
                            <h4>Grey</h4>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-small zion-btn-grey">Follow</button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-large zion-btn-grey">Follow</button>
                        </div>
                    </div>
                    <div class="col-md-12 margin-bottom-10">
                        <div class="col-md-4">
                            <h4>Blue</h4>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-small zion-btn-blue">Following</button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-large zion-btn-blue">Following</button>
                        </div>
                    </div>
                    <div class="col-md-12 margin-bottom-10">
                        <div class="col-md-4">
                            <h4>Red</h4>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-small zion-btn-red">Unfollow</button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn zion-btn-large zion-btn-red">Unfollow</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 no-padding" style="margin-bottom: 30px; padding-bottom: 30px; border-bottom: 1px solid #ccc">
                    <div class="col-md-12 margin-bottom-10">
                        <div class="col-md-4">
                            <p>Button with icon</p>
                        </div>
                        <div class="col-md-8">
                            <button class="btn zion-btn-icon zion-btn-blue">
                                <i class="fa fa-facebook" aria-hidden="true" style="background-color: #4d699f"></i>
                                <span>Sign in with facebook</span>
                            </button>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="col-md-8 col-md-offset-4">
                            <button class="btn zion-btn-icon zion-btn-red">
                                <i class="fa fa-google-plus" aria-hidden="true" style="background-color: #bc2732"></i>
                                <span>Sign in with google</span>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-12 no-padding" style="margin-bottom: 30px">
                    <button class="btn btn-default">Default</button>
                    <button class="btn btn-primary">Primary</button>
                    <button class="btn btn-success">Success</button>
                    <button class="btn btn-info">Info</button>
                    <button class="btn btn-warning">Warning</button>
                    <button class="btn btn-danger">Danger</button>
                    <button class="btn btn-link">Link</button>
                    <br/>
                    <br/>
                    <button class="btn btn-default-outline">Default</button>
                    <button class="btn btn-primary-outline">Primary</button>
                    <button class="btn btn-success-outline">Success</button>
                    <button class="btn btn-info-outline">Info</button>
                    <button class="btn btn-warning-outline">Warning</button>
                    <button class="btn btn-danger-outline">Danger</button>
                    <button class="btn btn-link-outline">Link</button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <h1 class="col-md-12 no-padding">Feed</h1>
                <div class="col-md-6 zion-feed-container">
                    <div class="zion-feed-header">
                        <div class="author-info-container">
                            <img class="img-circle image-avatar img-responsive"
                                 src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8BBqAKCyVX8yIMRIdNl-Nba410XCLQ4Fit-pZMP27mOlJo2t-">
                            <div class="author-name">
                                bob
                            </div>

                        </div>
                    </div>
                    <div class="zion-feed-content">
                        <div class="feed-image">
                            <img class="main-image"
                                 src="https://res.cloudinary.com/dbhqd0uit/image/upload/w_600,c_scale/uuz9vcoi252u1nzkx7c0.jpg"
                                 alt="title">
                        </div>
                    </div>
                    <div class="zion-feed-footer">
                        <div class="action-container">
                            <div class="col-xs-7 tag">
                                <span>#Casual</span>
                                <span>#Boho</span>
                                <span>#Glamorous</span>
                            </div>
                            <div class="col-xs-5 interact">
                                <div class="pull-right">
                                    <i class="like fa fa-heart">&nbsp;103</i>
                                    <i class="fa fa-comment-o">&nbsp;110</i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </stripes:layout-component>
</stripes:layout-render>