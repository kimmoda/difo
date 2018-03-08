(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['expiredCampaignList'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : (container.nullContext || {}), alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression, alias5=container.lambda;

  return "    <div class=\"media zion-expired-campaign-item\">\n        <div class=\"media-left media-middle\">\n            <a href=\""
    + alias4(((helper = (helper = helpers.orignialPostUrl || (depth0 != null ? depth0.orignialPostUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"orignialPostUrl","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n                <img class=\"zion-expired-campaign-user-avatar img-circle avatar media-object\" src=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.participant : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\" alt=\""
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.participant : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\">\n            </a>\n        </div>\n        <div class=\"media-body\">\n            <p><span class=\"creator-name\">"
    + alias4(alias5(((stack1 = (depth0 != null ? depth0.participant : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "</span>&nbsp;<span class=\"creation-date\">"
    + alias4((helpers.dateTimeFormat || (depth0 && depth0.dateTimeFormat) || alias2).call(alias1,(depth0 != null ? depth0.creationDate : depth0),{"name":"dateTimeFormat","hash":{},"data":data}))
    + "</span></p>\n            <h4 class=\"media-heading\">"
    + alias4((helpers.subString || (depth0 && depth0.subString) || alias2).call(alias1,(depth0 != null ? depth0.title : depth0),256,{"name":"subString","hash":{},"data":data}))
    + "</h4>\n            <p class=\"zion-campaign-description\">"
    + alias4((helpers.subString || (depth0 && depth0.subString) || alias2).call(alias1,(depth0 != null ? depth0.description : depth0),256,{"name":"subString","hash":{},"data":data}))
    + "</p>\n            <a href=\""
    + alias4(((helper = (helper = helpers.orignialPostUrl || (depth0 != null ? depth0.orignialPostUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"orignialPostUrl","hash":{},"data":data}) : helper)))
    + "\" target=\"_blank\">\n                <span class=\"zion-campaign-type\">"
    + alias4(((helper = (helper = helpers.taskType || (depth0 != null ? depth0.taskType : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"taskType","hash":{},"data":data}) : helper)))
    + "</span>\n            </a>\n        </div>\n    </div>\n    <hr/>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.data : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();