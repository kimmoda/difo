(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['campaignCard'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;

  return "                    <div class=\"zion-campaign-status-container\">\n                        <span class=\"label "
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.campaignStatus : depth0)) != null ? stack1.cssClass : stack1), depth0))
    + "\">"
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.campaignStatus : depth0)) != null ? stack1.value : stack1), depth0))
    + "</span>\n                    </div>\n";
},"3":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        <img class=\"zion-campaign-image img-responsive center-block\"\n                             src=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1), depth0))
    + "\"\n                             alt=\"post preview image\">\n";
},"5":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                            <img class=\"zion-campaign-source-icon\" src=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.favicon : stack1), depth0))
    + "\"\n                                 alt=\"social source icon\">\n";
},"7":function(container,depth0,helpers,partials,data) {
    return "                            <i class=\"fa fa-link zion-campaign-source-icon\" aria-hidden=\"true\"></i>\n";
},"9":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                            <p class=\"zion-campaign-source-title\">\n                                "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.title : stack1), depth0))
    + "\n                            </p>\n";
},"11":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        <p class=\"zion-campaign-source-description\">\n                            "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n                        </p>\n";
},"13":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                        <p class=\"zion-campaign-source-description\">\n                            "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.url : stack1), depth0))
    + "\n                        </p>\n";
},"15":function(container,depth0,helpers,partials,data) {
    var helper;

  return "                    <p class=\"zion-campaign-description\">\n                        "
    + container.escapeExpression(((helper = (helper = helpers.description || (depth0 != null ? depth0.description : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"description","hash":{},"data":data}) : helper)))
    + "\n                    </p>\n";
},"17":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "                    <a href=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.url : stack1), depth0))
    + "\" class=\"zion-link-sm\" target=\"_blank\">View Original Post</a>\n";
},"19":function(container,depth0,helpers,partials,data) {
    return "zion-disabled ";
},"21":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "                            <div class=\"btn btn-default zion-btn-white campaign-share-btn\">\n                                Invite My Friends\n                            </div>\n                            <div class=\"user-share-container\">\n                                <div id=\"zion-campaign-social-share-container\"\n                                     class=\"zion-campaign-social-share-container zion-social-share-plugin-container\" data-share-url=\""
    + alias4(((helper = (helper = helpers.shortUrl || (depth0 != null ? depth0.shortUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"shortUrl","hash":{},"data":data}) : helper)))
    + "\"\n                                     data-share-title=\""
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\" data-share-description=\""
    + alias4(((helper = (helper = helpers.content || (depth0 != null ? depth0.content : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"content","hash":{},"data":data}) : helper)))
    + "\"\n                                     data-share-image=\""
    + alias4(container.lambda(((stack1 = (depth0 != null ? depth0.photo : depth0)) != null ? stack1.url : stack1), depth0))
    + "\"></div>\n                            </div>\n";
},"23":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : (container.nullContext || {}),((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.isShare : stack1),{"name":"if","hash":{},"fn":container.program(24, data, 0),"inverse":container.program(27, data, 0),"data":data})) != null ? stack1 : "");
},"24":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "                                <button class=\"zion-campaign-promote-btn btn zion-btn-black "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(19, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\"\n                                        id=\"zion-campaign-promote-btn\" "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(25, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + ">\n                                    "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n                                </button>\n";
},"25":function(container,depth0,helpers,partials,data) {
    return " disabled";
},"27":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=depth0 != null ? depth0 : (container.nullContext || {});

  return "                                <a class=\"zion-campaign-promote-btn btn zion-btn-black "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(19, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\" id=\"zion-campaign-promote-btn\"\n                                   href=\""
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(28, data, 0),"inverse":container.program(30, data, 0),"data":data})) != null ? stack1 : "")
    + "\"\n                                   target=\"_blank\">\n                                    "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n                                </a>\n";
},"28":function(container,depth0,helpers,partials,data) {
    return "javascript:void(0);";
},"30":function(container,depth0,helpers,partials,data) {
    var stack1;

  return container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.url : stack1), depth0));
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression, alias5=container.lambda;

  return "<div class=\"modal-dialog campaign-modal-container\" role=\"document\">\n    <div class=\"modal-content\">\n        <div class=\"modal-body\">\n            <div class=\"top-bar clearfix\">\n                <div class=\"modal-close\">\n                    <i class=\"fa fa-lg fa-chevron-left\" aria-hidden=\"true\" data-dismiss=\"modal\" aria-label=\"Close\"></i>\n                </div>\n                <div class=\"mobile-top-bar-title\">\n                    <h4 class=\"mobile-top-bar-title-content\">Campaign</h4>\n                </div>\n            </div>\n            <button type=\"button\" class=\"modal-close-btn close\" data-dismiss=\"modal\" aria-label=\"Close\">\n                <span class=\"cancel-text\" aria-hidden=\"true\">&times;</span>\n            </button>\n            <div class=\"zion-campaign-card-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.campaignStatus : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <h4 class=\"zion-campaign-title-content\">\n                    "
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\n                </h4>\n                <p class=\"zion-campaign-create-date-time\">\n                    "
    + alias4((helpers.dateTimeFormat || (depth0 && depth0.dateTimeFormat) || alias2).call(alias1,(depth0 != null ? depth0.creationDate : depth0),{"name":"dateTimeFormat","hash":{},"data":data}))
    + "\n                </p>\n                <div class=\"zion-campaign-author-info-container\">\n                    <a href=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.profileUrl : stack1), depth0))
    + "\" target=\"_blank\"><img\n                            class=\"user-profile-link img-circle img-responsive zion-campaign-avatar\"\n                            src=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\"\n                            alt=\"avatar\"/></a>\n                    <span class=\"user-profile-link zion-campaign-author-name\"\n                          title=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">By "
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "</span>\n                </div>\n                <div class=\"campaign-image-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </div>\n                <blockquote class=\"zion-campaign-source-container\">\n                    <div class=\"row\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.favicon : stack1),{"name":"if","hash":{},"fn":container.program(5, data, 0),"inverse":container.program(7, data, 0),"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.title : stack1),{"name":"if","hash":{},"fn":container.program(9, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                    </div>\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.description : stack1),{"name":"if","hash":{},"fn":container.program(11, data, 0),"inverse":container.program(13, data, 0),"data":data})) != null ? stack1 : "")
    + "                </blockquote>\n\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.description : depth0),{"name":"if","hash":{},"fn":container.program(15, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <hr/>\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.showOriUrl : depth0),{"name":"if","hash":{},"fn":container.program(17, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <div class=\"promote-action-section\">\n                    <div class=\""
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.disabled : depth0),{"name":"if","hash":{},"fn":container.program(19, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\">\n                        <h4 class=\"zion-campaign-subtitle\">How to participate</h4>\n                        <p class=\"zion-campaign-rule\">\n                            1. Click on the button below. A new window will open.\n                        </p>\n                        <p class=\"zion-campaign-rule\">\n                            2. "
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.taskType : depth0)) != null ? stack1.description : stack1), depth0))
    + "\n                        </p>\n                        <p class=\"zion-campaign-rule zion-highlight\">\n                            3. Once you are done, come back to this page to claim your "
    + alias4(((helper = (helper = helpers.coinName || (depth0 != null ? depth0.coinName : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"coinName","hash":{},"data":data}) : helper)))
    + " coins reward.\n                        </p>\n                    </div>\n                    <div class=\"zion-campaign-promote-btn-container\" id=\"zion-campaign-promote-btn-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.isMine : depth0),{"name":"if","hash":{},"fn":container.program(21, data, 0),"inverse":container.program(23, data, 0),"data":data})) != null ? stack1 : "")
    + "                    </div>\n                </div>\n                <hr/>\n                <div class=\"campaign-history-container\">\n                    <h4 class=\"zion-campaign-subtitle\">Campaign participants</h4>\n                    <div id=\"campaign-participants\" class=\"campaign-participants\"></div>\n                    <div class=\"zion-loading\"></div>\n                    <div class=\"participants-load-more-button-container load-more-button-container zion-hide\" id=\"participants-load-more-button-container\">\n                        <span class=\"participants-load-more-button load-more-button\" id=\"participants-load-more-button\">View all participants</span>\n                    </div>\n                    <div id=\"loading-participants-error\" class=\"loading-participants-error alert alert-danger zion-hide\" role=\"alert\">\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
},"useData":true});
})();