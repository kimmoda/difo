<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Errors">
    <stripes:layout-component name="contents">
        <div class="zion-general-page">
            <div class="page-header">
                <h1>Service Unavailable</h1>
            </div>
            <article>
                <p>Oops! Something went wrong!</p>
                <p>
                    Help us improve your experience by sending an error report 
                    <a href="mailto:<c:out value="${appSupportEmail}"/>" title="error support"><c:out value="${appSupportEmail}"/></a>
                    or back to <a href="<c:url value="/" />" title="<c:out value="${appBrandName}"/>"> <c:out value="${appBrandName}"/></a>.
                </p>
            </article>
        </div>
    </stripes:layout-component>
</stripes:layout-render>