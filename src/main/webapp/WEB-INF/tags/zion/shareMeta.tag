<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<%@ attribute name="isStylehubSpecific" required="false" type="java.lang.String" %>

<c:choose>
    <c:when test="${!empty actionBean.shareMeta}">
        <c:if test="${isStylehubSpecific == 'false'}">
            <meta property="og:site_name" content="${actionBean.webConfig.appConfig.brandName}" />
        </c:if>
        <meta property="og:type" content="${actionBean.shareMeta.type}" />
	    <meta property="og:title" content="${actionBean.shareMeta.title}" />
	    <meta property="og:description" content="${actionBean.shareMeta.description}" />
	    <meta property="og:url" content="${actionBean.shareMeta.url}" />
	    <meta property="og:image" content="${actionBean.shareMeta.imageUrl}" />
	    <meta property="og:image:width" content="1024" />
	    <meta property="og:image:height" content="818" />    
    </c:when>
    <c:otherwise>
        <c:if test="${isStylehubSpecific == 'false'}">
            <meta property="og:site_name" content="${actionBean.webConfig.appConfig.brandName}" />
            <meta property="og:title" name="title" content="${actionBean.webConfig.appConfig.brandName}" />
        </c:if>
        <meta property="og:type" content="website" />
        <meta property="og:description" name="description" content="Help promote and get promoted by fellow fashion & lifestyle influencers. Increase social engagement, followers, and visibility online while earning rewards." />
        <meta property="og:url" name="url" content="${actionBean.webConfig.hostDomain}" />
        <meta property="og:image" name="image" content="${actionBean.webConfig.appConfig.logoUrl}" />
        <meta property="og:image:width" content="1024" />
        <meta property="og:image:height" content="818" />
    </c:otherwise>
</c:choose>
