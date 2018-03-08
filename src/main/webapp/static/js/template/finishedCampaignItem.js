(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['finishedCampaignItem'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {}), alias4=helpers.helperMissing, alias5="function";

  return "    <div class=\"col-xs-12 col-sm-12 col-md-6\">\n        <div class=\"row-container row\" data-campaign-id=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.task : depth0)) != null ? stack1.id : stack1), depth0))
    + "\">\n            <div class=\"col-xs-2 col-sm-2\">\n                <div class=\"preview-image\" style=\"background-image: url('"
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.task : depth0)) != null ? stack1.favicon : stack1), depth0))
    + "')\">\n\n                </div>\n            </div>\n            <div class=\"col-xs-7 col-sm-7 history-title\">\n                "
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.task : depth0)) != null ? stack1.title : stack1), depth0))
    + "\n            </div>\n            <div class=\"col-xs-3 col-sm-3 zion-campaign-reword\">\n                <i class=\"fa fa-plus zion-campaign-reword-icon\"></i>\n                <span class=\"zion-campaign-reward-text\">\n                    "
    + alias2(((helper = (helper = helpers.amount || (depth0 != null ? depth0.amount : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"amount","hash":{},"data":data}) : helper)))
    + "\n                    <br>\n                    "
    + alias2(((helper = (helper = helpers.currency || (depth0 != null ? depth0.currency : depth0)) != null ? helper : alias4),(typeof helper === alias5 ? helper.call(alias3,{"name":"currency","hash":{},"data":data}) : helper)))
    + "\n                </span>\n            </div>\n        </div>\n    </div>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.task : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();