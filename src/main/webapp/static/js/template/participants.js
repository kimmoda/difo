(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['participants'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=container.lambda, alias2=container.escapeExpression;

  return "    <div class=\"zion-campaign-participant\">\n        <a href=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.participant : depth0)) != null ? stack1.profileUrl : stack1), depth0))
    + "\" target=\"_blank\"><img class=\"zion-campaign-avatar img-circle img-responsive avatar\" src=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.participant : depth0)) != null ? stack1.avatar : stack1), depth0))
    + "\" alt=\""
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.participant : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "\"></a>\n        <span class=\"zion-campaign-participant-name\">"
    + alias2(alias1(((stack1 = (depth0 != null ? depth0.participant : depth0)) != null ? stack1.displayName : stack1), depth0))
    + "</span>\n        <span class=\"zion-campaign-reword\">"
    + alias2(((helper = (helper = helpers.amount || (depth0 != null ? depth0.amount : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : (container.nullContext || {}),{"name":"amount","hash":{},"data":data}) : helper)))
    + "</span>\n    </div>\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.data : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();