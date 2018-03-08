(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['sharedCampaign'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {}), alias4=helpers.helperMissing, alias5="function";

  return "    <div class=\"zion-error-message-modal-container\">\n        <p class=\"error-title\">\n            Uh Oh!\n        </p>\n        <p class=\"error-message\">\n            This campaign has been <b>DELETED</b> by the Author. Click the button below to return to "
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.appConfig : depth0)) != null ? stack1.brandName : stack1), depth0))
    + ".\n        </p>\n        <p class=\"error-message\">\n            If you are still having problems, contact us at <a\n                href=\"mailto:"
    + alias2(((helper = (helper = helpers.emailHelper || (depth0 != null ? depth0.emailHelper : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"emailHelper","hash":{},"data":data}) : helper)))
    + "\"><b>"
    + alias2(((helper = (helper = helpers.emailHelper || (depth0 != null ? depth0.emailHelper : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"emailHelper","hash":{},"data":data}) : helper)))
    + "</b></a> and our friendly support team\n            will get\n            back to you shortly.\n        </p>\n\n        <div class=\"btn btn-default zion-btn-white homepage-btn\">\n            Back to "
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.appConfig : depth0)) != null ? stack1.brandName : stack1), depth0))
    + "\n        </div>\n    </div>\n";
},"3":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression, alias5=container.lambda;

  return "\n    <div class=\"zion-campaign-status-container\">\n        <span class=\"label "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.campaignStatus : depth0),{"name":"if","hash":{},"fn":container.program(4, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\">"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.campaignStatus : depth0),{"name":"if","hash":{},"fn":container.program(6, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "</span>\n    </div>\n    <h4 class=\"zion-campaign-title-content\">\n        "
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\n    </h4>\n    <p class=\"zion-campaign-create-date-time\">\n        "
    + alias4((helpers.dateTimeFormat || (depth0 && depth0.dateTimeFormat) || alias2).call(alias1,(depth0 != null ? depth0.creationDate : depth0),{"name":"dateTimeFormat","hash":{},"data":data}))
    + "\n    </p>\n    <div class=\"zion-campaign-author-info-container\">\n        <a href=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.profileUrl : stack1), depth0))
    + "\" target=\"_blank\"><img\n                class=\"user-profile-link img-circle img-responsive zion-campaign-avatar\"\n                src=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\"\n                alt=\"avatar\"/></a>\n        <span class=\"user-profile-link zion-campaign-author-name\"\n              title=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">By "
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "</span>\n    </div>\n    <div class=\"campaign-image-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1),{"name":"if","hash":{},"fn":container.program(8, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    </div>\n    <blockquote class=\"zion-campaign-source-container\">\n        <div class=\"row\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.favicon : stack1),{"name":"if","hash":{},"fn":container.program(10, data, 0),"inverse":container.program(12, data, 0),"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.title : stack1),{"name":"if","hash":{},"fn":container.program(14, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "        </div>\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.description : stack1),{"name":"if","hash":{},"fn":container.program(16, data, 0),"inverse":container.program(18, data, 0),"data":data})) != null ? stack1 : "")
    + "    </blockquote>\n\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.description : depth0),{"name":"if","hash":{},"fn":container.program(20, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    <hr/>\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(22, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    <div class=\"promote-action-section\">\n        <div class=\""
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(24, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\">\n            <h4 class=\"zion-campaign-subtitle\">How to participate</h4>\n            <p class=\"zion-campaign-rule\">\n                1. Click on the button below. A new window will open.\n            </p>\n            <p class=\"zion-campaign-rule\">\n                2. "
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n            </p>\n            <p class=\"zion-campaign-rule zion-highlight\">\n                3. Once you are done, come back to this page to claim your "
    + alias4(((helper = (helper = helpers.coinName || (depth0 != null ? depth0.coinName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"coinName","hash":{},"data":data}) : helper)))
    + " coins reward.\n            </p>\n        </div>\n        <div class=\"zion-campaign-promote-btn-container\" id=\"zion-campaign-promote-btn-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.isMine : depth0),{"name":"if","hash":{},"fn":container.program(26, data, 0),"inverse":container.program(28, data, 0),"data":data})) != null ? stack1 : "")
    + "        </div>\n    </div>\n    <hr/>\n    <div class=\"campaign-history-container\">\n        <h4 class=\"zion-campaign-subtitle\">Campaign participants</h4>\n        <div id=\"campaign-participants\" class=\"campaign-participants\"></div>\n        <div class=\"zion-loading\"></div>\n        <div class=\"load-more-button-container zion-hide\" id=\"participants-load-more-button-container\">\n            <span class=\"participants-load-more-button load-more-button\" id=\"participants-load-more-button\">View all participants</span>\n        </div>\n        <div id=\"loading-participants-error\" class=\"loading-participants-error alert alert-danger zion-hide\"\n             role=\"alert\">\n        </div>\n    </div>\n";
},"4":function(container,depth0,helpers,partials,data) {
    var stack1;

  return container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.campaignStatus : depth0)) != null ? stack1.cssClass : stack1), depth0));
},"6":function(container,depth0,helpers,partials,data) {
    var stack1;

  return container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.campaignStatus : depth0)) != null ? stack1.value : stack1), depth0));
},"8":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "            <img class=\"zion-campaign-image img-responsive center-block\"\n                 src=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1), depth0))
    + "\"\n                 alt=\"\">\n";
},"10":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                <img class=\"zion-campaign-source-icon\" src=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.favicon : stack1), depth0))
    + "\"\n                     alt=\"social source icon\">\n";
},"12":function(container,depth0,helpers,partials,data) {
    return "                <i class=\"fa fa-link zion-campaign-source-icon\" aria-hidden=\"true\"></i>\n";
},"14":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                <p class=\"zion-campaign-source-title\">\n                    "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.title : stack1), depth0))
    + "\n                </p>\n";
},"16":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "            <p class=\"zion-campaign-source-description\">\n                "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n            </p>\n";
},"18":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "            <p class=\"zion-campaign-source-description\">\n                "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.url : stack1), depth0))
    + "\n            </p>\n";
},"20":function(container,depth0,helpers,partials,data) {
    var helper;

  return "        <p class=\"zion-campaign-description\">\n            "
    + container.escapeExpression(((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"description","hash":{},"data":data}) : helper)))
    + "\n        </p>\n";
},"22":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "        <a href=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.url : stack1), depth0))
    + "\" class=\"zion-link-sm\" target=\"_blank\">View Original Post</a>\n";
},"24":function(container,depth0,helpers,partials,data) {
    return "zion-disabled ";
},"26":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                <div class=\"btn btn-default zion-btn-white campaign-share-btn\">\n                    Invite My Friends\n                </div>\n                <div class=\"user-share-container\">\n                    <div id=\"zion-campaign-social-share-container\"\n                         class=\"zion-campaign-social-share-container zion-social-share-plugin-container\"\n                         data-share-url=\""
    + alias4(((helper = (helper = helpers.shortUrl || (depth0 != null ? depth0.shortUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"shortUrl","hash":{},"data":data}) : helper)))
    + "\"\n                         data-share-title=\""
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\" data-share-description=\""
    + alias4(((helper = (helper = helpers.content || (depth0 != null ? depth0.content : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"content","hash":{},"data":data}) : helper)))
    + "\"\n                         data-share-image=\""
    + alias4(container.lambda(((stack1 = (depth0 != null ? depth0.photo : depth0)) != null ? stack1.url : stack1), depth0))
    + "\"></div>\n                </div>\n";
},"28":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.isShare : stack1),{"name":"if","hash":{},"fn":container.program(29, data, 0),"inverse":container.program(32, data, 0),"data":data})) != null ? stack1 : "");
},"29":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "                    <button class=\"zion-campaign-promote-btn btn zion-btn-black "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(24, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\"\n                            id=\"zion-campaign-promote-btn\" "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(30, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ">\n                        "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n                    </button>\n";
},"30":function(container,depth0,helpers,partials,data) {
    return " disabled";
},"32":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "                    <a class=\"zion-campaign-promote-btn btn zion-btn-black "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(24, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\" id=\"zion-campaign-promote-btn\"\n                       href=\""
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(33, data, 0),"inverse":container.program(35, data, 0),"data":data})) != null ? stack1 : "")
    + "\"\n                       target=\"_blank\">\n                        "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n                    </a>\n";
},"33":function(container,depth0,helpers,partials,data) {
    return "javascript:void(0);";
},"35":function(container,depth0,helpers,partials,data) {
    var stack1;

  return container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.url : stack1), depth0));
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.isDeleted : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.program(3, data, 0),"data":data})) != null ? stack1 : "");
},"useData":true});
})();