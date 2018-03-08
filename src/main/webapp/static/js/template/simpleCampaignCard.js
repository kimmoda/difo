(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['simpleCampaignCard'] = template({"1":function(container,depth0,helpers,partials,data) {
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
    + "\"\n                             alt=\"\">\n";
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
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3=container.escapeExpression, alias4=container.lambda;

  return "<div class=\"modal-dialog campaign-modal-container\" role=\"document\">\n    <div class=\"modal-content\">\n        <div class=\"modal-body\">\n            <div class=\"top-bar clearfix\">\n                <div class=\"modal-close\">\n                    <i class=\"fa fa-lg fa-chevron-left\" aria-hidden=\"true\" data-dismiss=\"modal\" aria-label=\"Close\"></i>\n                </div>\n                <div class=\"mobile-top-bar-title\">\n                    <h4 class=\"mobile-top-bar-title-content\">Campaign</h4>\n                </div>\n            </div>\n            <button type=\"button\" class=\"modal-close-btn close\" data-dismiss=\"modal\" aria-label=\"Close\">\n                <span class=\"cancel-text\" aria-hidden=\"true\">&times;</span>\n            </button>\n            <div class=\"zion-campaign-card-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.campaignStatus : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <h4 class=\"zion-campaign-title-content\">\n                    "
    + alias3(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === "function" ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "\n                </h4>\n                <p class=\"zion-campaign-create-date-time\">\n                    "
    + alias3((helpers.dateTimeFormat || (depth0 && depth0.dateTimeFormat) || alias2).call(alias1,(depth0 != null ? depth0.creationDate : depth0),{"name":"dateTimeFormat","hash":{},"data":data}))
    + "\n                </p>\n                <div class=\"zion-campaign-author-info-container\">\n                    <a href=\""
    + alias3(alias4(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.profileUrl : stack1), depth0))
    + "\" target=\"_blank\"><img class=\"user-profile-link img-circle img-responsive zion-campaign-avatar\"\n                                                                          src=\""
    + alias3(alias4(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\"\n                                                                          alt=\"avatar\"/></a>\n                    <span class=\"user-profile-link zion-campaign-author-name\"\n                          title=\""
    + alias3(alias4(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">By "
    + alias3(alias4(((stack1 = (depth0 != null ? depth0.creator : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "</span>\n                </div>\n                <div class=\"campaign-image-container\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.image : stack1),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                </div>\n                <blockquote class=\"zion-campaign-source-container\">\n                    <div class=\"row\">\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.favicon : stack1),{"name":"if","hash":{},"fn":container.program(5, data, 0),"inverse":container.program(7, data, 0),"data":data})) != null ? stack1 : "")
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.title : stack1),{"name":"if","hash":{},"fn":container.program(9, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                    </div>\n"
    + ((stack1 = helpers["if"].call(alias1,((stack1 = (depth0 != null ? depth0.postUrlPreview : depth0)) != null ? stack1.description : stack1),{"name":"if","hash":{},"fn":container.program(11, data, 0),"inverse":container.program(13, data, 0),"data":data})) != null ? stack1 : "")
    + "                </blockquote>\n\n"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.description : depth0),{"name":"if","hash":{},"fn":container.program(15, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "                <hr>\n                <div class=\"campaign-history-container\">\n                    <h4 class=\"zion-campaign-subtitle\">Campaign participants</h4>\n                    <div id=\"campaign-participants\"></div>\n                    <div class=\"zion-loading\"></div>\n                    <div class=\"load-more-button-container zion-hide\" id=\"participants-load-more-button-container\">\n                        <span class=\"load-more-button\" id=\"participants-load-more-button\">View all participants</span>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
},"useData":true});
})();