<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-definition>
    <!DOCTYPE html>
    <html>
    <head>
        <title>Admin - stylehub</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/favicon.png">
        <link rel='stylesheet'
              href='https://fonts.googleapis.com/css?family=Lato%3A300%7CMontserrat%3A400%2C700%2C300%7CPlayfair+Display%3A400italic%7CPermanent+Marker%3A400&#038;subset=latin&#038;ver=4.5.9'
              type='text/css' media='all'/>
            <%-- font awesome --%>
        <script src="https://use.fontawesome.com/0db7afb289.js"></script>
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/lib/bootstrap.css</src>
            <src>/static/css/zion.common.form.css</src>
            <src>/static/css/lib/waitMe.css</src>
            <src>/static/css/zion.admin.common.css</src>
        </pack:style>
        <stripes:layout-component name="headMeta"/>
    </head>
    <body>
    <div id="wrapper">
        <%@ include file="/WEB-INF/jsp/include/adminNavTemplete.jsp" %>
        <div id="page-wrapper" class="zion-content">
            <stripes:layout-component name="contents"/>
        </div>
    </div>
    <%@ include file="/WEB-INF/jsp/include/commonScript.jsp" %>
    <%@ include file="/WEB-INF/jsp/include/adminScript.jsp" %>
    <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
        <%-- start common js lib --%>
        <src>/static/js/lib/waitMe.js</src>
        <src>/static/js/lib/xss.js</src>
        <src>/static/js/service/loadingservice.js</src>
        <%-- end lib --%>
    </pack:script>
    <stripes:layout-component name="footer"/>
    </body>
    </html>
</stripes:layout-definition>